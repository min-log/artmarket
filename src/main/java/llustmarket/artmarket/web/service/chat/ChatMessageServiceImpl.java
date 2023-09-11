package llustmarket.artmarket.web.service.chat;


import llustmarket.artmarket.domain.chat.ChatMessage;
import llustmarket.artmarket.web.dto.chat.ChatMessageDTO;
import llustmarket.artmarket.web.dto.chat.ChatMessageRequestDTO;
import llustmarket.artmarket.web.mapper.chat.ChatMessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ModelMapper modelMapper;
    private final ChatMessageMapper chatMessageMapper;

    @Override
    public ChatMessageRequestDTO registerChatMessage(ChatMessageDTO dto) {
        log.info("chat message register ");
        ChatMessage vo = modelMapper.map(dto, ChatMessage.class);
        chatMessageMapper.insertOne(vo);
        log.info(vo.getChatMessageId());
        ChatMessageRequestDTO requestDTO = searchChatMessageOne(vo.getChatMessageId());
        log.info("requestDTO : {}" ,requestDTO);

        return requestDTO;
    }

    @Override
    public ChatMessageRequestDTO searchChatMessageOne(long chatMessageId) {
        log.info("chat message search chatMessageId ");
        ChatMessage chatMessage = chatMessageMapper.selectOneByMessageId(chatMessageId);
        ChatMessageRequestDTO requestDTO = modelMapper.map(chatMessage, ChatMessageRequestDTO.class);
        return requestDTO;
    }

    @Override
    public List<ChatMessageRequestDTO> searchChatMessageList(long chatRoomId) {
        List<ChatMessage> chatMessages = chatMessageMapper.selectByRoomId(chatRoomId);
        List<ChatMessageRequestDTO> chatMessageDTOS = chatMessages.stream().map(item -> modelMapper.map(item, ChatMessageRequestDTO.class)).collect(Collectors.toList());
        return chatMessageDTOS;
    }

    @Override
    public List<ChatMessageRequestDTO> searchChatMessageListMore(long chatRoomId, long lastNum) {
        return null;
    }
}
