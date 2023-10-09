package llustmarket.artmarket.web.dto.chat;

import lombok.*;

import java.util.List;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class ChatRoomListResponseDTO {
    // 현재 회원의 정보 와 채팅 룸 리스트
    private String nickname; //닉네임
    private String intro; // 회원 인트로 메시지
    private String profileImg; // 회원 프로필이미지
    private List<ChatRoomListDTO> myChatRooms; // 채팅 룸 리스트
}
