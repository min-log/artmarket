package llustmarket.artmarket.web.service.chat;



import llustmarket.artmarket.domain.chat.ChatRoom;
import llustmarket.artmarket.web.dto.chat.ChatRoomDTO;
import llustmarket.artmarket.web.mapper.chat.ChatRoomMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Log4j2
@RequiredArgsConstructor
@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ModelMapper modelMapper;
    private final ChatRoomMapper chatRoomMapper;

    @Override
    public ChatRoomDTO registerChatRoom(ChatRoomDTO chatRoomDTO) {
        log.info("register ChatRoom ------------------------------");
        chatRoomMapper.insertOne(modelMapper.map(chatRoomDTO, ChatRoom.class));
        // 생성한 값 전달
        ChatRoomDTO result = searchOneExist(chatRoomDTO);
        return result;
    }


    @Override
    public ChatRoomDTO searchOneExist(ChatRoomDTO dto) {
        log.info("selectOne ------------------------------");
        log.info("dto : {}",dto);
        ChatRoom chatRoom =null;
        try {
            chatRoom = chatRoomMapper.selectOneExist(modelMapper.map(dto, ChatRoom.class));
            if(chatRoom == null) return null;
        }catch (Exception e) {
            e.printStackTrace();
        }

        log.info("vo : {}",chatRoom);
        return modelMapper.map(chatRoom, ChatRoomDTO.class);
    }
    
}
