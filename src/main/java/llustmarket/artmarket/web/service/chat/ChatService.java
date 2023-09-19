package llustmarket.artmarket.web.service.chat;


import llustmarket.artmarket.web.dto.chat.ChatDTO;
import llustmarket.artmarket.web.dto.chat.ChatMessageResponseDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomResponseDTO;
import lombok.extern.log4j.Log4j2;

import java.util.List;


public interface ChatService {
    ChatRoomResponseDTO registerChat(long askMemberId, ChatRoomDTO roomDTO);
    ChatDTO searchOneExist(ChatDTO chatDTO);
    ChatRoomResponseDTO searchOneRoomId(long roomId);
    public List<ChatDTO> searchChatAllByMemberId(long memberId);


}