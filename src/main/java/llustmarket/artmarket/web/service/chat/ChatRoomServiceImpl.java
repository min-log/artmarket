package llustmarket.artmarket.web.service.chat;



import llustmarket.artmarket.domain.chat.ChatRoom;
import llustmarket.artmarket.domain.file.File;
import llustmarket.artmarket.domain.file.FileType;
import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.web.dto.chat.ChatDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomListDTO;
import llustmarket.artmarket.web.dto.chat.ChatRoomMyPageDTO;
import llustmarket.artmarket.web.mapper.chat.ChatRoomMapper;
import llustmarket.artmarket.web.mapper.file.FileMapper;
import llustmarket.artmarket.web.mapper.member.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Log4j2
@RequiredArgsConstructor
@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ModelMapper modelMapper;
    private final ChatRoomMapper chatRoomMapper;
    private final MemberMapper memberMapper;
    private final FileMapper fileMapper;


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

    @Transactional
    @Override
    public ChatRoomMyPageDTO searchUserList(long memberId, List<ChatDTO> chatDTOS) {
        log.info("# 전체 채팅 룸 리스트 정보 전달");


        // 1. 전달될 룸 리스트
        List<ChatRoomListDTO> roomListDTO = new ArrayList<>();

        // 1-2. 회원이 참여했던 채팅 방 정보 가져오기
        chatDTOS.stream().forEach(item->{
            // 1-3. ChatRoom 정보 가져오기
            ChatRoom chatRoom = chatRoomMapper.selectOneId(item.getChatRoomId());
            //  1-4. 전달될 객체
            ChatRoomListDTO chatRoomDTO = ChatRoomListDTO.builder()
                    .chatRoomId(chatRoom.getChatRoomId())
                    .lastMsg(chatRoom.getChatRoomMsg())
                    .lastMsgDate(chatRoom.getChatRoomLastDate())
                    .build();
            //  1-5. 전달될 객체에 회원정보 추가
            Member memberYou;
            if(chatRoom.getChatToId() == memberId){
                // chatFromId 회원 아이디 값으로 파일 맵퍼로부터 찾아서 프로필이미지 경로 전송하기
                memberYou = memberMapper.selectOneByMemberId(chatRoom.getChatFromId());
            }else{
                // chatToId 회원 아이디 값으로 파일 맵퍼로부터 찾아서 프로필이미지 경로 전송하기
                memberYou = memberMapper.selectOneByMemberId(chatRoom.getChatToId());
            }
            // 프로필 이미지 : 파일 객체가 존재할 시 추가
                // 파일의 경로, 경로 아이디
            File  memberProfile = fileMapper.selectOnePathAndId(File.builder().filePath(String.valueOf(FileType.PROFILE)).fileTypeId(memberYou.getMemberId()).build());
            if(memberProfile != null) chatRoomDTO.setSendProfileImg(memberProfile.getFilePath() + "/" + memberProfile.getFileName());
            chatRoomDTO.setWhoSend(memberYou.getNickname());
            chatRoomDTO.setIdentity(memberYou.getIdentity());
            //  1-6. 전달될 객체 리스트에 객체 추가
            roomListDTO.add(chatRoomDTO);
        });


        // 2. 마이페이지 보유 회원 정보 추가
        Member memberMe = memberMapper.selectOneByMemberId(memberId);
        ChatRoomMyPageDTO myPageDTO = ChatRoomMyPageDTO.builder()
                .intro(memberMe.getMemberIntro())
                .nickname(memberMe.getNickname())
                .build();

        // 2-1. 회원 프로필
        File  memberProfile = fileMapper.selectOnePathAndId(File.builder().filePath(String.valueOf(FileType.PROFILE)).fileTypeId(memberMe.getMemberId()).build());
        if(memberProfile != null) myPageDTO.setProfileImg(memberProfile.getFilePath()+"/"+memberProfile.getFileTypeId());
        myPageDTO.setMyChatRooms(roomListDTO); // 룸 리스트 추가


        return myPageDTO;
    }

}
