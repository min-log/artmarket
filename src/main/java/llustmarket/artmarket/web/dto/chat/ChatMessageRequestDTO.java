package llustmarket.artmarket.web.dto.chat;


import llustmarket.artmarket.web.dto.file.FileUploadDTO;
import llustmarket.artmarket.web.dto.member.MemberDTO;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class ChatMessageRequestDTO {

    private long chatRoomId;//방 아이디
    private long chatSender; //보내는 아이디
    private String chatMsg; //메시지
    private FileUploadDTO chatFile;

}