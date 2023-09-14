package llustmarket.artmarket.web.service.chat;


import llustmarket.artmarket.domain.chat.Chat;
import llustmarket.artmarket.domain.member.MemberType;
import llustmarket.artmarket.web.dto.chat.ChatDTO;
import llustmarket.artmarket.web.dto.chat.ChatMessageResponseDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomDTO;
import llustmarket.artmarket.web.dto.member.MemberDTO;
import llustmarket.artmarket.web.mapper.chat.ChatMapper;
import llustmarket.artmarket.web.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {
    private final ModelMapper modelMapper;
    private final ChatMapper chatMapper;
    private final ChatRoomService chatRoomService;
    private final ChatMessageService messageService;
    private final MemberService memberService;

    @Transactional
    @Override
    public ChatRoomDTO registerChat(MemberDTO member, ChatRoomDTO roomDTO) {

        log.info("register Chat ---------------------");
        // 1. 참여정보 생성 - 저장 필수 정보 룸 아이디, 상품아이디, 현재 사용자 아이디, 사용자 권한
        ChatDTO chatDTO = ChatDTO.builder()
                .productId(roomDTO.getProductId()) // 상품 아이디
                .memberId(member.getMemberId()) // 사용자 아이디
                .chatIdentity(member.getIdentity()) // 사용자 권한
                .build();

        ChatRoomDTO chatRoomDTO; // 전달할 room

        // 방이름 (상대방 닉네임) 전달 -- 작가 아이디를 통해 가져오기
        MemberDTO memberDTO = memberService.selectOne(roomDTO.getChatToId());
        String roomName = memberDTO.getNickname();
        String memberNickname = member.getNickname(); // 사용자 닉네임


        // 2. 기존 대화 참여 내역이 있는지 확인
        ChatDTO dtoValue = searchOneExist(chatDTO);
        if(dtoValue == null){ // 3. 내역이 존재 하지 않을 시
            // 1. 룸생성
            chatRoomDTO = chatRoomService.registerChatRoom(roomDTO);
            chatRoomDTO.setChatRoomName(roomName); // 방이름 추가
            chatRoomDTO.setWriter(memberNickname); // 작성자 이름 추가

            // 1-1. 대화참여하는 곳에 룸 아이디 추가
            chatDTO.setChatRoomId(chatRoomDTO.getChatRoomId());
            // 2. 대화 참여 생성 - 문의자 / 작가
            // 2-1 . 보내는 사람 참여 생성
            chatMapper.insertOne(modelMapper.map(chatDTO,Chat.class));
            // 2-2. 작가 참여 생성
            Chat authorChatDto = Chat.builder()
                    .chatRoomId(chatRoomDTO.getChatRoomId())
                    .chatIdentity(String.valueOf(MemberType.AUTHOR))
                    .memberId(roomDTO.getChatToId())
                    .productId(roomDTO.getProductId())
                    .build();
            chatMapper.insertOne(authorChatDto);

            return chatRoomDTO;
        }
        log.info("내역 존재 시");
        chatRoomDTO = chatRoomService.searchOneExist(roomDTO);
        chatRoomDTO.setChatRoomName(roomName); // 방이름 추가
        chatRoomDTO.setWriter(memberNickname); // 작성자 이름 추가

        // 대화 내역 조회 및 전달
        List<ChatMessageResponseDTO> chatMessageDTOS = messageService.searchChatMessageList(chatRoomDTO.getChatRoomId());

        chatRoomDTO.setMsglist(chatMessageDTOS);


        return chatRoomDTO;
    }


    @Override
    public ChatDTO searchOneExist(ChatDTO chatDTO) {
        log.info("chat search exist--------------------");
        Chat vo = modelMapper.map(chatDTO, Chat.class);
        try{
            vo = chatMapper.selectOneExist(vo);
            if(vo == null) return null;
        }catch (Exception e){
            e.printStackTrace();
        }

        return modelMapper.map(vo, ChatDTO.class);
    }

    @Override
    public List<ChatDTO> findChatRoomById(long memberId) {
        List<Chat> chats = chatMapper.selectByMemberId(Chat.builder().memberId(memberId).build());
        List<ChatDTO> dtoList = chats.stream().map(item -> modelMapper.map(item, ChatDTO.class)).collect(Collectors.toList());
        return dtoList;
    }


}