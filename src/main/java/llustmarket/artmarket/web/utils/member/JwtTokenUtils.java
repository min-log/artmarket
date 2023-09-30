package llustmarket.artmarket.web.utils.member;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import llustmarket.artmarket.web.dto.member.GoogleLoginResponse;
import llustmarket.artmarket.web.dto.member.KakaoUserInfoDto;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtils {

    private static final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    //accessToken 만료시간 설정
    public final static long ACCESS_TOKEN_VALIDATION_SECOND = 1000L * 60 * 60 * 12; //12시간

    // 토큰 생성
    public String generateKakaoJwtToken(KakaoUserInfoDto kakaoUserInfo) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, kakaoUserInfo.getEmail());
    }

    public String generateGoogleJwtToken(GoogleLoginResponse googleUserInfo) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, googleUserInfo.getEmail());
    }

    // 토큰 생성 및 유효성 검증
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDATION_SECOND)) // 토큰 만료 시간 (12시간)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
