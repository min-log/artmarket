package llustmarket.artmarket.web.service.chat;


import llustmarket.artmarket.domain.file.FileType;
import llustmarket.artmarket.domain.chat.ChatMessage;
import llustmarket.artmarket.web.dto.file.FileUploadDTO;
import llustmarket.artmarket.web.dto.chat.ChatMessageDTO;
import llustmarket.artmarket.web.dto.chat.ChatMessageRequestDTO;
import llustmarket.artmarket.web.mapper.chat.ChatMessageMapper;
import llustmarket.artmarket.web.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ModelMapper modelMapper;
    private final ChatMessageMapper chatMessageMapper;
    private final FileService fileService;

    @Override
    public ChatMessageRequestDTO registerChatMessage(ChatMessageDTO dto) {
        // 메시지 저장
        log.info("chat message register ");
        ChatMessage vo = modelMapper.map(dto, ChatMessage.class);
        chatMessageMapper.insertOne(vo);
        log.info(vo.getChatMessageId());
        ChatMessageRequestDTO requestDTO = searchChatMessageOne(vo.getChatMessageId());
        log.info("requestDTO : {}" ,requestDTO);

        return requestDTO;
    }

    @Override
    public ChatMessageRequestDTO registerChatFileMessage(ChatMessageDTO dto) {
        // 파일을 가지고 있는 메시지 저장
        // 1. 파일 바이트[]를 가지고 있는 DTO - > MultipartFile객체를 상속받은 파일DTO로 변경
        // 2. 파일DTO 객체를 MultipartFile 객체로 변경
        FileUploadDTO file = dto.getFile();
        MultipartFile multipartFile = fileService.fileConversionMultipartFile(file);
        log.info("multipartFile : {}",multipartFile);

        //log.info("fileMultipart : {}",fileMultipart);

        // 3. 메시지 저장
            // 저장된 고유 번호 가져오기(long)

        // 4. MultipartFile 을 실제 경로에 저장 - 저장된 파일이름 필요.
        fileService.fileRegister(FileType.CHAT,multipartFile);

        // 5. FILE DB 저장



        return null;
    }

    @Override
    public ChatMessageRequestDTO searchChatMessageOne(long chatMessageId) {
        // 하나의 메시지 내용 전달
        log.info("chat message search chatMessageId ");
        ChatMessage chatMessage = chatMessageMapper.selectOneByMessageId(chatMessageId);
        ChatMessageRequestDTO requestDTO = modelMapper.map(chatMessage, ChatMessageRequestDTO.class);
        return requestDTO;
    }

    @Override
    public List<ChatMessageRequestDTO> searchChatMessageList(long chatRoomId) {
        // 채팅방 들어갔을때 매시지 리스트
        log.info("chat message list start ");
        List<ChatMessage> chatMessages = chatMessageMapper.selectByRoomId(chatRoomId);
        List<ChatMessageRequestDTO> chatMessageDTOS = chatMessages.stream().map(item -> modelMapper.map(item, ChatMessageRequestDTO.class)).collect(Collectors.toList());
        return chatMessageDTOS;
    }

    @Override
    public List<ChatMessageRequestDTO> searchChatMessageListMore(long chatRoomId, long lastNum) {
        return null;
    }
}
