package llustmarket.artmarket.web.controller.chat;


import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.web.dto.chat.ChatDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomListResponseDTO;
import llustmarket.artmarket.web.dto.chat.ChatSessionDTO;
import llustmarket.artmarket.web.dto.member.MemberDTO;
import llustmarket.artmarket.web.service.chat.ChatRoomService;
import llustmarket.artmarket.web.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper modelMapper;
    private final ChatService chatService;
    private final ChatRoomService chatRoomService;

    @GetMapping(value = "/rooms")
    public String rooms(Model model, HttpSession session){
        log.info("#마이페이지 회원 채팅방 리스트 전송");
        // 채팅방 생성
        //작가 회원
        Object login = session.getAttribute("login");
        log.info("login : {}",login);



        return "chat/rooms";
    }


    @GetMapping(value = "/roomList")
    public String roomList(Model model,HttpSession session){
        long memberId = 1;
        try{
            Member member = (Member) session.getAttribute("login");

            ChatSessionDTO login = (ChatSessionDTO)session.getAttribute("login");
            if(login == null){
                session.setAttribute("login",ChatSessionDTO.builder().memberId(member.getMemberId()).build());
            }
            log.info("login : {}",login);
            memberId = login.getMemberId();

        }catch (Exception e){
            e.printStackTrace();
        }


        ChatRoomListResponseDTO chatRoomListResponseDTO = chatRoomService.searchChatRoomList(memberId);
        model.addAttribute("chatRoomDTO", chatRoomListResponseDTO);

        return "chat/rooms_author";
    }
    // 임시 유저 아이디 session 저장
    @PostMapping(value = "/user")
    public String user(MemberDTO memberDTO, HttpSession session){


        log.info("# session :{}",memberDTO);
        session.setAttribute("login", memberDTO);
        session.setAttribute("userName",memberDTO.getNickname());
        session.setAttribute("memberId",memberDTO.getMemberId());




        return "redirect:/chat/rooms";
    }


}
