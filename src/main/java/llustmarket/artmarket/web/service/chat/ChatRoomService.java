package llustmarket.artmarket.web.service.chat;


import llustmarket.artmarket.web.dto.chat.ChatDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomListResponseDTO;

import java.time.LocalDateTime;
import java.util.List;


public interface ChatRoomService {
    ChatRoomDTO registerChatRoom(ChatRoomDTO dto);
    void updateChatRoom(long roomId, String message, LocalDateTime date);
    ChatRoomDTO searchChatRoomId(long chatRoomId);
    ChatRoomListResponseDTO searchUserList(long memberId, List<ChatDTO> chatDTOS);

}