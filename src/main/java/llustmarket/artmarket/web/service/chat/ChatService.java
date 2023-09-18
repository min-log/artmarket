package llustmarket.artmarket.web.service.chat;


import llustmarket.artmarket.web.dto.chat.ChatDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomResponseDTO;
import llustmarket.artmarket.web.dto.member.MemberDTO;

import java.util.List;


public interface ChatService {
    ChatRoomResponseDTO registerChat(long askMemberId, ChatRoomDTO roomDTO);
    ChatDTO searchOneExist(ChatDTO chatDTO);
    public List<ChatDTO> findChatRoomById(long memberId);

}