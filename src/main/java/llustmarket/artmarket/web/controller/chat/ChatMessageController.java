package llustmarket.artmarket.web.controller.chat;


import llustmarket.artmarket.domain.chat.MessageType;
import llustmarket.artmarket.web.dto.file.FileUploadDTO;
import llustmarket.artmarket.web.dto.chat.ChatMessageDTO;
import llustmarket.artmarket.web.dto.chat.ChatMessageResponseDTO;
import llustmarket.artmarket.web.dto.member.MemberDTO;
import llustmarket.artmarket.web.service.chat.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RestController
@RequiredArgsConstructor
public class ChatMessageController {
    private final SimpMessageSendingOperations sendingOperations;
    private final ChatMessageService messageService;


    //private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달

    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합
    //"/pub/chat/enter"
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDTO message){
        log.info("MESSAGE ENTER");
        log.info("message : {}",message);

        MemberDTO member = message.getMember();
        message.setWriter(member.getNickname());
        message.setMemberId(member.getMemberId());

        // 메시지 타입 추가
        message.setChatMessageType(String.valueOf(MessageType.ENTER));
        // 저장 및 반환
        ChatMessageResponseDTO chatMessageResponseDTO = messageService.registerChatMessage(message);
        // 추가 전달하는 내용
        chatMessageResponseDTO.setWriter(member.getNickname());
        chatMessageResponseDTO.setWriterIdentity(member.getIdentity());

        sendingOperations.convertAndSend("/sub/chat/room/"  + message.getChatRoomId(), chatMessageResponseDTO);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDTO message){
        log.info("MESSAGE TALK");
        log.info(message);
        log.info(message.getFile());


        MemberDTO member = message.getMember();
        message.setWriter(member.getNickname());
        message.setMemberId(member.getMemberId());

        FileUploadDTO file = message.getFile();


        if(file != null){
            // 메시지 타입 추가
            message.setChatMessageType(String.valueOf(MessageType.FILE));
            // 파일 변환 및 저장 conversion
            ChatMessageResponseDTO chatMessageResponseDTO = messageService.registerChatFileMessage(message);


        }else{
            // 메시지 타입 추가
            message.setChatMessageType(String.valueOf(MessageType.TALK));
            // 저장 및 반환
            ChatMessageResponseDTO chatMessageResponseDTO = messageService.registerChatMessage(message);
            // 추가 전달하는 내용
            chatMessageResponseDTO.setWriter(member.getNickname());
            chatMessageResponseDTO.setWriterIdentity(member.getIdentity());
        }


        //sendingOperations.convertAndSend("/sub/chat/room/" + message.getChatRoomId(), chatMessageRequestDTO);
    }



}