package llustmarket.artmarket.web.controller.chat;


import llustmarket.artmarket.web.dto.chat.ChatDTO;
import llustmarket.artmarket.web.dto.member.MemberDTO;
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
public class ChatRoomController {

    private final ChatService chatService;

    
    @GetMapping(value = "/rooms")
    public String rooms(Model model, HttpSession session){
        // 채팅방 생성
        //작가 회원
        MemberDTO authorMember = MemberDTO.builder().memberId(1).identity("AUTHOR").nickname("이지민").build();
        session.setAttribute("authorMember",authorMember);

        log.info("# All Chat Rooms");

        return "chat/rooms";
    }


    @GetMapping(value = "/roomList")
    public String roomList(Model model,HttpSession session){
        // 채팅내역 조회
        log.info("# All Chat roomList");
        log.info("# 해당페이지에서는 멤버가 작가로 로그인되게 임의로 변경");
        session.setAttribute("member", (MemberDTO)session.getAttribute("authorMember"));
        MemberDTO authorMember = (MemberDTO) session.getAttribute("member");

        List<ChatDTO> list = chatService.findChatRoomById(authorMember.getMemberId());
        log.info(list);

        model.addAttribute("list",list);
        return "chat/rooms_author";
    }




    // 임시 유저 아이디 session 저장
    @PostMapping(value = "/user")
    public String user(MemberDTO memberDTO, HttpSession session){

        log.info("# session :{}",memberDTO);
        session.setAttribute("userName",memberDTO.getNickname());
        memberDTO.setIdentity("GENERAL");
        session.setAttribute("member", memberDTO);


        return "redirect:/chat/rooms";
    }

}