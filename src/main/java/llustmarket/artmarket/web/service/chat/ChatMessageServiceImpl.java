package llustmarket.artmarket.web.service.chat;


import llustmarket.artmarket.domain.chat.ChatMessage;
import llustmarket.artmarket.domain.chat.MessageType;
import llustmarket.artmarket.domain.file.FileType;
import llustmarket.artmarket.domain.file.FileVO;
import llustmarket.artmarket.web.dto.chat.ChatMessageRequestDTO;
import llustmarket.artmarket.web.dto.file.ChatFileUploadDTO;
import llustmarket.artmarket.web.dto.chat.ChatMessageResponseDTO;
import llustmarket.artmarket.web.dto.file.FileDTO;
import llustmarket.artmarket.web.mapper.chat.ChatMessageMapper;
import llustmarket.artmarket.web.mapper.file.FileMapper;
import llustmarket.artmarket.web.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final FileMapper fileMapper;

    @Transactional
    @Override
    public ChatMessageResponseDTO registerChatMessage(ChatMessageRequestDTO dto) {
        log.info("# 일반 메시지 저장");
        log.info("dto : {}",dto);
        ChatMessage chatMessage = messageDTOToVO(dto);
        log.info("chatMessage : {}",chatMessage);
        chatMessageMapper.insertOne(chatMessage);
        log.info(chatMessage.getChatMessageId());

        ChatMessageResponseDTO requestDTO = searchChatMessageOne(chatMessage.getChatMessageId());
        return requestDTO;
    }



    @Transactional
    @Override
    public ChatMessageResponseDTO registerChatFileMessage(ChatMessageRequestDTO dto) {
        log.info("# 채팅 메시지 파일 저장 및 메시지 내용 저장");
        // 1. 파일 바이트[]를 가지고 있는 DTO - > MultipartFile객체를 상속받은 파일DTO로 변경
        // 2. 파일DTO 객체를 MultipartFile 객체로 변경
        ChatFileUploadDTO file = dto.getSendChatFile();
        MultipartFile multipartFile = fileService.fileConversionMultipartFile(file);

        // 3. MultipartFile 을 실제 경로에 저장 - 저장된 파일이름 필요.
        FileDTO fileDTO = fileService.fileRegister(FileType.CHAT, multipartFile);


        // 4. 일반 메시지 저장
        log.info("메시지 저장 dto: {}",dto);
        log.info("메시지 내용: {}",dto.getSendChatMsg());
        ChatMessage chatMessage = messageDTOToVO(dto);
        chatMessageMapper.insertOne(chatMessage);

        // 5. FILE DB 저장
        // chatMessage Id 경로 추가
        fileDTO.setFileTypeId(chatMessage.getChatMessageId());
        fileMapper.insertOne(modelMapper.map(fileDTO, FileVO.class));

        // 7. 저장된 메시지 내용
        ChatMessageResponseDTO requestDTO = searchChatMessageOne(chatMessage.getChatMessageId());
        log.info("requestDTO 저장된 최종 내용 확인 :{}",requestDTO);

        return requestDTO;
    }

    @Override
    public ChatMessageResponseDTO searchChatMessageOne(long chatMessageId) {
        log.info("# 저장된 하나의 메시지 내용 전달 ");
        ChatMessage chatMessage = chatMessageMapper.selectOneByMessageId(chatMessageId);
        ChatMessageResponseDTO chatMessageResponseDTO = messageVOToResultDTO(chatMessage);
        // 파일이 존재할 경우
        if(chatMessageResponseDTO.getChatType().equals(String.valueOf(MessageType.FILE))){
            chatMessageResponseDTO = messageFileSet(chatMessageId, chatMessageResponseDTO);
        }

        return chatMessageResponseDTO;
    }

    @Override
    public List<ChatMessageResponseDTO> searchChatMessageList(long chatRoomId) {
        log.info("# 채팅방 들어갔을때 메시지 리스트");
        List<ChatMessage> chatMessages = chatMessageMapper.selectByRoomId(chatRoomId);
        List<ChatMessageResponseDTO> chatMessageDTOS = chatMessages.stream().map(item -> {
            ChatMessageResponseDTO chatMessageResponseDTO = messageVOToResultDTO(item);
            // 파일이 존재할 경우
            if(chatMessageResponseDTO.getChatType().equals(String.valueOf(MessageType.FILE))){
                chatMessageResponseDTO = messageFileSet(item.getChatMessageId(), chatMessageResponseDTO);
            }
            return chatMessageResponseDTO;
        }).collect(Collectors.toList());
        return chatMessageDTOS;
    }


    public ChatMessageResponseDTO messageFileSet(long chatMessageId,ChatMessageResponseDTO chatMessageResponseDTO ){
        //# 파일이 존재할 경우
        String chatPath = String.valueOf(FileType.CHAT);
        FileVO fileVO = FileVO.builder()
                .filePath(chatPath)
                .fileTypeId(chatMessageId)
                .build();
        FileVO file = fileMapper.selectOnePathAndId(fileVO);
        chatMessageResponseDTO.setChatFile("/file/find?filePath=" + file.getFilePath() + "&fileTypeId=" +file.getFileTypeId());
        chatMessageResponseDTO.setChatFileName(file.getFileOriginName());
        return chatMessageResponseDTO;
    }

}
