package llustmarket.artmarket.web.controller.member;

import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.web.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    MemberService memberService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("form", new LoginForm());
        return "member/login";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute LoginForm form, HttpServletRequest request, Model model) {
        // 실제 로그인 로직을 구현해야 합니다.
        Member loggedInMember = memberService.getMemberByLoginId(form.getLoginId());

        if (loggedInMember != null && loggedInMember.getPassword().equals(form.getPassword())) {
            // 로그인 성공
            HttpSession session = request.getSession();
            session.setAttribute("loggedInMember", loggedInMember);
            return "redirect:/home";
        } else {
            // 로그인 실패
            model.addAttribute("loginError", "정확하지 않은 정보입니다.");
            return "member/login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/home";
    }
}