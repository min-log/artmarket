package llustmarket.artmarket.web.dto.file;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class FileUploadDTO {
    // websocket 에서 전달 받는 바이트 배열의 파일 객체
    private String fileName; // 파일 이름
    private String fileContentType; // 확장자
    private byte[] fileData; // 바이트 배열


}
