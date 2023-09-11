package llustmarket.artmarket.web.service.chat;


import llustmarket.artmarket.web.dto.chat.ChatRoomDTO;


public interface ChatRoomService {
//    public List<ChatRoomDTO> findAllRooms();

    ChatRoomDTO registerChatRoom(ChatRoomDTO chatRoomDTO);
    ChatRoomDTO searchOneExist(ChatRoomDTO dto);


}