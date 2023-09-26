package llustmarket.artmarket.web.dto.chat;


import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatSessionDTO {
    private long memberId;
    private long chatRoomID;
}
