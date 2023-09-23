package llustmarket.artmarket.web.mapper.chat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatRoomMapperTest {
    @Autowired
    private ChatRoomMapper chatRoomMapper;

    @Test
    @DisplayName("리스트 순서 확인")
    private void test1(){
        chatRoomMapper.selectOneId(1);
    }

}