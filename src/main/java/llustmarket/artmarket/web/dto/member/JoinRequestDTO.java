package llustmarket.artmarket.web.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class JoinRequestDTO {
    @NotBlank(message = "신원 확인 정보는 필수 입력 항목입니다.")
    @Size(max = 20, message = "신원 확인 정보는 최대 20글자까지 가능합니다.")
    @JsonProperty("JoinIdentity")
    private String JoinIdentity;

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    @Pattern(regexp = "^[ㄱ-힣]{2,5}$", message = "한글로 2~5글자로 입력해주세요.")
    @JsonProperty("JoinName")
    private String JoinName;

    @NotBlank(message = "닉네임은 필수 입력 항목입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣_]{2,10}$", message = "영문, 한글, 숫자, 특수기호(_)만 가능하며 3~10자여야 합니다.")
    @JsonProperty("JoinNickname")
    private String JoinNickname;

    @NotBlank(message = "로그인 아이디는 필수 입력 항목입니다.")
    @Pattern(regexp = "^[a-z]+[a-z0-9]{5,19}$", message = "영문자로 시작하는 영문자 또는 숫자 6~20자")
    @JsonProperty("JoinLoginId")
    private String JoinLoginId;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$", message = "영문,숫자,특수문자 조합 8~16자")
    @JsonProperty("JoinPassword")
    private String JoinPassword;

    @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다.")
    @JsonProperty("JoinPhone")
    private String JoinPhone;

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Size(max = 50, message = "이메일은 최대 50글자까지 가능합니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @JsonProperty("JoinEmail")
    private String JoinEmail;

}