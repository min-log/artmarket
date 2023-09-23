package llustmarket.artmarket.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class Token {

    private Long tokenId;

    private Long memberId;

    private String validationToken;

    private Date tokenLimitDate;

    private int tokenStatus;

    public Token(Long memberId, String validationToken, Date tokenLimitDate) {
        this.memberId = memberId;
        this.validationToken = validationToken;
        this.tokenLimitDate = tokenLimitDate;
    }
}