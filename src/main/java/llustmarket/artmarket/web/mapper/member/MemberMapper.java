package llustmarket.artmarket.web.mapper.member;

import llustmarket.artmarket.domain.member.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void insertMember(Member member);

    void updateIdentity(Long memberId, String identity);

    void updatePasswordByMemberId(@Param("password") String password, @Param("memberId") Long memberId);

    void updatePhoneByMemberId(@Param("memberId") Long memberId, @Param("newPhone") String newPhone);

    void updateEmailByMemberId(@Param("memberId") Long memberId, @Param("newEmail") String newEmail);

    int isLoginIdDuplicate(String loginId);

    int isNicknameDuplicate(String nickname);

    int isEmailDuplicate(String email);

    int isPhoneDuplicate(String phone);
    Member selectOneByMemberId(long memberId);
}
