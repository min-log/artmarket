package llustmarket.artmarket.web.service.member;

import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.web.mapper.member.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Transactional
    public void insertMember(Member member) {
        memberMapper.insertMember(member);
    }

    @Transactional
    public void updateIdentity(Long memberId, String identity) {
        memberMapper.updateIdentity(memberId, identity);
    }

    public void updatePasswordByMemberId(Long memberId, String password) {
        memberMapper.updatePasswordByMemberId(password, memberId);
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

    public Member getMemberByMemberId(Long memberId) {
        return memberMapper.selectMemberByMemberId(memberId);
    }

    public Member getMemberByPhone(String phone) {
        return memberMapper.selectMemberByPhone(phone);
    }

    @Transactional
    public void autoLogin(String sessionId, Date limitDate, String loginId) {

        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", sessionId);
        map.put("limitDate", limitDate);
        map.put("loginId", loginId);
        System.out.print(map);

        memberMapper.autoLogin(map);

    }

}