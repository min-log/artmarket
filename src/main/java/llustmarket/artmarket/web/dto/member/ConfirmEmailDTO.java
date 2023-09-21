package llustmarket.artmarket.web.dto.member;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class ConfirmEmailDTO {
    @Email
    private String confirmEmail;
}
