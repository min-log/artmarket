package llustmarket.artmarket.web.controller.member;

import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.web.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class LoginController {

    @Autowired
    MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<Object> processLogin(@RequestBody Map<String, String> loginData, HttpSession session) {
        Member member = memberService.getMemberByLoginId(loginData.get("loginId"));
        if (member == null || !member.getPassword().equals(loginData.get("loginPassword"))) {
            Map<String, String> loginFail = new HashMap<>();
            loginFail.put("loginNoMatchMsg", "아이디 또는 패스워드를 확인하세요.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginFail);
        }
        try {
            Map<String, String> loginSuccess = new HashMap<>();
            loginSuccess.put("loginTrueIdentity", member.getIdentity());
            loginSuccess.put("loginTrueId ", member.getMemberId());
            session.setAttribute("loggedInUser", member); // 세션에 사용자 정보 저장
            return ResponseEntity.status(HttpStatus.OK).body(loginSuccess);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}