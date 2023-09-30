package llustmarket.artmarket.web.service.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.web.dto.member.GoogleLoginResponse;
import llustmarket.artmarket.web.dto.member.JoinSocialDTO;
import llustmarket.artmarket.web.mapper.member.MemberMapper;
import llustmarket.artmarket.web.utils.member.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class GoogleUserService {
    private final PasswordEncoder passwordEncoder;
    private final MemberMapper memberMapper;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String client_id;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String client_secret;

    public void googleLogin(String code, HttpServletResponse response) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청https://accounts.google.com/o/oauth2/v2/auth?client_id=242939801101-svnsms546a27mk3i9mhfisb7fd0ge7l9.apps.googleusercontent.com&redirect_uri=http://localhost:8070/google-login&response_type=code&scope=email profile
        String accessToken = getAccessToken(code);
        log.info("accessToken = {}", accessToken);

        // 2. 토큰으로 카카오 API 호출
        GoogleLoginResponse googleUserInfo = getGoogleUserInfo(accessToken);

        // 5. response Header에 JWT 토큰 추가
        googleUsersAuthorizationInput(googleUserInfo, (HttpServletResponseWrapper) response);
    }

    private String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", client_id);
        body.add("client_secret", client_secret);
        body.add("code", code);
        body.add("grant_type", "authorization_code");
        body.add("redirect_uri", "http://localhost:8070/google-login");
        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> googleTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        rt.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        rt.setErrorHandler(new DefaultResponseErrorHandler() {
            public boolean hasError(ClientHttpResponse response) throws IOException {
                HttpStatus statusCode = response.getStatusCode();
                return statusCode.series() == HttpStatus.Series.SERVER_ERROR;
            }
        });
        ResponseEntity<String> response = rt.exchange(
                "https://oauth2.googleapis.com/token",
                HttpMethod.POST,
                googleTokenRequest,
                String.class
        );
        log.info("코드 = {}", code);

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }


    // 2. 토큰으로 구글 API 호출
    private GoogleLoginResponse getGoogleUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> googleUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://www.googleapis.com/userinfo/v2/me",
                HttpMethod.GET,
                googleUserInfoRequest,
                String.class
        );

        // responseBody에 있는 정보를 꺼냄
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        String id = jsonNode.get("id").asText();
        String email = jsonNode.get("email").asText();

        return new GoogleLoginResponse(id, email);
    }

    public Member registerGoogle(JoinSocialDTO request) {
        Optional<Member> member = memberMapper.findByUserEmail(request.getJoinEmail());
        if (member.isEmpty()) {
            Optional<Member> highestMemberIdMember = memberMapper.findHighestMemberId();
            long highestMembrId = 1L; // 기본값을 1로 설정

            if (highestMemberIdMember.isPresent()) {
                highestMembrId = highestMemberIdMember.get().getMemberId() + 1;
            }
            String loginId = "art" + highestMembrId + "@google";
            String password = request.getJwtToken();
            Member googleUser = new Member(request.getJoinName(), request.getJoinNickname(), loginId, password, request.getJoinPhone(), request.getJoinEmail(), request.getJoinIdentity());
            memberMapper.insertMember(googleUser);
            return googleUser;
        } else {
            return null;
        }
    }

    private void googleUsersAuthorizationInput(GoogleLoginResponse googleUserInfo, HttpServletResponse response) {
        String token = null;
        try {
            JwtTokenUtils jwtTokenUtils = new JwtTokenUtils();
            token = jwtTokenUtils.generateGoogleJwtToken(googleUserInfo);
            Optional<Member> member = memberMapper.findByUserEmail(googleUserInfo.getEmail());

            if (member.isPresent()) {
                memberMapper.updatePasswordByEmail(token, googleUserInfo.getEmail());
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("loginTrueIdentity", member.get().getIdentity());
                responseBody.put("loginTrueId", member.get().getMemberId());
                responseBody.put("loginTrueName", member.get().getName());
                response.setHeader("Authorization", "BEARER " + token);
                response.setHeader("Content-type", "application/json;charset=UTF-8");
                response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
            } else {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("email", googleUserInfo.getEmail());
                response.setHeader("Authorization", "BEARER " + token);
                response.setHeader("Content-type", "application/json;charset=UTF-8");
                response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("token = {}", token);
    }
}
