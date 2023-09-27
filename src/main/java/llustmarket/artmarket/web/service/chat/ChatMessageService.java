package llustmarket.artmarket.web.service.chat;


import llustmarket.artmarket.domain.chat.ChatMessage;
import llustmarket.artmarket.web.dto.chat.ChatMessageRequestDTO;
import llustmarket.artmarket.web.dto.chat.ChatMessageResponseDTO;
import llustmarket.artmarket.web.service.DateTimeService;
import java.util.List;


public interface ChatMessageService {

    ChatMessageResponseDTO registerChatMessage(ChatMessageRequestDTO dto);
    ChatMessageResponseDTO registerChatFileMessage(ChatMessageRequestDTO dto);
    ChatMessageResponseDTO searchChatMessageOne(long chatMessageId);
    List<ChatMessageResponseDTO> searchChatMessageList(long chatRoomId);

    void deleteMessageList(long chatRoomId);


    default ChatMessage messageDTOToVO(ChatMessageRequestDTO dto){
        ChatMessage result = ChatMessage.builder()
                .memberId(dto.getSendChatSender())
                .chatRoomId(dto.getSendChatRoomId())
                .message(dto.getSendChatMsg())
                .chatMessageType(dto.getChatType())
                .build();
        return result;
    }


    default ChatMessageResponseDTO messageVOToResultDTO(ChatMessage vo){
        //시간 변경
        DateTimeService dateTimeService = new DateTimeService();
        String chatDate = dateTimeService.DateToString(vo.getChatMessageDate());

        ChatMessageResponseDTO result =  ChatMessageResponseDTO.builder()
                .chatSender(vo.getMemberId())
                .chatDate(chatDate)
                .chatMsg(vo.getMessage())
                .chatType(vo.getChatMessageType())
                .build();
        return result;
    }

}