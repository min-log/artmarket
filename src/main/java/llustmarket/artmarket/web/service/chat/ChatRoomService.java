package llustmarket.artmarket.web.service.chat;


import llustmarket.artmarket.web.dto.chat.ChatDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomListResponseDTO;

import java.util.List;


public interface ChatRoomService {
    ChatRoomDTO registerChatRoom(ChatRoomDTO dto);
    ChatRoomDTO searchChatRoomId(long chatRoomId);
    ChatRoomListResponseDTO searchUserList(long memberId, List<ChatDTO> chatDTOS);

}