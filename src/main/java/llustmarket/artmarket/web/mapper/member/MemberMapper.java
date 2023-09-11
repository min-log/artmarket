package llustmarket.artmarket.web.mapper.member;

import llustmarket.artmarket.domain.member.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void insertMember(Member member);
    Member selectOneByMemberId(long memberId);
}
