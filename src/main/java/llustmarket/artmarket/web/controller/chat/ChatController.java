package llustmarket.artmarket.web.controller.chat;


import llustmarket.artmarket.domain.member.MemberType;
import llustmarket.artmarket.web.dto.chat.ChatRoomDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomRequestDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomResponseDTO;
import llustmarket.artmarket.web.dto.member.MemberDTO;
import llustmarket.artmarket.web.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;

@Log4j2
@RestController
@RequiredArgsConstructor
public class ChatController {
    // 상품페이지 채팅방 생성

    private final ChatService chatService;

    //채팅방 개설 및 채팅방 전달 -- 버튼 클릭시로 변경 필요
    @PostMapping(value = "/product")
    public ResponseEntity<Object> create(@RequestBody ChatRoomRequestDTO roomRequestDTO){
        log.info("# 상품페이지 채팅방 생성");
        // 403 에러시 --- 시큐리티 http.csrf().disable(); 설정 필요 또는 post 전달시 프론트에서 헤더에 시큐리티 토큰 추가 필요.
        long askProductId = roomRequestDTO.getAskProduct();
        long askMember = roomRequestDTO.getAskMember();

        // 생성되는 DB: 채팅방 / 채팅참여자
        ChatRoomDTO dto = ChatRoomDTO.builder()
        .productId(askProductId) //상품번호
        .build();

        // 채팅방 생성 - 챗룸 정보, 사용자 정보 전달
        try {
            ChatRoomResponseDTO result = chatService.registerChat(askMember, dto);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch (Exception e){
            e.printStackTrace();
            String errorMessage = "{\"errorMessage\": \"상품번호 또는 회원번호가 올바르지 않습니다.\"}";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
        }


    }




}