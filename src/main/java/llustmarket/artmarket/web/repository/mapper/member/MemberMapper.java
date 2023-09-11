package llustmarket.artmarket.web.repository.mapper.member;

import llustmarket.artmarket.domain.member.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void insertMember(Member member);

    void updateIdentity(Long memberId, String identity);

    int isLoginIdDuplicate(String loginId);

    int isNicknameDuplicate(String nickname);

    int isEmailDuplicate(String email);

    int isPhoneDuplicate(String phone);
}