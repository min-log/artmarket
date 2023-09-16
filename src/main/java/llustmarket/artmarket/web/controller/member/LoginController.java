package llustmarket.artmarket.web.controller.member;

import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.web.dto.member.LoginUserDTO;
import llustmarket.artmarket.web.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Slf4j
@Controller
public class LoginController {

    @Autowired
    MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginUserDTO loginUser, BindingResult bindingResult, HttpSession session, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            List<Map<String, String>> loginErrors = new ArrayList<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("joinErrorParam", fieldName);
                errorMap.put("joinErrorMsg", errorMessage);
                loginErrors.add(errorMap);
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(loginErrors);
        }
        Member member = memberService.getMemberByLoginId(loginUser.getLoginId());
        if (member != null) {
            if (member.getPassword().equals(loginUser.getLoginPassword())) {
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
                }

                try {
                    Map<String, String> loginSuccess = new HashMap<>();
                    loginSuccess.put("loginTrueIdentity", member.getIdentity());
                    loginSuccess.put("loginTrueId", String.valueOf(member.getMemberId()));
                    return ResponseEntity.status(HttpStatus.OK).body(loginSuccess);
                } catch (Exception e) {
                    String fieldName = String.valueOf(e.getCause());
                    String errorMessage = e.getMessage();
                    List<Map<String, String>> loginErrors = new ArrayList<>();
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("loginError", fieldName + " : " + errorMessage);
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

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        Member member = (Member) session.getAttribute("login");
        if (member != null) {
            session.removeAttribute("login");
            session.invalidate();
            Cookie cookie = WebUtils.getCookie(request, "loginCookie");

            if (cookie != null) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                memberService.autoLogin("none", new Date(), member.getLoginId());
            }

            Map<String, String> logoutSuccess = new HashMap<>();
            logoutSuccess.put("logoutMsg", "로그아웃되었습니다.");
            return ResponseEntity.status(HttpStatus.OK).body(logoutSuccess);
        } else {
            List<Map<String, String>> loginErrors = new ArrayList<>();
            String fieldName = "로그아웃";
            String errorMessage = "이미 로그아웃 되어있습니다.";

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("logoutError", fieldName + " : " + errorMessage);
            loginErrors.add(errorMap);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(loginErrors);
        }
    }
}