package llustmarket.artmarket.web.dto.mypage.member;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileImageDTO {
    private MultipartFile profileImage;
    private String profileFileType;
    private Long profileFileTypeId;
}
