package llustmarket.artmarket.web.dto.mypage.member;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class modPasswordDTO {

    private Long modPassId;
    @Pattern(regexp = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$", message = "영문,숫자,특수문자 조합 8~16자")
    private String modBeforePass;
    private Long newPassId;
    @Pattern(regexp = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$", message = "영문,숫자,특수문자 조합 8~16자")
    private String newPassword;
}
