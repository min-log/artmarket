package llustmarket.artmarket.domain.chat;

import lombok.*;

import java.time.LocalDateTime;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ChatRoom {
    private long chatRoomId; //고유 번호
    private long productId; //주문 상품 페이지 고유 번호
    private long chatFromId; // 보내는 사람 
    private long chatToId; // 받는 사람 - 작가

    private LocalDateTime chatRoomDate; //생성 날짜
    private LocalDateTime chatRoomLastDate; // 마지막 메시지 날짜
    private String chatRoomMsg; //마지막 메시지

}
