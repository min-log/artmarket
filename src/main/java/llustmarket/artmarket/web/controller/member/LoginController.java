package llustmarket.artmarket.web.controller.member;

import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.web.dto.member.LoginUserDTO;
import llustmarket.artmarket.web.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginUserDTO loginUser, BindingResult bindingResult,
                                        HttpSession session, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            List<Map<String, String>> loginErrors = new ArrayList<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("loginErrorParam", fieldName);
                errorMap.put("loginErrorMsg", errorMessage);
                loginErrors.add(errorMap);
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(loginErrors);
        }
        Member member = memberService.getMemberByLoginId(loginUser.getLoginId());
        if (member != null) {
            if (passwordEncoder.matches(loginUser.getLoginPassword(), member.getPassword())) {
                try {
                    session.setAttribute("login", member);

                    if (loginUser.isAutoLogin()) {
                        long second = 60 * 60 * 24 * 30;

                        Cookie cookie = new Cookie("loginCookie", session.getId());
                        cookie.setPath("/");
                        cookie.setMaxAge((int) second);
                        response.addCookie(cookie);

                        long millis = System.currentTimeMillis() + (second * 1000);
                        Date limitDate = new Date(millis);

                        memberService.autoLogin(session.getId(), limitDate, loginUser.getLoginId());
                    } else {
                        int sessionTimeoutInSeconds = 3600; // 1시간 (3600초)
                        session.setMaxInactiveInterval(sessionTimeoutInSeconds);
                    }
                    Map<String, String> loginSuccess = new HashMap<>();
                    loginSuccess.put("loginTrueIdentity", member.getIdentity());
                    loginSuccess.put("loginTrueId", String.valueOf(member.getMemberId()));
                    loginSuccess.put("loginTrueName", String.valueOf(member.getName()));
                    return ResponseEntity.status(HttpStatus.OK).body(loginSuccess);
                } catch (Exception e) {
                    List<Map<String, String>> loginErrors = new ArrayList<>();
                    String fieldName = String.valueOf(e.getCause());
                    String errorMessage = e.getMessage();

                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("loginErrorParam", fieldName);
                    errorMap.put("loginErrorMsg", errorMessage);
                    loginErrors.add(errorMap);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(loginErrors);
                }
            } else {
                Map<String, String> loginFail = new HashMap<>();
                loginFail.put("loginNoMatchMsg", "아이디 또는 패스워드를 확인하세요.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginFail);
            }

        } else {
            Map<String, String> loginFail = new HashMap<>();
            loginFail.put("loginNoMatchMsg", "아이디 또는 패스워드를 확인하세요.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginFail);
        }
    }
}
