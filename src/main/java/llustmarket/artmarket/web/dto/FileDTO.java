package llustmarket.artmarket.web.dto;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class FileDTO {
    private long fileId; // 파일아이디
    private String filePath; // 파일 경로 
    private long fileTypeId; // 파일 경로에 따른 아이디
    private long fileOriginName; // 실제이름
    private long fileName; // 변경된 이름
}
