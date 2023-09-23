package llustmarket.artmarket.web.service.member;


import llustmarket.artmarket.domain.member.Token;
import llustmarket.artmarket.web.mapper.member.TokenMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
public class TokenService {

    @Autowired
    private TokenMapper tokenMapper;

    @Transactional
    public void saveToken(Token token) {
        tokenMapper.insertToken(token);
    }

    @Transactional
    public void invalidateTokens(Long memberId) {
        tokenMapper.invalidateTokens(memberId);
    }

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void invalidateExpiredTokens() {
        Date currentDate = new Date();
        tokenMapper.invalidateExpiredTokens(currentDate);
    }

    public Token getLatestTokenByMemberId(Long memberId) {
        return tokenMapper.selectLatestTokenByMemberId(memberId);
    }
}