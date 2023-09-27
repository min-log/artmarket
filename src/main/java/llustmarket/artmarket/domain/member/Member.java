package llustmarket.artmarket.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

    private Long memberId;

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    @Pattern(regexp = "^[ㄱ-힣]{2,5}$", message = "한글로 2~5글자로 입력해주세요.")
    private String name;

    @NotBlank(message = "닉네임은 필수 입력 항목입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣_]{2,10}$", message = "영문, 한글, 숫자, 특수기호(_)만 가능하며 3~10자여야 합니다.")
    private String nickname;

    @NotBlank(message = "로그인 아이디는 필수 입력 항목입니다.")
    @Pattern(regexp = "^[a-z]+[a-z0-9]{5,19}$", message = "영문자로 시작하는 영문자 또는 숫자 6~20자")
    private String loginId;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$", message = "영문,숫자,특수문자 조합 8~16자")
    private String password;

    @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다.")
    private String phone;

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Size(max = 50, message = "이메일은 최대 50글자까지 가능합니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    private String accountBank;
    private String account;

    @NotBlank(message = "일반 회원, 판매 회원 정보는 필수 입력 항목입니다.")
    @Size(max = 20, message = "역할 확인 정보는 최대 20글자까지 가능합니다.")
    private String identity;
    private String memberIntro = "안녕하세요";
    private Date memberDate = new Date();
    private Integer withdrawl = 0;
    private boolean autoLogin;
    private String sessionId = "none";
    private Date limitDate;


    public Member(String name, String nickname, String loginId, String password, String phone, String email, String identity) {
        this.name = name;
        this.nickname = nickname;
        this.loginId = loginId;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.identity = identity;
    }
}
