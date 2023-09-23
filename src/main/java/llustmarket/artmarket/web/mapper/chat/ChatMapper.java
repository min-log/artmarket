package llustmarket.artmarket.web.mapper.chat;

import llustmarket.artmarket.domain.chat.Chat;
import llustmarket.artmarket.domain.chat.ChatRoomList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {
   void insertOne(Chat vo);
   int updateChatLastDate(Chat vo);
   int updateChatStatus(Chat vo);
   int deleteChat(Chat vo);

   Chat selectOneExist(Chat vo);
   List<Chat> selectByRoomId(long chatRoomId);
   Chat selectByRoomIdAndMemberId(Chat vo);
}
