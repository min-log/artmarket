package llustmarket.artmarket.web.controller.chat;


import llustmarket.artmarket.web.dto.chat.ChatDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomListResponseDTO;
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
    // 채팅방 조회 관련
    private final ChatService chatService;
    private final ChatRoomService chatRoomService;


    @GetMapping(value = "/myfage")
    public ResponseEntity<Object> roomList(@RequestParam(value = "myfageId") long memberId) {
        log.info("# 마이페이지 채팅 내역 조회");
        List<ChatDTO> list = chatService.findChatRoomById(memberId);
        try {
            ChatRoomListResponseDTO chatRoomListResponseDTO = chatRoomService.searchUserList(memberId, list);
            return ResponseEntity.status(HttpStatus.OK).body(chatRoomListResponseDTO);
        }catch (Exception e){
            e.printStackTrace();
            log.info("err msg : {}",e.getMessage());
            // 에러 메시지를 JSON 형식으로 바디에 담아서 반환
            String errorMessage = "{\"myfageErrorMsg\" :\"memberId is null, 일치하는 회원을 찾을 수 없습니다.\"}";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
        }
    }


    // 하나의 채팅 룸 안에 대화 내용 출력
//    @GetMapping(value = "/myfage")
//    public ResponseEntity<Object> roomList(@RequestParam(value = "clickChatId") long roomId,
//                                           @RequestParam(value = "clickMember") long memberId) {
//        // 존재하는 채팅방 룸 정보와 대화 내역 전송
//
//
//
//
//
//        return null;
//
//    }






}