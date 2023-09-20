package llustmarket.artmarket.web.dto.mypage.member;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class ModEmailDTO {
    private Long modMemberId;
    private Long modEmailId;
    @Email
    private String newEmail;
}
