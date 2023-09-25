package llustmarket.artmarket.web.service.alert;

import llustmarket.artmarket.domain.alert.Alert;
import llustmarket.artmarket.domain.alert.AlertType;
import llustmarket.artmarket.web.dto.alert.AlertDTO;
import llustmarket.artmarket.web.dto.alert.AlramDTO;
import llustmarket.artmarket.web.dto.alert.Alrams;
import llustmarket.artmarket.web.dto.chat.ChatRoomDTO;
import llustmarket.artmarket.web.dto.member.MemberDTO;
import llustmarket.artmarket.web.mapper.alert.AlertMapper;
import llustmarket.artmarket.web.service.chat.ChatRoomService;
import llustmarket.artmarket.web.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class AlertServiceImpl implements AlertService{

    private final MemberService memberService;
    private final ChatRoomService chatRoomService;
    private final AlertMapper alertMapper;

    @Transactional
    @Override
    public void registerAlert(long memberId,long alertPath, AlertType alertType) {

        //현제 회원 정보
        MemberDTO member = null;
        // 상대 회원 Id
        long authorMemberId = 0;

        switch (String.valueOf(alertType)){
            case "MESSAGE" : {
                    // 현제 회원 구하기
                // 상대 회원 아이디값만 구하기
                member = memberService.selectOne(memberId);
                // 상대 회원 아이디
                ChatRoomDTO chatRoomDTO = chatRoomService.searchChatRoomId(alertPath);
                authorMemberId = chatRoomDTO.getChatFromId();
                if(memberId == authorMemberId) authorMemberId = chatRoomDTO.getChatToId();
            }
            default  : {
                // 주문상품

            }
        }

        Alert alert = Alert.builder()
                //상대방 회원 정보
                .memberId(authorMemberId)
                //나의 정보
                .alertWriter(member.getNickname())
                .alertIdentity(member.getIdentity())
                .alertType(String.valueOf(alertType))
                .alertPath(alertPath)
                .build();
        alertMapper.insertOne(alert);
    }

    @Override
    public Alrams searchOneAlert(long memberId) {
        log.info("# 회원의 알림 리스트");
        List<Alert> alerts = alertMapper.selectOne(memberId);
        List<AlramDTO> alramList = alerts.stream().map(item -> {
            return AlramDTO.builder()
                    .alramSender(item.getAlertWriter())
                    .alramType(item.getAlertType())
                    .alertDate(item.getAlertDate()).build();
        }).collect(Collectors.toList());
        return Alrams.builder().alrams(alramList).build();
    }
}
