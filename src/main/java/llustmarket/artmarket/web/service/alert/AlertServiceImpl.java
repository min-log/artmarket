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
        //상대 회원 구하기
        MemberDTO memberDTO = null;
        switch (String.valueOf(alertType)){
            case "MESSAGE" : {
                // 메시지
                log.info("메시지");
                ChatRoomDTO chatRoomDTO = chatRoomService.searchChatRoomId(alertPath);
                long authorMember = chatRoomDTO.getChatFromId();
                if(memberId == authorMember) authorMember = chatRoomDTO.getChatToId();
                memberDTO =memberService.selectOne(authorMember);
            }
            default  : {
                // 주문상품

            }

        }

        Alert alert = Alert.builder()
                .memberId(memberDTO.getMemberId())
                .alertWriter(memberDTO.getNickname())
                .alertIdentity(memberDTO.getIdentity())
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
            MemberDTO memberDTO = memberService.selectOne(item.getMemberId());
            return AlramDTO.builder()
                    .alramSender(memberDTO.getNickname())
                    .alramType(item.getAlertType())
                    .alertDate(item.getAlertDate()).build();
        }).collect(Collectors.toList());
        return Alrams.builder().alrams(alramList).build();
    }
}
