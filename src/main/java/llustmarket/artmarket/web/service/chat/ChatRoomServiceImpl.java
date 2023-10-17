package llustmarket.artmarket.web.service.chat;


import llustmarket.artmarket.domain.chat.ChatRoom;
import llustmarket.artmarket.domain.chat.ChatRoomList;
import llustmarket.artmarket.domain.file.FileType;
import llustmarket.artmarket.domain.file.FileVO;
import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.web.dto.chat.ChatRoomDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomListDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomListResponseDTO;
import llustmarket.artmarket.web.mapper.chat.ChatRoomMapper;
import llustmarket.artmarket.web.mapper.file.FileMapper;
import llustmarket.artmarket.web.mapper.member.MemberMapper;
import llustmarket.artmarket.web.service.DateTimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Log4j2
@RequiredArgsConstructor
@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ModelMapper modelMapper;
    private final ChatRoomMapper chatRoomMapper;
    private final MemberMapper memberMapper;
    private final FileMapper fileMapper;
    private final DateTimeService dateTimeService;


    @Override
    public ChatRoomDTO registerChatRoom(ChatRoomDTO dto) {
        log.info("# 채팅룸 생성 ------------------------------");
        ChatRoom vo = modelMapper.map(dto, ChatRoom.class);
        chatRoomMapper.insertOne(vo);
        // 생성한 값 전달
        dto.setChatRoomId(vo.getChatRoomId());
        return dto;
    }

    @Override
    public void updateChatRoom(long roomId, String message, String date) {
        ChatRoom roomVO = ChatRoom.builder().chatRoomId(roomId).chatRoomMsg(message).chatRoomLastDate(dateTimeService.StringToDate(date)).build();
        int result = chatRoomMapper.updateOne(roomVO);
    }

    @Override
    public int deleteChat(long chatRoomId) {
        int i = chatRoomMapper.deleteChatRoom(chatRoomId);
        return i;
    }


    @Override
    public ChatRoomDTO searchChatRoomId(long chatRoomId) {
        log.info("# 채팅 룸 전달 ");
        ChatRoom chatRoom = chatRoomMapper.selectOneId(chatRoomId);
        return modelMapper.map(chatRoom, ChatRoomDTO.class);
    }


    @Transactional
    @Override
    public ChatRoomListResponseDTO searchChatRoomList(long memberId) {
        List<ChatRoomList> chatRoomLists = chatRoomMapper.selectListByRoomId(memberId);
        // 1. 전달될 룸 리스트
        List<ChatRoomListDTO> roomListDTO = new ArrayList<>();
        chatRoomLists.forEach(item -> {
            // 1-3. ChatRoom 정보 가져오기
            //  1-4. 전달될 객체
            ChatRoomListDTO chatRoomDTO = ChatRoomListDTO.builder()
                    .chatRoomId(item.getChatRoomId())
                    .chatMsg(item.getChatRoomMsg())
                    .chatDate(item.getChatRoomLastDate())
                    .build();

            //  1-5. 전달될 객체에 상대방의 회원정보 추가
            Member memberYou;
            // 1) 상대 회원 정보 찾기
            if (item.getChatToId() == memberId) {
                memberYou = memberMapper.selectOneByMemberId(item.getChatFromId());
            } else {
                memberYou = memberMapper.selectOneByMemberId(item.getChatToId());
            }
            // 2) 프로필 이미지 : 파일 객체가 존재할 시 추가
            // 파일의 경로, 경로 아이디
            FileVO fileProfile = FileVO.builder().filePath(String.valueOf(FileType.PROFILE)).fileTypeId(memberYou.getMemberId()).build();
            FileVO memberProfile = fileMapper.selectOnePathAndId(fileProfile);
            if (memberProfile != null)
                chatRoomDTO.setChatSenderProfile("/file/find/" + memberProfile.getFilePath() + "/" + memberProfile.getFileTypeId());
            chatRoomDTO.setChatSender(memberYou.getNickname());
            chatRoomDTO.setChatSenderIdtity(memberYou.getIdentity());

            //  1-6. 전달될 룸 리스트 객체에 추가
            roomListDTO.add(chatRoomDTO);
        });
        // 2. 마이페이지 보유 회원 정보 추가
        Member memberMe = memberMapper.selectOneByMemberId(memberId);
        ChatRoomListResponseDTO myPageDTO = ChatRoomListResponseDTO.builder()
                .intro(memberMe.getMemberIntro())
                .nickname(memberMe.getNickname())
                .build();

        // 2-1. 회원 프로필
        FileVO memberProfile = fileMapper.selectOnePathAndId(FileVO.builder().filePath(String.valueOf(FileType.PROFILE)).fileTypeId(memberMe.getMemberId()).build());
        if (memberProfile != null)
            myPageDTO.setProfileImg("" + File.separator + "upload" + File.separator + memberProfile.getFilePath() + File.separator + memberProfile.getFileName());
        myPageDTO.setMyChatRooms(roomListDTO); // 룸 리스트 추가
        return myPageDTO;
    }

}
