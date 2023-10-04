package llustmarket.artmarket.web.controller.mypage.member;

import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.domain.member.Token;
import llustmarket.artmarket.web.dto.mypage.member.ModEmailDTO;
import llustmarket.artmarket.web.dto.mypage.member.ModPhoneDTO;
import llustmarket.artmarket.web.dto.mypage.member.modPasswordDTO;
import llustmarket.artmarket.web.service.member.EmailService;
import llustmarket.artmarket.web.service.member.MemberService;
import llustmarket.artmarket.web.service.member.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
public class MemberController {
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final MemberService memberService;
    private static final int EXPIRATION_TIME_MINUTES = 5;
    private final EmailService emailService;

    public MemberController(PasswordEncoder passwordEncoder, TokenService tokenService, MemberService memberService, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.memberService = memberService;
        this.emailService = emailService;
    }

    @GetMapping("/mypage-info/{member_id}")
    public ResponseEntity<Object> getMemberInfo(@PathVariable("member_id") Long memberId) {
        try {
            Member member = memberService.getMemberByMemberId(memberId);
            if (member != null) {
                Map<String, String> responseData = new HashMap<>();
                responseData.put("nickname", member.getNickname());
                responseData.put("phone", member.getPhone());
                responseData.put("email", member.getEmail());
                responseData.put("bank", member.getAccountBank());
                responseData.put("account", member.getAccount());

                return ResponseEntity.ok(responseData);
            } else {
                List<Map<String, String>> memberInfoErrors = new ArrayList<>();
                String fieldName = "member_id 오류";
                String errorMessage = "해당하는 회원 정보가 없습니다.";

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("memberInfoErrorParam", fieldName);
                errorMap.put("memberInfoErrorMsg", errorMessage);
                memberInfoErrors.add(errorMap);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(memberInfoErrors);
            }
        } catch (NumberFormatException e) {
            List<Map<String, String>> memberInfoErrors = new ArrayList<>();
            String fieldName = "member_id 오류";
            String errorMessage = "유효한 숫자 형식이 아닙니다";

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("memberInfoErrorParam", fieldName);
            errorMap.put("memberInfoErrorMsg", errorMessage);
            memberInfoErrors.add(errorMap);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(memberInfoErrors);
        } catch (Exception e) {
            List<Map<String, String>> memberInfoErrors = new ArrayList<>();
            String fieldName = String.valueOf(e.getCause());
            String errorMessage = e.getMessage();

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("memberInfoErrorParam", fieldName);
            errorMap.put("memberInfoErrorMsg", errorMessage);
            memberInfoErrors.add(errorMap);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(memberInfoErrors);
        }
    }

    @PostMapping("/mypage-info/mod-password")
    public ResponseEntity<Object> preModifyPassword(@Valid @RequestBody modPasswordDTO request, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                List<Map<String, String>> modPasswordErrors = new ArrayList<>();

                for (FieldError error : bindingResult.getFieldErrors()) {
                    String fieldName = error.getField();
                    String errorMessage = error.getDefaultMessage();

                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("modPasswordErrorParam", fieldName);
                    errorMap.put("modPasswordErrorMsg", errorMessage);
                    modPasswordErrors.add(errorMap);
                }

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(modPasswordErrors);
            }
            Long memberId = request.getModPassId();
            String beforePass = request.getModBeforePass();

            Member member = memberService.getMemberByMemberId(memberId);
            if (member != null) {
                if (passwordEncoder.matches(beforePass, member.getPassword())) {
                    return ResponseEntity.noContent().build();
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            } else {
                List<Map<String, String>> modPasswordErrors = new ArrayList<>();

                String fieldName = "패스워드 변경 정보 오류";
                String errorMessage = "멤버아이디와 비밀번호를 확인하세요.";

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("modPasswordErrorParam", fieldName);
                errorMap.put("modPasswordErrorMsg", errorMessage);
                modPasswordErrors.add(errorMap);

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(modPasswordErrors);
            }
        } catch (NumberFormatException e) {
            List<Map<String, String>> modPasswordErrors = new ArrayList<>();
            String fieldName = "member_id 오류";
            String errorMessage = "유효한 숫자 형식이 아닙니다";

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("modPasswordErrorParam", fieldName);
            errorMap.put("modPasswordErrorMsg", errorMessage);
            modPasswordErrors.add(errorMap);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(modPasswordErrors);
        } catch (Exception e) {
            List<Map<String, String>> modPasswordErrors = new ArrayList<>();
            String fieldName = String.valueOf(e.getCause());
            String errorMessage = e.getMessage();

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("modPasswordErrorParam", fieldName);
            errorMap.put("modPasswordErrorMsg", errorMessage);
            modPasswordErrors.add(errorMap);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(modPasswordErrors);
        }
    }

    @PatchMapping("/mypage-info/mod-password")
    public ResponseEntity<Object> modifyPassword(@Valid @RequestBody modPasswordDTO request, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                List<Map<String, String>> modPasswordErrors = new ArrayList<>();

                for (FieldError error : bindingResult.getFieldErrors()) {
                    String fieldName = error.getField();
                    String errorMessage = error.getDefaultMessage();

                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("modPasswordErrorParam", fieldName);
                    errorMap.put("modPasswordErrorMsg", errorMessage);
                    modPasswordErrors.add(errorMap);
                }

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(modPasswordErrors);
            }

            Member member = memberService.getMemberByMemberId(request.getNewPassId());
            if (member != null) {
                if (passwordEncoder.matches(request.getNewPassword(), member.getPassword())) {
                    List<Map<String, String>> modPasswordErrors = new ArrayList<>();
                    String fieldName = "newPassword";
                    String errorMessage = "기존 패스워드와 동일합니다.";

                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("modPasswordErrorParam", fieldName);
                    errorMap.put("modPasswordErrorMsg", errorMessage);
                    modPasswordErrors.add(errorMap);

                    return ResponseEntity.status(HttpStatus.CONFLICT).body(modPasswordErrors);
                } else {
                    String hashedPassword = passwordEncoder.encode(request.getNewPassword());
                    memberService.updatePasswordByMemberId(request.getNewPassId(), hashedPassword);
                    return ResponseEntity.noContent().build();
                }
            } else {
                List<Map<String, String>> modPasswordErrors = new ArrayList<>();

                String fieldName = "패스워드 변경 정보 오류";
                String errorMessage = "멤버아이디와 비밀번호를 확인하세요.";

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("modPasswordErrorParam", fieldName);
                errorMap.put("modPasswordErrorMsg", errorMessage);
                modPasswordErrors.add(errorMap);

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(modPasswordErrors);
            }
        } catch (NumberFormatException e) {
            List<Map<String, String>> modPasswordErrors = new ArrayList<>();
            String fieldName = "member_id 오류";
            String errorMessage = "유효한 숫자 형식이 아닙니다";

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("modPasswordErrorParam", fieldName);
            errorMap.put("modPasswordErrorMsg", errorMessage);
            modPasswordErrors.add(errorMap);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(modPasswordErrors);
        } catch (Exception e) {
            List<Map<String, String>> modPasswordErrors = new ArrayList<>();
            String fieldName = String.valueOf(e.getCause());
            String errorMessage = e.getMessage();

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("modPasswordErrorParam", fieldName);
            errorMap.put("modPasswordErrorMsg", errorMessage);
            modPasswordErrors.add(errorMap);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(modPasswordErrors);
        }
    }

    @PostMapping("/mypage-info/mod-phone")
    public ResponseEntity<Object> preModifyPhone(@RequestBody ModPhoneDTO request) {
        try {
            Member member = memberService.getMemberByMemberId(request.getModMemberId());

            if (member != null) {
                // 이전에 보낸 모든 토큰을 비활성화시킵니다.
                tokenService.invalidateTokens(member.getMemberId());

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

                    // 토큰을 테이블에 저장합니다.
                    emailService.sendTokenByEmail(member.getEmail(), token, expirationTime.getTime());
                    Token saveToken = new Token(member.getMemberId(), uuid, expirationTime.getTime());
                    tokenService.saveToken(saveToken);

                    return ResponseEntity.noContent().build();
                } else {
                    List<Map<String, String>> modPhoneErrors = new ArrayList<>();

                    String fieldName = "인증번호 생성 오류";
                    String errorMessage = "문자열에 6개 이상의 숫자가 필요합니다.";

                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("modPhoneErrorParam", fieldName);
                    errorMap.put("modPhoneErrorMsg", errorMessage);
                    modPhoneErrors.add(errorMap);

                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(modPhoneErrors);
                }
            } else {
                String errorMessage = "멤버 정보를 찾을 수 없습니다.";

                Map<String, String> modPhoneFail = new HashMap<>();
                modPhoneFail.put("dontModPhoneMsg", errorMessage);

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(modPhoneFail);
            }
        } catch (Exception e) {
            List<Map<String, String>> modPhoneErrors = new ArrayList<>();
            String fieldName = "알 수 없는 휴대폰 번호 변경 오류";
            String errorMessage = "휴대폰 번호 변경 관련 정보를 확인해 주세요.";

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("modPhoneErrorParam", fieldName);
            errorMap.put("modPhoneErrorMsg", errorMessage);
            modPhoneErrors.add(errorMap);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(modPhoneErrors);
        }
    }

    @GetMapping("/mypage-info/mod-phone/{member_id}/{token}")
    public ResponseEntity<Object> verifyPhoneModToken(@PathVariable("member_id") Long memberId, @PathVariable("token") String token) {
        try {
            Token latestToken = tokenService.getLatestTokenByMemberId(memberId);

            if (latestToken != null && latestToken.getTokenStatus() == 1) {
                StringBuilder numericString = new StringBuilder();
                for (char c : latestToken.getValidationToken().toCharArray()) {
                    if (Character.isDigit(c)) {
                        numericString.append(c);
                    }
                }

                if (numericString.substring(0, 6).equals(token)) {
                    return ResponseEntity.ok().build();
                }
            }

            Map<String, String> responseMessage = new HashMap<>();
            responseMessage.put("errorMessage", "유효하지 않은 토큰입니다.");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMessage);
        } catch (Exception e) {
            List<Map<String, String>> modPhoneErrors = new ArrayList<>();
            String fieldName = "토큰";
            String errorMessage = "휴대폰 번호 인증 과정을 확인해주세요.";

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("modPhoneErrorParam", fieldName);
            errorMap.put("modPhoneErrorMsg", errorMessage);
            modPhoneErrors.add(errorMap);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(modPhoneErrors);
        }
    }

    @PatchMapping("/mypage-info/mod-phone")
    public ResponseEntity<Object> modifyPhoneNumber(@Valid @RequestBody ModPhoneDTO modPhone, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Map<String, String>> modPhoneErrors = new ArrayList<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("modPhoneErrorParam", fieldName);
                errorMap.put("modPhoneErrorMsg", errorMessage);
                modPhoneErrors.add(errorMap);
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(modPhoneErrors);
        }

        try {
            Member member = memberService.getMemberByMemberId(modPhone.getModPhoneId());

            if (member != null) {
                if (modPhone.getNewPhone().equals(member.getPhone())) {
                    List<Map<String, String>> modPhoneErrors = new ArrayList<>();
                    String fieldName = "새 전화번호";
                    String errorMessage = "기존 전화번호와 동일합니다.";

                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("modPhoneErrorParam", fieldName);
                    errorMap.put("modPhoneErrorMsg", errorMessage);
                    modPhoneErrors.add(errorMap);

                    return ResponseEntity.status(HttpStatus.CONFLICT).body(modPhoneErrors);
                }
                memberService.updatePhoneByMemberId(modPhone.getModPhoneId(), modPhone.getNewPhone());
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                List<Map<String, String>> modPhoneErrors = new ArrayList<>();
                String fieldName = "멤버 조회 오류";
                String errorMessage = "member_id에 해당하는 멤버가 없습니다.";

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("modPhoneErrorParam", fieldName);
                errorMap.put("modPhoneErrorMsg", errorMessage);
                modPhoneErrors.add(errorMap);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(modPhoneErrors);
            }
        } catch (Exception e) {
            List<Map<String, String>> modPhoneErrors = new ArrayList<>();
            String fieldName = String.valueOf(e.getCause());
            String errorMessage = e.getMessage();

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("modPhoneErrorParam", fieldName);
            errorMap.put("modPhoneErrorMsg", errorMessage);
            modPhoneErrors.add(errorMap);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(modPhoneErrors);
        }
    }

    @PostMapping("/mypage-info/mod-email")
    public ResponseEntity<Object> preModifyEmail(@RequestBody ModEmailDTO request) {
        try {
            Member member = memberService.getMemberByMemberId(request.getModMemberId());

            if (member != null) {
                // 이전에 보낸 모든 토큰을 비활성화시킵니다.
                tokenService.invalidateTokens(member.getMemberId());

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

                    // 토큰을 테이블에 저장합니다.
                    emailService.sendTokenByEmail(member.getEmail(), token, expirationTime.getTime());
                    Token saveToken = new Token(member.getMemberId(), uuid, expirationTime.getTime());
                    tokenService.saveToken(saveToken);

                    return ResponseEntity.noContent().build();
                } else {
                    List<Map<String, String>> modPhoneErrors = new ArrayList<>();

                    String fieldName = "인증번호 생성 오류";
                    String errorMessage = "문자열에 6개 이상의 숫자가 필요합니다.";

                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("modEmailErrorParam", fieldName);
                    errorMap.put("modEmailErrorMsg", errorMessage);
                    modPhoneErrors.add(errorMap);

                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(modPhoneErrors);
                }
            } else {
                String errorMessage = "멤버 정보를 찾을 수 없습니다.";

                Map<String, String> modPhoneFail = new HashMap<>();
                modPhoneFail.put("dontModEmailMsg", errorMessage);

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(modPhoneFail);
            }
        } catch (Exception e) {
            List<Map<String, String>> modPhoneErrors = new ArrayList<>();
            String fieldName = "알 수 없는 이메일 변경 오류";
            String errorMessage = "이메일 변경 관련 정보를 확인해 주세요.";

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("modEmailErrorParam", fieldName);
            errorMap.put("modEmailErrorMsg", errorMessage);
            modPhoneErrors.add(errorMap);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(modPhoneErrors);
        }
    }

    @GetMapping("/mypage-info/mod-email/{member_id}/{token}")
    public ResponseEntity<Object> verifyEmailModToken(@PathVariable("member_id") Long memberId, @PathVariable("token") String token) {
        try {
            Token latestToken = tokenService.getLatestTokenByMemberId(memberId);

            if (latestToken != null && latestToken.getTokenStatus() == 1) {
                StringBuilder numericString = new StringBuilder();
                for (char c : latestToken.getValidationToken().toCharArray()) {
                    if (Character.isDigit(c)) {
                        numericString.append(c);
                    }
                }

                if (numericString.substring(0, 6).equals(token)) {
                    return ResponseEntity.ok().build();
                }
            }

            Map<String, String> responseMessage = new HashMap<>();
            responseMessage.put("errorMessage", "유효하지 않은 토큰입니다.");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMessage);
        } catch (Exception e) {
            List<Map<String, String>> modEmailErrors = new ArrayList<>();
            String fieldName = "토큰";
            String errorMessage = "이메일 인증 과정을 확인해주세요.";

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("modEmailErrorParam", fieldName);
            errorMap.put("modEmailErrorMsg", errorMessage);
            modEmailErrors.add(errorMap);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(modEmailErrors);
        }
    }

    @PatchMapping("/mypage-info/mod-email")
    public ResponseEntity<Object> modifyEmail(@Valid @RequestBody ModEmailDTO modEmail, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Map<String, String>> modEmailErrors = new ArrayList<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("modEmailErrorParam", fieldName);
                errorMap.put("modEmailErrorMsg", errorMessage);
                modEmailErrors.add(errorMap);
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(modEmailErrors);
        }

        try {
            Member member = memberService.getMemberByMemberId(modEmail.getModEmailId());

            if (member != null) {
                if (modEmail.getNewEmail().equals(member.getEmail())) {
                    List<Map<String, String>> modEmailErrors = new ArrayList<>();
                    String fieldName = "새 이메일";
                    String errorMessage = "기존 이메일과 동일합니다.";

                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("modEmailErrorParam", fieldName);
                    errorMap.put("modEmailErrorMsg", errorMessage);
                    modEmailErrors.add(errorMap);
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(modEmailErrors);
                }
                memberService.updateEmailByMemberId(modEmail.getModEmailId(), modEmail.getNewEmail());
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                List<Map<String, String>> modEmailErrors = new ArrayList<>();
                String fieldName = "멤버 조회 오류";
                String errorMessage = "member_id에 해당하는 멤버가 없습니다.";

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("modEmailErrorParam", fieldName);
                errorMap.put("modEmailErrorMsg", errorMessage);
                modEmailErrors.add(errorMap);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(modEmailErrors);
            }
        } catch (Exception e) {
            List<Map<String, String>> modEmailErrors = new ArrayList<>();
            String fieldName = String.valueOf(e.getCause());
            String errorMessage = e.getMessage();

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("modEmailErrorParam", fieldName);
            errorMap.put("modEmailErrorMsg", errorMessage);
            modEmailErrors.add(errorMap);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(modEmailErrors);
        }
    }
}
