package llustmarket.artmarket.web.dto.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class LoginUserDTO {
    @NotBlank(message = "로그인 아이디는 필수 입력 항목입니다.")
    @Pattern(regexp = "^[a-z]+[a-z0-9]{5,19}$", message = "영문자로 시작하는 영문자 또는 숫자 6~20자")
    private String loginId;
    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$", message = "영문,숫자,특수문자 조합 8~16자")
    private String loginPassword;

    private boolean autoLogin;
}
