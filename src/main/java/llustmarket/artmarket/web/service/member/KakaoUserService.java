package llustmarket.artmarket.web.service.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.web.dto.member.JoinSocialDTO;
import llustmarket.artmarket.web.dto.member.KakaoUserInfoDto;
import llustmarket.artmarket.web.mapper.member.MemberMapper;
import llustmarket.artmarket.web.utils.member.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoUserService {
    private final PasswordEncoder passwordEncoder;
    private final MemberMapper memberMapper;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String client_id;

    public String kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code);
        log.info("accessToken = {}", accessToken);
        // 2. 토큰으로 카카오 API 호출
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);
        // 3. response Header에 JWT 토큰 추가
        String redirectURL = kakaoUsersAuthorizationInput(kakaoUserInfo, response);

        return redirectURL;
    }

    // 1. "인가 코드"로 "액세스 토큰" 요청
    private String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", client_id);
        body.add("redirect_uri", "http://61.97.189.178:8070/kakao-login");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        rt.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        rt.setErrorHandler(new DefaultResponseErrorHandler() {
            public boolean hasError(ClientHttpResponse response) throws IOException {
                HttpStatus statusCode = response.getStatusCode();
                return statusCode.series() == HttpStatus.Series.SERVER_ERROR;
            }
        });
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class);

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    // 2. 토큰으로 카카오 API 호출
    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class);

        // responseBody에 있는 정보를 꺼냄
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        Long id = jsonNode.get("id").asLong();
        String email = jsonNode.get("kakao_account").get("email").asText();

        return new KakaoUserInfoDto(id, email);
    }

    // 3. 카카오ID로 회원가입 처리
    public Member registerKakao(JoinSocialDTO request) {
        Optional<Member> member = memberMapper.findByUserEmail(request.getJoinEmail());
        if (member.isEmpty()) {
            Optional<Member> highestMemberIdMember = memberMapper.findHighestMemberId();
            long highestMembrId = 1L; // 기본값을 1로 설정

            if (highestMemberIdMember.isPresent()) {
                highestMembrId = highestMemberIdMember.get().getMemberId() + 1;
            }
            String loginId = "art" + highestMembrId + "@social";

            String password = request.getJwtToken();
            Member kakaoUser = new Member(request.getJoinName(), request.getJoinNickname(), loginId, passwordEncoder.encode(password),
                    request.getJoinPhone(), request.getJoinEmail(), request.getJoinIdentity());
            memberMapper.insertMember(kakaoUser);
            return kakaoUser;
        } else {
            return null;
        }
    }

    // 5. response Header에 JWT 토큰 추가
    private String kakaoUsersAuthorizationInput(KakaoUserInfoDto kakaoUserInfo, HttpServletResponse response) {
        String token = null;
        try {
            JwtTokenUtils jwtTokenUtils = new JwtTokenUtils();
            token = jwtTokenUtils.generateKakaoJwtToken(kakaoUserInfo);
            Optional<Member> member = memberMapper.findByUserEmail(kakaoUserInfo.getEmail());

            if (member.isPresent()) {
                memberMapper.updatePasswordByEmail(passwordEncoder.encode(token), kakaoUserInfo.getEmail());

                // 세션 대신 쿠키에 정보 저장
                Cookie loginTrueIdentity = new Cookie("loginTrueIdentity", member.get().getIdentity());
                Cookie loginTrueId = new Cookie("loginTrueId", String.valueOf(member.get().getMemberId()));
                Cookie loginTrueName = new Cookie("loginTrueName", member.get().getName());
                Cookie loginType = new Cookie("loginType", "SOCIAL");
                Cookie loginId = new Cookie("loginId", member.get().getLoginId());
                Cookie nickname = new Cookie("nickname", member.get().getNickname());

                // 쿠키의 유효 시간 설정 (초 단위)
                int maxAge = 1 * 60 * 60; // 예시로 1시간 유지

                loginTrueIdentity.setMaxAge(maxAge);
                loginTrueId.setMaxAge(maxAge);
                loginTrueName.setMaxAge(maxAge);

                response.addCookie(loginTrueIdentity);
                response.addCookie(loginTrueId);
                response.addCookie(loginTrueName);
                response.addCookie(loginType);
                response.addCookie(loginId);
                response.addCookie(nickname);

                return "redirect:/index.html";
            } else {
                // 세션 대신 쿠키에 정보 저장
                Cookie joinType = new Cookie("joinType", "SOCIAL");
                Cookie email = new Cookie("email", kakaoUserInfo.getEmail());
                Cookie jwtToken = new Cookie("jwtToken", token);

                // 쿠키의 유효 시간 설정 (초 단위)
                int maxAge = 1 * 60 * 60; // 예시로 1시간 유지in

                joinType.setMaxAge(maxAge);
                email.setMaxAge(maxAge);

                response.addCookie(joinType);
                response.addCookie(email);
                response.addCookie(jwtToken);

                return "redirect:/join.html";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "여기가 오류";
        }
    }
}
