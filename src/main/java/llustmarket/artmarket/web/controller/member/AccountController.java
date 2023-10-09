package llustmarket.artmarket.web.controller.member;

import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.web.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final MemberService memberService;

    @GetMapping("/editMember")
    public String showEditMemberForm(Model model) {
        Member member = new Member();
        model.addAttribute("member", member);
        return "editMember";
    }

    @PostMapping("/editMember")
    public String editMember(@ModelAttribute Member member) {
        try {
            memberService.updateAccount(member.getAccountBank(), member.getAccount(), member.getMemberId());
            return "redirect:/editMember";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/editMember";
        }
    }
}
