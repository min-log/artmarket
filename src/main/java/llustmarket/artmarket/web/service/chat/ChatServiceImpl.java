package llustmarket.artmarket.web.service.chat;


import llustmarket.artmarket.domain.chat.Chat;
import llustmarket.artmarket.domain.chat.MessageType;
import llustmarket.artmarket.domain.file.FileType;
import llustmarket.artmarket.web.dto.chat.ChatDTO;
import llustmarket.artmarket.web.dto.chat.ChatMessageResponseDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomResponseDTO;
import llustmarket.artmarket.web.dto.file.FileDTO;
import llustmarket.artmarket.web.dto.member.MemberDTO;
import llustmarket.artmarket.web.dto.order.OrderDTO;
import llustmarket.artmarket.web.dto.product.ProductDTO;
import llustmarket.artmarket.web.mapper.chat.ChatMapper;
import llustmarket.artmarket.web.service.file.FileService;
import llustmarket.artmarket.web.service.member.MemberService;
import llustmarket.artmarket.web.service.order.OrderService;
import llustmarket.artmarket.web.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    private final OrderService orderService;


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
                .chatLeaveDate(LocalDateTime.now())
                .build();


        // 2. 기존 대화 참여 내역이 있는지 확인
        ChatDTO dtoValue = searchOneExist(chatDTO);
        if(dtoValue == null){// 3. 내역이 존재 하지 않을 시
            log.info("내역 존재 x");
            // 작가 정보
            ProductDTO productDTO = productService.selectOne(roomDTO.getProductId());
            MemberDTO authorMember = memberService.selectOne(productDTO.getMemberId());
            roomDTO.setChatFromId(authorMember.getMemberId()); // 받는사람
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
                    .chatLeaveDate(LocalDateTime.now())
                    .build();
            chatMapper.insertOne(authorChatDto);

            chatRoomDTO = ChatRoomResponseDTO.builder()
                    .chatRoomId(chatRoom.getChatRoomId())
                    .chatProudct(chatRoom.getProductId())
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
    public void updateChatLastDate(long roomId, long memberId) {
        LocalDateTime lastDate = LocalDateTime.now();
        int i = chatMapper.updateChatLastDate(Chat.builder().chatRoomId(roomId).memberId(memberId).chatLeaveDate(lastDate).build());
    }

    @Override
    public int updateChatStatus(long roomId, long memberId,boolean status) {
        int i = chatMapper.updateChatStatus(Chat.builder().chatRoomId(roomId).memberId(memberId).chatStatus(status).build());
        return i;
    }

    @Override
    public void chatListStatusChange(long roomId) {
        log.info("# 룸에 참여한 회원들 상태 변경 ");



    }


    @Override
    public ChatDTO searchOneExist(ChatDTO chatDTO) {
        log.info("# 대화 내역 존재 확인");
        Chat vo = modelMapper.map(chatDTO, Chat.class);
        try{
            vo = chatMapper.selectOneExist(vo);
            if(vo == null) return null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return modelMapper.map(vo, ChatDTO.class);
    }

    public Boolean searchOneChatStatus(long roomId,long memberId) {
        log.info("# 상태값 가져오기");
        Chat chat = chatMapper.selectByRoomIdAndMemberId(Chat.builder().chatRoomId(roomId).memberId(memberId).build());
        return chat.isChatStatus();
    }


    @Override
    public ChatRoomResponseDTO searchOneRoomId(long roomId) {
        log.info("# 채팅방 상세 내역");
        //chat_from_id
        ChatRoomDTO chatRoom = chatRoomService.searchChatRoomId(roomId);
        // 대화 내역 조회 및 전달
        List<ChatMessageResponseDTO> chatMessageDTOS = messageService.searchChatMessageList(chatRoom.getChatRoomId());
        ChatRoomResponseDTO chatRoomDTO = ChatRoomResponseDTO.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .chatProudct(chatRoom.getProductId())
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




    @Transactional
    @Override
    public boolean removeStateChat(long roomId, long memberId) {
        log.info("# 사용자 대화참여 상태값 변경");
        // 주문내역의 마감기간과 현제 날짜 비교하여 마감 후 일시 내 목록에서 숨김처리 가능
        // 1. 주문내역 확인
        ChatRoomDTO chatRoomDTO = chatRoomService.searchChatRoomId(roomId);
        long productId = chatRoomDTO.getProductId(); // 상품id
        // 1-1. 내가 주문한 내역이 있는지 확인
        OrderDTO order = orderService.selectOne(productId, memberId);
        long otherMemberId = chatRoomDTO.getChatToId(); // 상대방ID
        if(memberId == chatRoomDTO.getChatToId()) otherMemberId = chatRoomDTO.getChatFromId();
        if(order == null){
            // 1-2. 내가 주문한 내역이 아닐경우, 상대방이 주문한 내역 확인
            OrderDTO orderOtherDTO = orderService.selectOne(productId, otherMemberId);
            order = orderOtherDTO;
        }else if(order != null){
            //2. 주문한 내역이 존재, 마감기한과 현제 날짜 비교
            // 마감기간 보다 현제 날짜가 이후 이면 chat_status true로 변경 -> 리스트에서 숨김처리
            LocalDateTime deadline = order.getDeadline();
            // deadline 이 현제보다 과거일 경우 true
            boolean dateBefore = deadline.isBefore(LocalDateTime.now());
            if(dateBefore == false) { // 삭제 불가능
                return false;
            }
        }

        // 2-1. 나의 목록 상태 변경
        int st = updateChatStatus(roomId, memberId,true);
        // 3. 해당 룸에 포함되는 사람들의 chat 상태 비교 모두다 true 일경우 실제 삭제 작업
        //3-1. 상대방의 상태 값 가져오기
        Boolean memberStatus = searchOneChatStatus(roomId, otherMemberId);
        if(st == 1 && memberStatus == true){
            // 모두다 삭제 일 경우 실제 삭제 로직
            boolean result = removeChat(roomId, memberId, otherMemberId);
            return result;
        }
        return true;
    }


    @Transactional
    @Override
    public boolean removeChat(long roomId, long memberId, long otherMemberId) {
        log.info("# 실제 채팅 참여내역 삭제 로직");
        int result = 0;
        //1. 메시지 내역 삭제
        //1-1. 룸에 해당하는 메시지 내역 모두 가져와서 삭제
        messageService.deleteMessageList(roomId);
        //2. 채팅 참여 내역 삭제
        int deleteChat = chatMapper.deleteChat(Chat.builder().chatRoomId(roomId).memberId(memberId).build());
        int deleteChatOther = chatMapper.deleteChat(Chat.builder().chatRoomId(roomId).memberId(otherMemberId).build());
        //3. 채팅 룸 삭제
        if(deleteChat == 1 && deleteChatOther == 1){
            result = chatRoomService.deleteChat(roomId);
        }
        if(result == 1) return true;
        return false;
    }




}