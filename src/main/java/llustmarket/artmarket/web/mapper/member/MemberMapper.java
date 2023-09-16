package llustmarket.artmarket.web.mapper.member;

import llustmarket.artmarket.domain.member.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface MemberMapper {
    void insertMember(Member member);

    void updateIdentity(Long memberId, String identity);

    void updatePasswordByMemberId(@Param("password") String password, @Param("memberId") Long memberId);

    int isLoginIdDuplicate(String loginId);

    int isNicknameDuplicate(String nickname);

    int isEmailDuplicate(String email);

    int isPhoneDuplicate(String phone);

    Member selectMemberByLoginId(String loginId);

    Member selectMemberByPhone(String phone);

    Member selectMemberByMemberId(Long memberId);

    //특정 회원의 세션아이디와 쿠키 유효기간을 저장
    void autoLogin(Map<String, Object> map);

}
