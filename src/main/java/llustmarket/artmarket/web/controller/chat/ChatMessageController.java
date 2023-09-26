package llustmarket.artmarket.web.controller.chat;



import llustmarket.artmarket.domain.alert.AlertType;
import llustmarket.artmarket.domain.chat.MessageType;
import llustmarket.artmarket.web.dto.alert.AlertDTO;
import llustmarket.artmarket.web.dto.chat.ChatMessageRequestDTO;
import llustmarket.artmarket.web.dto.chat.ChatMessageResponseDTO;
import llustmarket.artmarket.web.dto.chat.ChatSessionDTO;
import llustmarket.artmarket.web.service.alert.AlertService;
import llustmarket.artmarket.web.service.chat.ChatMessageService;
import llustmarket.artmarket.web.service.chat.ChatRoomService;
import llustmarket.artmarket.web.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;

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
    public void message(ChatMessageRequestDTO message, SimpMessageHeaderAccessor messageHeaderAccessor){
        log.info("# 채팅방 대화 ");
        // log.info("# 같은 방의 참여자가 접속 중 인지 확인");
        boolean memberOther = false;

        // StompHeaderAccessor를 사용하여 WebSocket 세션에서 사용자 정보 가져오기
        Map<String, Object> sessionAttributes = messageHeaderAccessor.getSessionAttributes();
        List<ChatSessionDTO> sessionList = (List<ChatSessionDTO>)sessionAttributes.get("chatSessionList");
        // log.info("sessionList 모든 사용자 수 : {}", sessionList.size());
        if(sessionList.size() != 0){
            for (ChatSessionDTO session : sessionList) {
                // log.info("같은방 다른 사용자가 존재할 시 ");
                if (session.getChatRoomID() == message.getSendChatRoomId() && session.getMemberId() != message.getSendChatSender())  memberOther = true;
            }
            if(memberOther == false){
                log.info("# 알림");
               //  기존 알림이 있을 시 시간만 업데이트
                AlertDTO alertDTO = alertService.searchOnePath(message.getSendChatRoomId(), AlertType.MESSAGE);
                if(alertDTO != null){
                    alertService.updateDate(alertDTO);
                }else {
                    //같은 방의 다른 회원 없음 알림 전달
                    alertService.registerAlert(message.getSendChatSender(),message.getSendChatRoomId(), AlertType.MESSAGE);
                }

            }
        }

        // 메시지 관련 처리
        messageCommunication(message);

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


}