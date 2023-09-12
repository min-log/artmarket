package llustmarket.artmarket.web.controller.member;

import lombok.Data;

@Data
public class LoginForm {

    private String loginId;

    private String password;

    private boolean autoLogin;
}
