package llustmarket.artmarket.web.mapper.chat;


import llustmarket.artmarket.domain.chat.ChatRoom;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatRoomMapper {
   void insertOne(ChatRoom vo);
   ChatRoom selectOneId(Long chatRoomId);


}
