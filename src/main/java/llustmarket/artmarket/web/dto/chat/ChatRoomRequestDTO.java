package llustmarket.artmarket.web.dto.chat;

import lombok.*;

import java.util.List;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class ChatRoomRequestDTO {
    // 상품 페이지
    private long askProduct;
    private long askMember;


    // 마이 페이지
    private long clickChatId;
    private long clickMember;


    // 마이 페이지 삭제
    private long remChatMember;
    private long remChatRoomId;


}