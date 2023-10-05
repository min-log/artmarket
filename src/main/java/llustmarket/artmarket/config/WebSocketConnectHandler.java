package llustmarket.artmarket.config;

import llustmarket.artmarket.web.dto.chat.ChatSessionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Component
public class WebSocketConnectHandler implements HandshakeInterceptor {

    private final HttpSession httpSession;
    private List<ChatSessionDTO> chatSessionList = new ArrayList<>();

    @Transactional
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        log.info("# 연결 확인");
        if (request instanceof ServletServerHttpRequest) {
            // HttpSession에서 사용자 정보 가져오기
            ChatSessionDTO userDTO = (ChatSessionDTO) httpSession.getAttribute("chatSession");
            //log.info("연결된 사용자 : {}",userDTO);
            // WebSocket 세션에 사용자 정보 저장
            if(null==attributes.get("chatSessionList")){
                chatSessionList.add(userDTO);
                attributes.put("chatSessionList", chatSessionList);
            }

            // 복제 하여 사용 -- 동시 접속시 오류 줄일 수 잇다 .
            List<ChatSessionDTO> chatSessions = (List<ChatSessionDTO>)attributes.get("chatSessionList");
            List<ChatSessionDTO> copyOfChatSessionList;
            synchronized (chatSessionList) {
                copyOfChatSessionList = new ArrayList<>(chatSessions);
            }
            for(ChatSessionDTO i : copyOfChatSessionList){
                if(i.getMemberId() != userDTO.getMemberId() && i.getChatRoomID() != userDTO.getChatRoomID()){
                    //동일한 회원, 동일한 방이 아닐 경우
                    chatSessionList.add(userDTO);
                    attributes.put("chatSessionList",chatSessionList);
                }
            }
            attributes.put("chatSessionUser",userDTO);
            if (userDTO != null) return true;
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        log.info("연결 후 실행");
    }



}
