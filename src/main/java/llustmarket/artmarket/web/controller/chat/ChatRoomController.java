package llustmarket.artmarket.web.controller.chat;


import llustmarket.artmarket.domain.alert.AlertType;
import llustmarket.artmarket.web.dto.chat.*;
import llustmarket.artmarket.web.service.alert.AlertService;
import llustmarket.artmarket.web.service.chat.ChatRoomService;
import llustmarket.artmarket.web.service.chat.ChatService;
import llustmarket.artmarket.web.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Log4j2
@RestController
@RequiredArgsConstructor
public class ChatRoomController {
    // 마이페이지

    private final ChatService chatService;
    private final ChatRoomService chatRoomService;
    private final AlertService alertService;
    private final FileService fileService;

    @GetMapping(value = "/myfage/{member_id}")
    public ResponseEntity<Object> roomList(@PathVariable(value = "member_id") long memberId) {
        log.info("# 마이페이지 채팅 내역 조회");
        try {
            ChatRoomListResponseDTO chatRoomListResponseDTO = chatRoomService.searchChatRoomList(memberId);
            chatRoomListResponseDTO.setProfileImg2(fileService.getAttachmentImage(chatRoomListResponseDTO.getProfileImg()));
            return ResponseEntity.status(HttpStatus.OK).body(chatRoomListResponseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            // 일치하는 회원 없음
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(null);
        }
    }


    // 하나의 채팅 룸 안에 대화 내용 출력
    @Transactional
    @PostMapping(value = "/myfage")
    public ResponseEntity<Object> roomListView(@RequestBody ChatRoomRequestDTO roomRequestDTO, HttpSession httpSession) {
        log.info("# 마이페이지 채팅 상세페이지");
        long clickChatId = roomRequestDTO.getClickChatId();
        long clickMember = roomRequestDTO.getClickMember();

        ChatRoomDTO chatRoomDTO = chatRoomService.searchChatRoomId(clickChatId);
        if (clickMember != chatRoomDTO.getChatFromId() && clickMember != chatRoomDTO.getChatToId()) {
            // 속하는 회원이 아닐 경우
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // 연결된 회원 정보
        httpSession.setAttribute("chatSession", ChatSessionDTO.builder().chatRoomID(clickChatId).memberId(clickMember).build());

        // 알림이 있었다면, 상태 변경
        alertService.updateOneCheck(clickMember, clickChatId, AlertType.MESSAGE);

        // 존재하는 채팅방 룸 정보와 대화 내역 전송
        ChatRoomResponseDTO chatRoomResponseDTO = chatService.searchOneRoomId(clickChatId);
        return ResponseEntity.status(HttpStatus.OK).body(chatRoomResponseDTO);
    }


    @DeleteMapping(value = "/myfage")
    public ResponseEntity<Object> chatRoomDelete(@RequestBody ChatRoomRequestDTO roomRequestDTO) {
        log.info("# 채팅룸 삭제");
        boolean result = false;
        try {
            result = chatService.removeStateChat(roomRequestDTO.getRemChatRoomId(), roomRequestDTO.getRemChatMember());
            if (result == false) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


}
