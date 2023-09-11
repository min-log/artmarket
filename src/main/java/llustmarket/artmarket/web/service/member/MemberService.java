package llustmarket.artmarket.web.service.member;


import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.web.dto.member.MemberDTO;
import llustmarket.artmarket.web.mapper.member.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberMapper MemberMapper;
    private final ModelMapper mapper;


    public MemberDTO selectOne(long memberId){
        Member member = MemberMapper.selectOneByMemberId(memberId);
        return mapper.map(member, MemberDTO.class);
    }



}
