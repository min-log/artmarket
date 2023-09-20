package llustmarket.artmarket.web.dto.mypage.member;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class ModPhoneDTO {
    private Long modMemberId;
    private Long modPhoneId;
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다.")
    private String newPhone;
}
