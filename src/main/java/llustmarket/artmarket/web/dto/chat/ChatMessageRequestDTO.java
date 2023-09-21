package llustmarket.artmarket.web.dto.chat;


import llustmarket.artmarket.web.dto.file.ChatFileUploadDTO;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class ChatMessageRequestDTO {
    // 받아오는 메시지 데이터
    private long sendChatRoomId;//방 아이디
    private long sendChatSender; //보내는 아이디
    private String sendChatMsg; //메시지
    private ChatFileUploadDTO sendChatFile;
    private String chatType; // 메시지 타입

    // 채팅 방 닫기
    private long closeChatMember;
    private long closeChatRoomId;

}