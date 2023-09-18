package llustmarket.artmarket.web.controller.member;

import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.domain.member.Token;
import llustmarket.artmarket.web.dto.member.FindInfoDTO;
import llustmarket.artmarket.web.service.member.EmailService;
import llustmarket.artmarket.web.service.member.MemberService;
import llustmarket.artmarket.web.service.member.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RequestMapping("/api")
@RestController
public class FindInfoController {

    final MemberService memberService;
    final TokenService tokenService;

    private final EmailService emailService;

    private static final int EXPIRATION_TIME_MINUTES = 60;

    public FindInfoController(MemberService memberService, TokenService tokenService, EmailService emailService) {
        this.memberService = memberService;
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    @PostMapping("/find-info")
    public ResponseEntity<Object> findInfo(@Valid @RequestBody FindInfoDTO findInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Map<String, String>> findIdErrors = new ArrayList<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("findIdErrorParam", fieldName);
                errorMap.put("findIdErrorMsg", errorMessage);
                findIdErrors.add(errorMap);
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(findIdErrors);
        }

        if (findInfo.getFindIdPhone() != null && findInfo.getFindIdName() != null && findInfo.getFindIdEmail() != null) {
            try {
                Member member = memberService.getMemberByPhone(findInfo.getFindIdPhone());
                if (member != null && member.getName().equals(findInfo.getFindIdName()) && member.getEmail().equals(findInfo.getFindIdEmail())) {
                    Map<String, String> findIdSuccess = new HashMap<>();
                    findIdSuccess.put("findLoginId", member.getLoginId());
                    return ResponseEntity.status(HttpStatus.OK).body(findIdSuccess);
                } else {
                    Map<String, String> findIdFail = new HashMap<>();
                    findIdFail.put("dontFindIdMsg", "입력한 정보를 다시 확인하세요.");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(findIdFail);
                }
            } catch (Exception e) {
                List<Map<String, String>> findIdErrors = new ArrayList<>();
                String fieldName = "알 수 없는 아이디 찾기 오류";
                String errorMessage = "아이이 찾기 관련 정보를 확인해 주세요.";
                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("findIdErrorParam", fieldName);
                errorMap.put("findIdErrorMsg", errorMessage);
                findIdErrors.add(errorMap);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(findIdErrors);
            }
        } else if (findInfo.getFindPassLoginId() != null && findInfo.getFindPassName() != null && findInfo.getFindPassEmail() != null) {
            try {
                Member member = memberService.getMemberByLoginId(findInfo.getFindPassLoginId());
                // 이전에 보낸 모든 토큰을 비활성화시킵니다.
                tokenService.invalidateTokens(member.getMemberId());
                if (member != null && member.getName().equals(findInfo.getFindPassName()) && member.getEmail().equals(findInfo.getFindPassEmail())) {
                    String uuid = UUID.randomUUID().toString();
                    StringBuilder numericString = new StringBuilder();
                    for (char c : uuid.toCharArray()) {
                        if (Character.isDigit(c)) {
                            numericString.append(c);
                        }
                    }
                    if (numericString.length() >= 6) {
                        String token = numericString.substring(0, 6);
                        // 현재 시간 기준으로 유효기간 설정
                        Calendar expirationTime = Calendar.getInstance();
                        expirationTime.add(Calendar.MINUTE, EXPIRATION_TIME_MINUTES);
                        // 이메일로 토큰을 보냅니다.
                        emailService.sendTokenByEmail(findInfo.getFindPassEmail(), token, expirationTime.getTime());
                        Token saveToken = new Token(member.getMemberId(), uuid, expirationTime.getTime());

                        tokenService.saveToken(saveToken);
                        return ResponseEntity.status(HttpStatus.OK).build();
                    } else {
                        List<Map<String, String>> findPasswordErrors = new ArrayList<>();

                        String fieldName = "인증번호 생성 오류";
                        String errorMessage = "문자열에 6개 이상의 숫자가 필요합니다.";

                        Map<String, String> errorMap = new HashMap<>();
                        errorMap.put("findPassErrorParam", fieldName);
                        errorMap.put("findPassErrorMsg", errorMessage);
                        findPasswordErrors.add(errorMap);

                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(findPasswordErrors);
                    }
                } else {
                    String errorMessage = "입력한 정보를 다시 확인하세요";

                    Map<String, String> findPassFail = new HashMap<>();
                    findPassFail.put("dontFindPassMsg", errorMessage);

                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(findPassFail);
                }
            } catch (Exception e) {
                log.error("error = {}", e);
                List<Map<String, String>> findPasswordErrors = new ArrayList<>();
                String fieldName = "알 수 없는 비밀번호 찾기 오류";
                String errorMessage = "비밀번호 찾기 관련 정보를 확인해 주세요.";
                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("findPassErrorParam", fieldName);
                errorMap.put("findPassErrorMsg", errorMessage);
                findPasswordErrors.add(errorMap);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(findPasswordErrors);
            }
        } else if (findInfo.getUpdateToken() != null && findInfo.getUpdateId() != null) {
            try {
                Token latestToken = tokenService.getLatestTokenByMemberId(findInfo.getUpdateId());
                if (latestToken != null && latestToken.getTokenStatus() == 1) {
                    StringBuilder numericString2 = new StringBuilder();
                    for (char c : latestToken.getValidationToken().toCharArray()) {
                        if (Character.isDigit(c)) {
                            numericString2.append(c);
                        }
                    }
                    if (numericString2.substring(0, 6).equals(findInfo.getUpdateToken())) {
                        return ResponseEntity.status(HttpStatus.OK).build();
                    }
                }
                Map<String, String> findPassFail = new HashMap<>();
                findPassFail.put("dontFindPassMsg", "유효기간이 지났거나 입력한 정보를 다시 확인하세요.");

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(findPassFail);
            } catch (Exception e) {
                log.error("error = {}", e);
                List<Map<String, String>> findPasswordErrors = new ArrayList<>();
                String fieldName = "인증 과정 오류";
                String errorMessage = "인증 과정을 확인해주세요.";
                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("findPassErrorParam", fieldName);
                errorMap.put("findPassErrorMsg", errorMessage);
                findPasswordErrors.add(errorMap);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(findPasswordErrors);
            }

        }
        List<Map<String, String>> findErrors = new ArrayList<>();
        String fieldName = "정보 오류";
        String errorMessage = "정보를 모두 입력해주세요.";
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("findErrorParam", fieldName);
        errorMap.put("findErrorMsg", errorMessage);
        findErrors.add(errorMap);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(findErrors);
    }
}