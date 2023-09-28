package llustmarket.artmarket.web.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinKakaoDTO {
    @NotBlank(message = "일반 회원, 판매 회원 정보는 필수 입력 항목입니다.")
    @Size(max = 20, message = "역할 확인 정보는 최대 20글자까지 가능합니다.")
    private String kakaoJoinIdentity;
    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    @Pattern(regexp = "^[ㄱ-힣]{2,5}$", message = "한글로 2~5글자로 입력해주세요.")
    private String kakaoJoinName;
    @NotBlank(message = "닉네임은 필수 입력 항목입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣_]{2,10}$", message = "영문, 한글, 숫자, 특수기호(_)만 가능하며 3~10자여야 합니다.")
    private String kakaoJoinNickname;
    @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다.")
    private String kakaoJoinPhone;
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Size(max = 50, message = "이메일은 최대 50글자까지 가능합니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String kakaoJoinEmail;
}
