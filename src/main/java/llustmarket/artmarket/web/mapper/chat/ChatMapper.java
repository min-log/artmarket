package llustmarket.artmarket.web.mapper.chat;

import llustmarket.artmarket.domain.chat.Chat;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {
   void insertOne(Chat vo);
   int updateChatLastDate(Chat vo);
   int updateChatStatus(Chat vo);
   int deleteChat(Chat vo);

   Chat selectOneExist(Chat vo);
   List<Chat> selectByRoomId(long RoomId);
   List<Chat> selectByMemberId(Chat vo);
   Chat selectByRoomIdAndMemberId(Chat vo);
}
