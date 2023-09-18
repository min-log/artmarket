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
    private long askProduct;
    private long askMember;
}