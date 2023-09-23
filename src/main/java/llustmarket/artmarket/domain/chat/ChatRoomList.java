package llustmarket.artmarket.domain.chat;

import lombok.*;

import java.time.LocalDateTime;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatRoomList {

    // chatRoom 정보
    private long productId;
    private long chatToId;
    private long chatFromId;
    private LocalDateTime chatRoomDate;
    private LocalDateTime chatRoomLastDate; // 마지막 메시지 전송시간
    private String chatRoomMsg; // 마지막 메시지

    // 현재회원의 chat 정보
    private long chatId;
    private long chatRoomId;
    private long memberId;
    private LocalDateTime chatLeaveDate;

}
