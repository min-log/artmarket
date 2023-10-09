package llustmarket.artmarket.web.mapper.member;

import llustmarket.artmarket.domain.member.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Mapper
public interface MemberMapper {
    void insertMember(Member member);

    @Transactional
    void updateIdentity(Long memberId);

    @Transactional
    void updateAccount(@Param("accountBank") String accountBank, @Param("account") String account, @Param("memberId") Long memberId);

    @Transactional
    void updateIntro(@Param("memberIntro") String intro, @Param("memberId") Long memberId);

    @Transactional
    void updatePasswordByMemberId(@Param("password") String password, @Param("memberId") Long memberId);

    @Transactional
    void updatePasswordByEmail(@Param("password") String password, @Param("email") String email);

    @Transactional
    void updatePhoneByMemberId(@Param("memberId") Long memberId, @Param("newPhone") String newPhone);

    @Transactional
    void updateEmailByMemberId(@Param("memberId") Long memberId, @Param("newEmail") String newEmail);

    int isLoginIdDuplicate(String loginId);

    int isNicknameDuplicate(String nickname);

    int isEmailDuplicate(String email);

    int isPhoneDuplicate(String phone);

    Member selectMemberByLoginId(String loginId);

    Member selectMemberByPhone(String phone);

    Member selectMemberByMemberId(Long memberId);

    Optional<Member> findByUserEmail(String email);

    Optional<Member> findHighestMemberId();

    //특정 회원의 세션아이디와 쿠키 유효기간을 저장
    void autoLogin(Map<String, Object> map);

    Member selectOneByMemberId(long memberId);

    void withdrawl(long memberId);
}
