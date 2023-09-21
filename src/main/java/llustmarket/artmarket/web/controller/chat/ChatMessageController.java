package llustmarket.artmarket.web.controller.chat;


import llustmarket.artmarket.domain.chat.MessageType;
import llustmarket.artmarket.web.dto.chat.ChatMessageRequestDTO;
import llustmarket.artmarket.web.dto.chat.ChatMessageResponseDTO;
import llustmarket.artmarket.web.service.chat.ChatMessageService;
import llustmarket.artmarket.web.service.chat.ChatRoomService;
import llustmarket.artmarket.web.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RestController
@RequiredArgsConstructor
public class ChatMessageController {
    // 메시지 전송 컨트롤러
    private final SimpMessageSendingOperations sendingOperations;
    private final ChatMessageService messageService;
    private final ChatRoomService chatRoomService;
    private final ChatService chatService;



    @Transactional
    @MessageMapping(value = "/chat-room/send")
    public void message(ChatMessageRequestDTO message){
        log.info("# 채팅방 대화 ");
        // 2. 대화내용 DB 저장 및 화면 전달 객체 내용 받기
        if(message.getCloseChatRoomId() != 0){
            log.info("# 채팅방 닫기");
            chatService.updateChatLastDate(message.getCloseChatRoomId(), message.getCloseChatMember());
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


        // 4. 대화내용 채팅방 내 사용자에게 전달
        sendingOperations.convertAndSend("/sub/chat-room/get/" + message.getSendChatRoomId(), chatMessageResponseDTO);
    }



}