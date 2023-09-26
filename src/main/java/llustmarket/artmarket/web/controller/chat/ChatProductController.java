package llustmarket.artmarket.web.controller.chat;


import llustmarket.artmarket.web.dto.chat.ChatRoomDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomRequestDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomResponseDTO;
import llustmarket.artmarket.web.dto.chat.ChatSessionDTO;
import llustmarket.artmarket.web.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Log4j2
@RestController
@RequiredArgsConstructor
public class ChatProductController {
    // 상품페이지 채팅방 생성
    private final ChatService chatService;

    //채팅방 개설 및 채팅방 전달 -- 버튼 클릭시로 변경 필요
    @PostMapping(value = "/product")
    public ResponseEntity<Object> create(@RequestBody ChatRoomRequestDTO roomRequestDTO, HttpSession httpSession){
        log.info("# 상품페이지 채팅방 생성");

        long askProductId = roomRequestDTO.getAskProduct();
        long askMember = roomRequestDTO.getAskMember();

        // 생성되는 DB: 채팅방 / 채팅참여자
        ChatRoomDTO dto = ChatRoomDTO.builder()
        .productId(askProductId) //상품번호
        .build();

        // 채팅방 생성 - 챗룸 정보, 사용자 정보 전달
        try {
            ChatRoomResponseDTO result = chatService.registerChat(askMember, dto);
            // 연결된 회원 정보
            httpSession.setAttribute("chatSession", ChatSessionDTO.builder().chatRoomID(result.getChatRoomId()).memberId(askMember).build());

            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(null);
        }

    }




}