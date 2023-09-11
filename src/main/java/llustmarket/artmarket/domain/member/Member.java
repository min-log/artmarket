package llustmarket.artmarket.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@RequiredArgsConstructor
public class Member {
    private Long memberId;
    private String name;
    private String nickname;
    private String loginId;
    private String password;
    private String phone;
    private String email;
    private String accountBank;
    private String account;
    private String identity;
    private String memberIntro;
    private Date memberDate;
    private Integer withdrawl;
}
