package llustmarket.artmarket.web.controller.member;

import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.web.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping
public class JoinController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/join")
    public String showJoinForm(Model model) {
        model.addAttribute("member", new Member());
        return "html/member/join";
    }

    @PostMapping("/join")
    public String processJoin(@Validated @ModelAttribute Member member, BindingResult bindingResult, @RequestParam("user-author-check") String authorCheck, Model model) {
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "html/member/join";
        }
        // 중복 체크
        if (memberService.isLoginIdDuplicate(member.getLoginId()) ||
                memberService.isNicknameDuplicate(member.getNickname()) ||
                memberService.isEmailDuplicate(member.getEmail()) ||
                memberService.isPhoneDuplicate(member.getPhone()) ||
                !member.getPassword().equals(member.getConfirmPassword())) {

            if (memberService.isLoginIdDuplicate(member.getLoginId())) {
                model.addAttribute("loginIdError", "이미 사용 중인 로그인 ID입니다.");
            }

            if (memberService.isNicknameDuplicate(member.getNickname())) {
                model.addAttribute("nicknameError", "이미 사용 중인 닉네임입니다.");
            }

            if (memberService.isEmailDuplicate(member.getEmail())) {
                model.addAttribute("emailError", "이미 사용 중인 이메일입니다.");
            }

            if (memberService.isPhoneDuplicate(member.getPhone())) {
                model.addAttribute("phoneError", "이미 사용 중인 전화번호입니다.");
            }

            if (!member.getPassword().equals(member.getConfirmPassword())) {
                model.addAttribute("confirmPasswordError", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            }

            return "html/member/join";
        }

        // identity 설정
        if (authorCheck.equals("user")) {
            member.setIdentity("user");
        } else if (authorCheck.equals("author")) {
            member.setIdentity("author");
        }

        // 회원 가입 로직 수행
        memberService.insertMember(member);

        return "redirect:/index"; // 가입 후 홈 페이지로 리다이렉트
    }
}