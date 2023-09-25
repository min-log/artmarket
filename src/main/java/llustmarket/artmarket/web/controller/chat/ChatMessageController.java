package llustmarket.artmarket.web.controller.chat;


import llustmarket.artmarket.domain.alert.AlertType;
import llustmarket.artmarket.domain.chat.MessageType;
import llustmarket.artmarket.web.dto.chat.ChatMessageRequestDTO;
import llustmarket.artmarket.web.dto.chat.ChatMessageResponseDTO;
import llustmarket.artmarket.web.service.alert.AlertService;
import llustmarket.artmarket.web.service.chat.ChatMessageService;
import llustmarket.artmarket.web.service.chat.ChatRoomService;
import llustmarket.artmarket.web.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Log4j2
@RestController
@RequiredArgsConstructor
public class ChatMessageController {
    // 메시지 전송 컨트롤러
    private final SimpMessageSendingOperations sendingOperations;
    private final ChatMessageService messageService;
    private final ChatRoomService chatRoomService;
    private final ChatService chatService;
    private final AlertService alertService;
    private final List<ChatMessageRequestDTO> roomMember = new ArrayList<>();
    @Transactional
    @MessageMapping(value = "/chat-room/send")
    public void message(ChatMessageRequestDTO message){
        log.info("# 채팅방 대화 ");
        // 2. 대화내용 DB 저장 및 화면 전달 객체 내용 받기
        if(message.getCloseChatRoomId() != 0){
            log.info("# 채팅방 닫기");
            chatService.updateChatLastDate(message.getCloseChatRoomId(), message.getCloseChatMember());
            log.info(roomMember.size());
            for (int i = 0; i < roomMember.size(); i++) {
                if(roomMember.get(i).getSendChatRoomId() == message.getCloseChatRoomId() && roomMember.get(i).getSendChatSender() == message.getCloseChatMember()) roomMember.remove(i);
            }


        }else {
            messageCommunication(message);
        }
    }


    private void messageCommunication(ChatMessageRequestDTO message){
        // 1. 화면에 전달할 객체
        ChatMessageResponseDTO chatMessageResponseDTO;

        if(message.getSendChatFile() != null){
            log.info("# 채팅방 파일 + 대화");
            // 메시지 타입 추가
            message.setChatType(String.valueOf(MessageType.FILE));
            // 파일 변환 및 저장 conversion
            chatMessageResponseDTO = messageService.registerChatFileMessage(message);
        }else{
            log.info("# 채팅방 일반 대화 ");
            // 메시지 타입 추가
            message.setChatType(String.valueOf(MessageType.TALK));
            // 저장 및 반환
            chatMessageResponseDTO = messageService.registerChatMessage(message);
        }

        // 3. 룸 정보 변경 (존재하는 방이 있을 시 마지막 메시지, 메시지 전송 시간 변경)
        if(chatMessageResponseDTO != null) chatRoomService.updateChatRoom(message.getSendChatRoomId(), chatMessageResponseDTO.getChatMsg(), chatMessageResponseDTO.getChatDate());

        // 4. 채팅방 숨김 처리 된 내역일 때 숨김 처리 제거
        chatService.updateChatListStatus(message.getSendChatRoomId(),message.getSendChatSender());

        // 5. 대화내용 채팅방 내 사용자에게 전달
        sendingOperations.convertAndSend("/sub/chat-room/get/" + message.getSendChatRoomId(), chatMessageResponseDTO);
    }


    @Transactional
    @MessageMapping(value = "/chat-room/get")
    public void messageGet(ChatMessageRequestDTO message){
        //log.info("# 채팅룸 인원이 참여중인지 확인");
        long sendChatSender = message.getSendChatSender();
        long sendChatRoomId = message.getSendChatRoomId();
        boolean memberUse = false;
        boolean memberOther = false;

        if(roomMember.size() == 0) {
            roomMember.add(message);
        } else {
            for(ChatMessageRequestDTO member : roomMember){
                // 존재하는 회원인지 확인하여 추가
                if(member.getSendChatRoomId() == sendChatRoomId && member.getSendChatSender() == sendChatSender) memberUse = true;
                // 같이 있는지 확인
                if(member.getSendChatRoomId() == sendChatRoomId && member.getSendChatSender() != sendChatSender) memberOther = true;
            }
            if(memberUse == false) roomMember.add(message);
        }

        if(memberOther == false){
            log.info("# 알림 전송");
            alertService.registerAlert(sendChatSender,message.getSendChatRoomId(), AlertType.MESSAGE);
        }

    }

}