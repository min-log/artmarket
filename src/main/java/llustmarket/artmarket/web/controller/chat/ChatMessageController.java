package llustmarket.artmarket.web.controller.chat;


import llustmarket.artmarket.domain.chat.MessageType;
import llustmarket.artmarket.web.dto.chat.ChatMessageRequestDTO;
import llustmarket.artmarket.web.dto.chat.ChatMessageResponseDTO;
import llustmarket.artmarket.web.service.chat.ChatMessageService;
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


    @Transactional
    @MessageMapping(value = "/chat-room/send")
    public void message(ChatMessageRequestDTO message){
        log.info("# 채팅방 대화 ");
        log.info(message);

        // 1. 화면에 전달할 객체
        ChatMessageResponseDTO chatMessageResponseDTO;

        // 2. 대화내용 DB 저장 및 화면 전달 객체 내용 받기
        if(message.getSendChatFile() != null){
            log.info("# 채팅방 파일 + 대화");
            log.info("message : {}",message.getSendChatMsg());
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
        // 4. 대화내용 채팅방 내 사용자에게 전달
        sendingOperations.convertAndSend("/sub/chat-room/get" + message.getSendChatRoomId(), chatMessageResponseDTO);



    }



}