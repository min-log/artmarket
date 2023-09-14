package llustmarket.artmarket.web.service.chat;


import llustmarket.artmarket.web.dto.chat.ChatDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomListDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomMyPageDTO;

import java.util.List;


public interface ChatRoomService {
//    public List<ChatRoomDTO> findAllRooms();

    ChatRoomDTO registerChatRoom(ChatRoomDTO chatRoomDTO);
    ChatRoomDTO searchOneExist(ChatRoomDTO dto);
    ChatRoomMyPageDTO searchUserList(long memberId, List<ChatDTO> chatDTOS);

}