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
    public String editMember(@ModelAttribute Member member, Model model) {
        try {
            Member memberByMemberId = memberService.getMemberByMemberId(member.getMemberId());
            if (memberByMemberId == null) {
                model.addAttribute("errorMessage", "회원 아이디에 해당하는 회원이 없습니다. 정보를 확인해주세요.");
            }
            if (member.getAccountBank().equals("")) {
                model.addAttribute("errorMessage", "은행명 정보를 입력해주세요.");
            }
            if (member.getAccount().equals("")) {
                model.addAttribute("errorMessage", "계좌번호를 입력해주세요.");
            }
            if (memberByMemberId != null && !member.getAccountBank().equals("") && !member.getAccount().equals("")) {
                memberService.updateAccount(member.getAccountBank(), member.getAccount(), member.getMemberId());
                model.addAttribute("successMessage", "회원 정보가 성공적으로 업데이트되었습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "회원 정보 업데이트에 실패했습니다. 다시 시도해주세요.");
        }
        return "editMember";
    }
}
