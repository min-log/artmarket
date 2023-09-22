package llustmarket.artmarket.web.service.chat;


import llustmarket.artmarket.domain.chat.ChatMessage;
import llustmarket.artmarket.web.dto.chat.ChatMessageRequestDTO;
import llustmarket.artmarket.web.dto.chat.ChatMessageResponseDTO;

import java.util.List;


public interface ChatMessageService {



    ChatMessageResponseDTO registerChatMessage(ChatMessageRequestDTO dto);
    ChatMessageResponseDTO registerChatFileMessage(ChatMessageRequestDTO dto);
    ChatMessageResponseDTO searchChatMessageOne(long chatMessageId);
    List<ChatMessageResponseDTO> searchChatMessageList(long chatRoomId);
    int deleteMessage(long chatMessageId);


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
        ChatMessageResponseDTO result =  ChatMessageResponseDTO.builder()
                .chatMsgId(vo.getChatRoomId())
                .chatSender(vo.getMemberId())
                .chatDate(vo.getChatMessageDate())
                .chatMsg(vo.getMessage())
                .chatType(vo.getChatMessageType())
                .build();
        return result;
    }

}