package llustmarket.artmarket.web.service.chat;


import llustmarket.artmarket.web.dto.chat.ChatMessageDTO;
import llustmarket.artmarket.web.dto.chat.ChatMessageResponseDTO;

import java.util.List;


public interface ChatMessageService {
    ChatMessageResponseDTO registerChatMessage(ChatMessageDTO dto);

    ChatMessageResponseDTO registerChatFileMessage(ChatMessageDTO dto);

    // 하나의 메시지 가져오기
    ChatMessageResponseDTO searchChatMessageOne(long chatMessageId);

    // 초기 메시지 리스트 10개 인덱스 번호 아래부터
    List<ChatMessageResponseDTO> searchChatMessageList(long chatRoomId);


    // 더보기 클릭시 + 현제 리스트 뒤로 + 10
        // 현제 마지막 인덱스 + 10
    List<ChatMessageResponseDTO> searchChatMessageListMore(long chatRoomId, long lastNum);



}