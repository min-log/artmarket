package llustmarket.artmarket.domain.chat;

import lombok.*;

import java.time.LocalDateTime;
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ChatMessage {

    private long chatMessageId;//고유 번호
    private long chatRoomId;//방 아이디
    private long memberId; //보내는 사람
    private LocalDateTime chatMessageDate; //생성날짜
    private String message; //메시지
    private String chatMessageType; // 메시지 타입

}
