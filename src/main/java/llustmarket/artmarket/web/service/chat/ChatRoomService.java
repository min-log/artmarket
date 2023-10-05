package llustmarket.artmarket.web.service.chat;


import llustmarket.artmarket.web.dto.chat.ChatRoomDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomListResponseDTO;
import java.time.LocalDateTime;


public interface ChatRoomService {
    ChatRoomDTO registerChatRoom(ChatRoomDTO dto);
    void updateChatRoom(long roomId, String message, String date);
    int deleteChat(long chatRoomId);
    ChatRoomDTO searchChatRoomId(long chatRoomId);
    ChatRoomListResponseDTO searchChatRoomList(long memberId);


}