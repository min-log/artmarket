package llustmarket.artmarket.domain.file;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class File {
    private long fileId; // 파일아이디
    private String filePath; // 파일 경로 
    private long fileTypeId; // 파일 경로에 따른 아이디
    private long fileOriginName; // 실제이름
    private long fileName; // 변경된 이름
    private LocalDateTime fileDate; // 생성일
}
