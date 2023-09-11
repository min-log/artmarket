package llustmarket.artmarket.web.service.chat;


import llustmarket.artmarket.web.dto.chat.ChatDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomDTO;
import llustmarket.artmarket.web.dto.member.MemberDTO;

import java.util.List;


public interface ChatService {
    ChatRoomDTO registerChat(MemberDTO member, ChatRoomDTO roomDTO);

    //기존 문의내역 존재하는지 확인
    ChatDTO searchOneExist(ChatDTO chatDTO);

    // 보유하고 있는 대화 내역 조회
    public List<ChatDTO> findChatRoomById(long memberId);


}