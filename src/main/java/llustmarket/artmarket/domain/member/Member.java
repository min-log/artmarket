package llustmarket.artmarket.domain.member;

import lombok.Data;

@Data
public class Member {

    private Long memberId;

    private String name;

    private String nickname;

    private String loginId;

    private String password;

    private String confirmPassword;

    private String phone;

    private String email;

    private String accountBank;
    private String account;
    private String identity;
    private String memberIntro = "안녕하세요";
    private Integer withdrawl = 0;

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