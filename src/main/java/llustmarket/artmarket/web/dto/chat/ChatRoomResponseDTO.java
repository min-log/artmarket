package llustmarket.artmarket.web.dto.chat;

import lombok.*;

import java.util.List;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class ChatRoomResponseDTO {
    // 채팅방 생성 및 입장시 전달
    private long chatRoomId; //고유 번호
    private long chatProudct; //주문 상품 페이지 고유 번호
    private List<ChatMessageResponseDTO> chatList;
}