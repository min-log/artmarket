package llustmarket.artmarket.web.service.member;

import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.web.mapper.member.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    public void insertMember(Member member) {
        memberMapper.insertMember(member);
    }

    public void updateIdentity(Long memberId, String identity) {
        memberMapper.updateIdentity(memberId, identity);
    }

    public boolean isLoginIdDuplicate(String loginId) {
        return memberMapper.isLoginIdDuplicate(loginId) > 0;
    }

    public boolean isNicknameDuplicate(String nickname) {
        return memberMapper.isNicknameDuplicate(nickname) > 0;
    }

    public boolean isEmailDuplicate(String email) {
        return memberMapper.isEmailDuplicate(email) > 0;
    }

    public boolean isPhoneDuplicate(String phone) {
        return memberMapper.isPhoneDuplicate(phone) > 0;
    }

    public Member getMemberByLoginId(String loginId) {
        return memberMapper.selectMemberByLoginId(loginId);
    }
}