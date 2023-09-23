package llustmarket.artmarket.web.controller.chat;


import llustmarket.artmarket.web.dto.chat.ChatDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomListResponseDTO;
import llustmarket.artmarket.web.dto.member.MemberDTO;
import llustmarket.artmarket.web.service.chat.ChatRoomService;
import llustmarket.artmarket.web.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Log4j2
public class ChatTestController {

    private final ChatService chatService;
    private final ChatRoomService chatRoomService;

    @GetMapping(value = "/rooms")
    public String rooms(Model model, HttpSession session){
        log.info("#마이페이지 회원 채팅방 리스트 전송");
        // 채팅방 생성
        //작가 회원
        session.setAttribute("member", MemberDTO.builder().build());
        MemberDTO authorMember = MemberDTO.builder().memberId(1).identity("AUTHOR").nickname("이지민").build();
        session.setAttribute("authorMember",authorMember);

        return "chat/rooms";
    }


    @GetMapping(value = "/roomList")
    public String roomList(Model model,HttpSession session){
        // 채팅내역 조회
        log.info("# All Chat roomList");
        session.setAttribute("member", (MemberDTO)session.getAttribute("authorMember"));
        MemberDTO member = (MemberDTO) session.getAttribute("member");

        log.info("member : {}",member);
        session.setAttribute("userName",member.getNickname());
        session.setAttribute("memberId",member.getMemberId());
        ChatRoomListResponseDTO chatRoomListResponseDTO = chatRoomService.searchChatRoomList(member.getMemberId());
        model.addAttribute("chatRoomDTO", chatRoomListResponseDTO);

        return "chat/rooms_author";
    }
    // 임시 유저 아이디 session 저장
    @PostMapping(value = "/user")
    public String user(MemberDTO memberDTO, HttpSession session){

        log.info("# session :{}",memberDTO);
        session.setAttribute("member", memberDTO);
        session.setAttribute("userName",memberDTO.getNickname());
        session.setAttribute("memberId",memberDTO.getMemberId());




        return "redirect:/chat/rooms";
    }


}
