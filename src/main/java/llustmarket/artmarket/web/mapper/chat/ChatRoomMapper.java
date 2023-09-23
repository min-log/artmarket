package llustmarket.artmarket.web.mapper.chat;


import llustmarket.artmarket.domain.chat.ChatRoom;
import llustmarket.artmarket.domain.chat.ChatRoomList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatRoomMapper {
   void insertOne(ChatRoom vo);
   int updateOne(ChatRoom vo);
   int deleteChatRoom(long chatRoomId);
   ChatRoom selectOneId(Long chatRoomId);
   List<ChatRoomList> selectListByRoomId(long chatRoomId);


}
