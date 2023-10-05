package llustmarket.artmarket.web.service.chat;


import llustmarket.artmarket.web.dto.chat.ChatDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
class ChatServiceImplTest {
    @Autowired
    private ChatService chatService;



    @Test
    void selectChatExist() {
        ChatDTO dto = ChatDTO.builder()
                .productId(1)
                .memberId(3)
                .build();
        ChatDTO chatDTO = chatService.searchOneExist(dto);
        System.out.println(chatDTO);
    }


    @Test
    void removeStateChat() {
        boolean result = chatService.removeStateChat(8, 3);
        System.out.println("result : "+ result);
    }
}