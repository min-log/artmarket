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
public class ChatRoomMyPageDTO {
    // 현재 회원의 정보
    private String nickname; //닉네임
    private String intro; // 회원 인트로 메시지
    private String profileImg; // 회원 프로필이미지
    private List<ChatRoomListDTO> myChatRooms; // 채팅 리스트
}