package llustmarket.artmarket.web.service.chat;


import llustmarket.artmarket.domain.chat.Chat;
import llustmarket.artmarket.web.dto.chat.ChatDTO;
import llustmarket.artmarket.web.dto.chat.ChatMessageResponseDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomResponseDTO;
import llustmarket.artmarket.web.dto.member.MemberDTO;
import llustmarket.artmarket.web.dto.product.ProductDTO;
import llustmarket.artmarket.web.mapper.chat.ChatMapper;
import llustmarket.artmarket.web.service.member.MemberService;
import llustmarket.artmarket.web.service.product.ProductService;
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
    private final ProductService productService;

    @Transactional
    @Override
    public ChatRoomResponseDTO registerChat(long askMemberId, ChatRoomDTO roomDTO) {
        log.info("# 채팅 참여 정보생성");
        // 1. 참여정보 생성 - 저장 필수 정보 룸 아이디, 상품아이디, 현재 사용자 아이디, 사용자 권한
        // 문의 회원
        MemberDTO askMember = memberService.selectOne(askMemberId);
        // room - DB 저장
        roomDTO.setChatToId(askMember.getMemberId()); // 보내는 사람
        // room - 프론트 전달
        ChatRoomResponseDTO chatRoomDTO;

        // 문의 회원 참여 정보
        ChatDTO chatDTO = ChatDTO.builder()
                .productId(roomDTO.getProductId()) // 상품 아이디
                .memberId(askMemberId) // 사용자 아이디
                .chatIdentity(askMember.getIdentity()) // 사용자 권한
                .build();
        // 작가 정보
        ProductDTO productDTO = productService.selectOne(roomDTO.getProductId());
        MemberDTO authorMember = memberService.selectOne(productDTO.getMemberId());
        roomDTO.setChatFromId(authorMember.getMemberId()); // 받는사람

        // 2. 기존 대화 참여 내역이 있는지 확인
        ChatDTO dtoValue = searchOneExist(chatDTO);
        if(dtoValue == null){// 3. 내역이 존재 하지 않을 시
            log.info("내역 존재 x");
            // 1. 룸생성 - DB
            // 방이름 (상대방 닉네임) 전달 -- 작가 아이디를 통해 가져오기
            ChatRoomDTO chatRoom = chatRoomService.registerChatRoom(roomDTO);
            // 1-1. 대화참여하는 곳에 룸 아이디 추가
            chatDTO.setChatRoomId(chatRoom.getChatRoomId());
            // 2. 대화 참여 생성 - 문의자 / 작가
            // 2-1 . 보내는 사람 참여 생성
            chatMapper.insertOne(modelMapper.map(chatDTO,Chat.class));
            // 2-2. 작가 참여 생성
            Chat authorChatDto = Chat.builder()
                    .chatRoomId(chatRoom.getChatRoomId())
                    .chatIdentity(authorMember.getIdentity())
                    .memberId(authorMember.getMemberId())
                    .productId(roomDTO.getProductId())
                    .build();
            chatMapper.insertOne(authorChatDto);

            chatRoomDTO = ChatRoomResponseDTO.builder()
                    .chatRoomId(chatRoom.getChatRoomId())
                    .productId(chatRoom.getProductId())
                    .build();

            return chatRoomDTO;
        }
        log.info("내역 존재 o");
        //chat_from_id
        ChatRoomDTO chatRoom = chatRoomService.searchChatRoomId(dtoValue.getChatRoomId());
        ChatRoomResponseDTO chatRoomResponseDTO = searchOneRoomId(chatRoom.getChatRoomId());
        return chatRoomResponseDTO;
    }


    @Override
    public ChatDTO searchOneExist(ChatDTO chatDTO) {
        log.info("# 대화 내역 존재 확인");
        log.info("chatDTO : {}",chatDTO);
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
    public ChatRoomResponseDTO searchOneRoomId(long roomId) {
        log.info("# 채팅방 상세 내역");
        //chat_from_id
        ChatRoomDTO chatRoom = chatRoomService.searchChatRoomId(roomId);
        log.info("chatRoom : {}",chatRoom);
        // 대화 내역 조회 및 전달
        List<ChatMessageResponseDTO> chatMessageDTOS = messageService.searchChatMessageList(chatRoom.getChatRoomId());
        ChatRoomResponseDTO chatRoomDTO = ChatRoomResponseDTO.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .productId(chatRoom.getProductId())
                .chatList(chatMessageDTOS)
                .build();

        return chatRoomDTO;
    }

    @Override
    public List<ChatDTO> searchChatAllByMemberId(long memberId) {
        log.info("# 사용자의 대화참여 리스트 찾기");
        List<Chat> chats = chatMapper.selectByMemberId(Chat.builder().memberId(memberId).build());
        List<ChatDTO> dtoList = chats.stream().map(item -> modelMapper.map(item, ChatDTO.class)).collect(Collectors.toList());
        return dtoList;
    }


}