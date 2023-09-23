package llustmarket.artmarket.web.service.chat;

import llustmarket.artmarket.web.dto.chat.ChatRoomListResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatRoomServiceImplTest {

    @Autowired
    private ChatRoomService chatRoomService;
    @Test
    @DisplayName("나의 채팅 리스트 확인")
    void searchChatRoomList() {
        ChatRoomListResponseDTO chatRoomListResponseDTO = chatRoomService.searchChatRoomList(1);
        System.out.println(chatRoomListResponseDTO);
    }
}