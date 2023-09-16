package llustmarket.artmarket.web.dto.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UpdatePassDTO {
    private Long updatePassId;
    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$", message = "영문,숫자,특수문자 조합 8~16자")
    private String updatePassword;
}
