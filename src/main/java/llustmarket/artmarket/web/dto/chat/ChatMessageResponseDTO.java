package llustmarket.artmarket.web.dto.chat;


import lombok.*;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class ChatMessageResponseDTO {

    private long chatMessageId;//고유 번호
    private long chatRoomId;//방 아이디
    private long memberId; //보내는 사람
    private LocalDateTime chatMessageDate; //생성날짜
    private String message; //메시지
    private String chatMessageType; // 메시지 타입

    private String writer; //보내는 사람 이름
    private String writerIdentity; //보내는 사람 권한정보
    private String fileNameOrigin; //파일 원본 이름
    private String fileName; // 파일 수정된 이름

}