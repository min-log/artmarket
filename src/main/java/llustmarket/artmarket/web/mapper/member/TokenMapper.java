package llustmarket.artmarket.web.mapper.member;

import llustmarket.artmarket.domain.member.Token;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface TokenMapper {
    void insertToken(Token token);

    void invalidateTokens(Long memberId);

    void invalidateExpiredTokens(Date currentDate);

    Token selectLatestTokenByMemberId(Long memberId);
}
