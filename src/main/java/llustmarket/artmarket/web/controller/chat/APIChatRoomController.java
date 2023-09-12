package llustmarket.artmarket.web.controller.chat;


import llustmarket.artmarket.web.dto.chat.ChatRoomDTO;
import llustmarket.artmarket.web.dto.member.MemberDTO;
import llustmarket.artmarket.web.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/apiChat")
public class APIChatRoomController {
    private final ChatService chatService;


    //채팅방 개설 및 채팅방 전달 -- 버튼 클릭시로 변경 필요
    @GetMapping(value = "/room")
    public ChatRoomDTO create(@RequestParam(value = "productId") long productId,
                              @RequestParam(value = "authorId") long authorId,
                              HttpSession session
                              ){
        log.info("채팅방 / 채팅참여자  register ------------------");
        // 현재 사용자 정보 : 추후 session 말고 Authentication 가져오기
        MemberDTO member = (MemberDTO) session.getAttribute("member");
        log.info(member);

        ChatRoomDTO dto = ChatRoomDTO.builder()
                .productId(productId) //상품번호
                .chatFromId(member.getMemberId()) // 보내는사람
                .chatToId(authorId) // 작가
                .chatFromId(member.getMemberId()).build();

        // 채팅방 생성 - 챗룸 정보, 사용자 정보 전달
        ChatRoomDTO result = chatService.registerChat(member, dto);
        return result;
    }




}