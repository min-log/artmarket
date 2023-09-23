package llustmarket.artmarket.web.controller.chat;


import llustmarket.artmarket.web.dto.chat.*;
import llustmarket.artmarket.web.service.chat.ChatRoomService;
import llustmarket.artmarket.web.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
public class ChatRoomController {
    // 마이페이지

    private final ChatService chatService;
    private final ChatRoomService chatRoomService;

    @GetMapping(value = "/myfage/{member_id}")
    public ResponseEntity<Object> roomList(@PathVariable(value = "member_id") long memberId) {
        log.info("# 마이페이지 채팅 내역 조회");
        try {
            ChatRoomListResponseDTO chatRoomListResponseDTO = chatRoomService.searchChatRoomList(memberId);
            return ResponseEntity.status(HttpStatus.OK).body(chatRoomListResponseDTO);
        }catch (Exception e){
            e.printStackTrace();
            // 일치하는 회원 없음
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(null);
        }
    }


    // 하나의 채팅 룸 안에 대화 내용 출력
    @PostMapping(value = "/myfage")
    public ResponseEntity<Object> roomList(@RequestBody ChatRoomRequestDTO roomRequestDTO) {
        log.info("# 마이페이지 채팅 상세페이지");
        long clickChatId = roomRequestDTO.getClickChatId();
        long clickMember = roomRequestDTO.getClickMember();

        ChatRoomDTO chatRoomDTO = chatRoomService.searchChatRoomId(clickChatId);
        if(clickMember != chatRoomDTO.getChatFromId() && clickMember != chatRoomDTO.getChatToId()){
            // 속하는 회원이 아닐 경우
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        ChatRoomResponseDTO chatRoomResponseDTO = chatService.searchOneRoomId(clickChatId);
        // 존재하는 채팅방 룸 정보와 대화 내역 전송
        return ResponseEntity.status(HttpStatus.OK).body(chatRoomResponseDTO);
    }


    @DeleteMapping(value = "/myfage")
    public ResponseEntity<Object> chatRoomDelete(@RequestBody ChatRoomRequestDTO roomRequestDTO){
        log.info("# 채팅룸 삭제");
        boolean result = false;
        try{
            result = chatService.removeStateChat(roomRequestDTO.getRemChatRoomId(), roomRequestDTO.getRemChatMember());
            if(result == false){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }





}