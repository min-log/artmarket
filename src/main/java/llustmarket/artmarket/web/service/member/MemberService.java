package llustmarket.artmarket.web.service.member;

import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.web.dto.member.MemberDTO;
import llustmarket.artmarket.web.mapper.member.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberMapper memberMapper;
    private final ModelMapper mapper;

    public void insertArtistMember(Member member) {
        // identity가 비어있을 경우 기본값인 "artist"로 설정
        if (member.getIdentity() == null || member.getIdentity().isEmpty()) {
            member.setIdentity("artist");
        }
        memberMapper.insertMember(member);
    }

    public void insertNormalMember(Member member) {
        // identity가 비어있을 경우 기본값인 "normal"로 설정
        if (member.getIdentity() == null || member.getIdentity().isEmpty()) {
            member.setIdentity("normal");
        }
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

    public MemberDTO selectOne(long memberId){
        Member member = memberMapper.selectOneByMemberId(memberId);
        return mapper.map(member, MemberDTO.class);
    }
}