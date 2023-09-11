package llustmarket.artmarket.web.dto.chat;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class ChatRoomDTO {
    private long chatRoomId; //고유 번호
    private long productId; //주문 상품 페이지 고유 번호
    private long chatFromId; // 보내는 사람
    private long chatToId; // 받는 사람 - 작가
    private String chatRoomName ; // 상대방 닉네임

    private LocalDateTime chatRoomDate; //생성 날짜
    private LocalDateTime chatRoomLastDate; // 마지막 메시지 날짜
    private String chatRoomMsg; //마지막 메시지

    private String writer; //보내는 사람 이름
    private List<ChatMessageRequestDTO> msglist;



//
//    public static ChatRoomDTO create(String name) {
//        ChatRoomDTO chatRoom = new ChatRoomDTO();
//        chatRoom.chatRoomId = UUID.randomUUID().toString();
//        chatRoom.chatRoomName = name;
//        return chatRoom;
//    }
}