package llustmarket.artmarket.domain.member;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class Member {

    private Long memberId;

    @NotBlank(message = "이름은 필수 항목입니다.")
    @Size(max = 5, message = "이름은 최대 5글자까지 입력 가능합니다.")
    private String name;

    @NotBlank(message = "닉네임은 필수 항목입니다.")
    @Size(max = 10, message = "닉네임은 최대 10글자까지 입력 가능합니다.")
    private String nickname;

    @NotBlank(message = "로그인 ID는 필수 항목입니다.")
    @Size(max = 20, message = "로그인 ID는 최대 20글자까지 입력 가능합니다.")
    private String loginId;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Size(max = 20, message = "비밀번호는 최대 20글자까지 입력 가능합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 항목입니다.")
    private String confirmPassword;

    @NotBlank(message = "전화번호는 필수 항목입니다.")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다. 01x-xxx(x)-xxxx")
    @Size(max = 13, message = "전화번호는 최대 13글자까지 입력 가능합니다.")
    private String phone;

    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Size(max = 50, message = "이메일은 최대 50글자까지 입력 가능합니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    private String accountBank;
    private String account;
    private String identity;
    private String memberIntro = "안녕하세요";
    private Integer withdrawl = 0;
}