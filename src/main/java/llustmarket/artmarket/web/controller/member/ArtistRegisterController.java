package llustmarket.artmarket.web.controller.member;

import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.web.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@Validated
@RequiredArgsConstructor
public class ArtistRegisterController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/artist/register")
    public String showArtistRegistrationForm(Model model) {
        model.addAttribute("member", new Member());
        return "member/artist_register";
    }

    @PostMapping("/artist/register")
    public String processArtistRegistrationForm(@Valid @ModelAttribute("member") Member member, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // 검증 에러가 있을 경우 처리
            for (FieldError error : bindingResult.getFieldErrors()) {
                log.info("Validation error for field {}: {}", error.getField(), error.getDefaultMessage());
            }
            return "member/artist_register";
        }

        // Check for duplicate information
        boolean isLoginIdDuplicate = memberService.isLoginIdDuplicate(member.getLoginId());
        boolean isNicknameDuplicate = memberService.isNicknameDuplicate(member.getNickname());
        boolean isEmailDuplicate = memberService.isEmailDuplicate(member.getEmail());
        boolean isPhoneDuplicate = memberService.isPhoneDuplicate(member.getPhone());

        if (isLoginIdDuplicate || isNicknameDuplicate || isEmailDuplicate || isPhoneDuplicate) {
            if (isLoginIdDuplicate) {
                bindingResult.rejectValue("loginId", "duplicate.loginId", "중복된 아이디입니다.");
            }
            if (isNicknameDuplicate) {
                bindingResult.rejectValue("nickname", "duplicate.nickname", "중복된 닉네임입니다.");
            }
            if (isEmailDuplicate) {
                bindingResult.rejectValue("email", "duplicate.email", "중복된 이메일입니다.");
            }
            if (isPhoneDuplicate) {
                bindingResult.rejectValue("phone", "duplicate.phone", "중복된 휴대폰 번호입니다.");
            }
            return "member/artist_register";
        }

        // 회원 등록
        memberService.insertArtistMember(member);
        return "redirect:/home"; // 성공 페이지로 리다이렉트
    }
}