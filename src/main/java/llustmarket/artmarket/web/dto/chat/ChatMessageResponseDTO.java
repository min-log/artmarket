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
    // 전달하는 메시지 데이터
    private long chatMsgId; // 메시지 ID 내역 삭제 시 필요.
    private long chatSender; //보내는 사람
    private LocalDateTime chatDate; //생성날짜
    private String  chatMsg; //메시지
    private String chatType; // 메시지 타입
    private String chatFile; //파일 url
    private String chatFileName; // 파일 이름
    private String chatFileDownload; // 실제 파일 이름
}