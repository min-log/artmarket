package llustmarket.artmarket.web.service.chat;


import llustmarket.artmarket.web.dto.chat.ChatDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomResponseDTO;

import java.time.LocalDateTime;


public interface ChatService {
    ChatRoomResponseDTO registerChat(long askMemberId, ChatRoomDTO roomDTO);
    void updateChatLastDate(long roomId, long memberId);
    int updateChatStatus(long roomId, long memberId,boolean status,LocalDateTime leaveDate);
    void updateChatListStatus(long roomId, long memberId);
    ChatDTO searchOneExist(ChatDTO chatDTO);
    Boolean searchOneChatStatus(long roomId,long memberId);
    ChatRoomResponseDTO searchOneRoomId(long roomId);
    boolean removeStateChat(long roomId, long memberId);
    boolean removeChat(long roomId,long chatId, long otherChatId);




}