package llustmarket.artmarket.web.mapper.chat;


import llustmarket.artmarket.domain.chat.ChatMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMessageMapper {
   void insertOne(ChatMessage vo);
   ChatMessage selectOneByMessageId(long messageId);
   List<ChatMessage> selectByRoomId(long chatRoomId);

}
