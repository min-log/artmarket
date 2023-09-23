package llustmarket.artmarket.web.mapper.chat;

import llustmarket.artmarket.domain.chat.ChatRoomList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class ChatRoomMapperTest {
    @Autowired
    private ChatRoomMapper chatRoomMapper;
    @Test
    void selectListByRoomId() {
        List<ChatRoomList> chatRoomLists = chatRoomMapper.selectListByRoomId(1);
        chatRoomLists.forEach(item->{
            System.out.println(item);
        });
    }

}