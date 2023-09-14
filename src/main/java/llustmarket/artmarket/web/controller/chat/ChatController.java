package llustmarket.artmarket.web.controller.chat;


import llustmarket.artmarket.domain.member.MemberType;
import llustmarket.artmarket.web.dto.chat.ChatRoomDTO;
import llustmarket.artmarket.web.dto.member.MemberDTO;
import llustmarket.artmarket.web.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/apiChat")
public class ChatController {
    private final ChatService chatService;
    private final HttpHeaders header = new HttpHeaders();

    //채팅방 개설 및 채팅방 전달 -- 버튼 클릭시로 변경 필요
    @GetMapping(value = "/room")
    public ResponseEntity<ChatRoomDTO> create(@RequestParam(value = "productId") long productId,
                                              @RequestParam(value = "authorId") long authorId,
                                              HttpSession session
                              ){
        log.info("# 상품페이지 채팅방 생성");
        // 생성되는 DB: 채팅방 / 채팅참여자
        // 현재 사용자 정보 : 추후 session 말고 Authentication 가져오기
        MemberDTO member = MemberDTO.builder().memberId(3).nickname("전서연").identity(String.valueOf(MemberType.GENERAL)).build();

        ChatRoomDTO dto = ChatRoomDTO.builder()
        .productId(productId) //상품번호
        .chatFromId(member.getMemberId()) // 보내는사람
        .chatToId(authorId) // 작가
        .build();

        // 채팅방 생성 - 챗룸 정보, 사용자 정보 전달
        ChatRoomDTO result = chatService.registerChat(member, dto);
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return new ResponseEntity<>(result, header, HttpStatus.OK);
    }




}