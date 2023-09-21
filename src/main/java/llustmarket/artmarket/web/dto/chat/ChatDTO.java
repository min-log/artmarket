package llustmarket.artmarket.web.dto.chat;


import lombok.*;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatDTO {

    private long chatId;//고유 번호
    private long chatRoomId; //대화방번호
    private long productId; //주문 상품 페이지 고유 번호
    private long memberId; //회원번호
    private String chatIdentity; //회원권한
    private LocalDateTime chatDate; //처음 입장 날짜
    private LocalDateTime chatLeaveDate; //마지막 나간 날짜
    private boolean chatStatus; //리스트에서 숨김 처리

}
