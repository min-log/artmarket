package llustmarket.artmarket.web.service.chat;


import llustmarket.artmarket.web.dto.chat.ChatDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomResponseDTO;

import java.util.List;


public interface ChatService {
    ChatRoomResponseDTO registerChat(long askMemberId, ChatRoomDTO roomDTO);
    void updateChatLastDate(long roomId, long memberId);
    int updateChatStatus(long roomId, long memberId,boolean status);
    void chatListStatusChange(long roomId);
    ChatDTO searchOneExist(ChatDTO chatDTO);
    Boolean searchOneChatStatus(long roomId,long memberId);
    ChatRoomResponseDTO searchOneRoomId(long roomId);
    List<ChatDTO> searchChatAllByMemberId(long memberId);
    boolean removeStateChat(long roomId, long memberId);
    boolean removeChat(long roomId,long chatId, long otherChatId);




}