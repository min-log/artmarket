package llustmarket.artmarket.web.mapper.chat;

import llustmarket.artmarket.domain.chat.Chat;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {
   void insertOne(Chat vo);
   int updateChatLastDate(Chat vo);
   int updateChatStatus(Chat vo);
   int deleteChat(long chatId);

   Chat selectOneExist(Chat vo);
   List<Chat> selectByMemberId(Chat vo);
   Chat selectByRoomIdAndMemberId(long chatRoomId, long memberId);
}
