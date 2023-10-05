package llustmarket.artmarket.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import javax.servlet.http.HttpSession;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final HttpSession httpSession;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp/chat")
                .setAllowedOriginPatterns("http://localhost:8070")
                .addInterceptors(new WebSocketConnectHandler(httpSession))
                .withSockJS()
                .setClientLibraryUrl("https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"); //sock-client.js 의 위치
    }

    /*어플리케이션 내부에서 사용할 path 지정할*/
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지 받기 : sub 1:1 · topic 1:n
        registry.enableSimpleBroker("/sub","/topic");
        // 메시지 전송
        registry.setApplicationDestinationPrefixes("/pub");
    }


}