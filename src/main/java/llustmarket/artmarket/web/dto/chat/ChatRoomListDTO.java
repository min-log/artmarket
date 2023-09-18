package llustmarket.artmarket.web.dto.chat;

import lombok.*;

import java.time.LocalDateTime;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class ChatRoomListDTO {
    // 화면에 전달되는 채팅 룸 리스트의 내부 정보
    private Long chatRoomId; //룸아이디
    private String lastMsg; // 마지막 메시지 내용
    private LocalDateTime lastMsgDate; // 마지막 메시지 전송 시간
    private String whoSend; // 보낸사람 닉네임
    private String identity; // 보낸사람 권한
    private String sendProfileImg;  // 보낸사람 프로필이미지 /file_fath/file_name
}