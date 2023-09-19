package llustmarket.artmarket.web.dto.member;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class FindInfoDTO {
    @Pattern(regexp = "^[ㄱ-힣]{2,5}$", message = "한글로 2~5글자로 입력해주세요.")
    private String findIdName;

    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다.")
    private String findIdPhone;

    @Size(max = 50, message = "이메일은 최대 50글자까지 가능합니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String findIdEmail;

    @Pattern(regexp = "^[ㄱ-힣]{2,5}$", message = "한글로 2~5글자로 입력해주세요.")
    private String findPassName;

    @Pattern(regexp = "^[a-z]+[a-z0-9]{5,19}$", message = "영문자로 시작하는 영문자 또는 숫자 6~20자")
    private String findPassLoginId;

    @Size(max = 50, message = "이메일은 최대 50글자까지 가능합니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String findPassEmail;

    private Long updateId;
    private String updateToken;
}
