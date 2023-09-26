package llustmarket.artmarket.config;

import llustmarket.artmarket.web.dto.chat.ChatSessionDTO;
import llustmarket.artmarket.web.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Component
public class WebSocketDisconnectHandler {
    private final ChatService chatService;

    @EventListener
    public void handleDisconnectEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        // WebSocket 연결 해제 시 세션 정보 가져오기
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();

        // 연결 종료된 사용자
        ChatSessionDTO chatSessionUser = (ChatSessionDTO)sessionAttributes.get("chatSessionUser");

        // 연결된 사용자 리스트 수정 및 연결 종료 된 사용자 세션 제거
        // DB 상태 변경 - 마지막 종료 시간
        List<ChatSessionDTO>  chatSessionList = (List<ChatSessionDTO>) sessionAttributes.get("chatSessionList");
        if (chatSessionList != null) {
            // WebSocket 연결을 해제시 처리되어야할 내용
            // 사용자 리스트에서 제거, 연결된 session 제거
            log.info("# 연결 종료");
            chatService.updateChatLastDate(chatSessionUser.getChatRoomID(), chatSessionUser.getMemberId());
            chatSessionList.remove(chatSessionUser);
            sessionAttributes.put("chatSessionList",chatSessionList);
            sessionAttributes.remove("chatSessionUser");
        } else {
            log.info("연결 종료된 사용자 정보 없음");
        }
    }
}
