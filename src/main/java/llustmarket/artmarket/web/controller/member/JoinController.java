package llustmarket.artmarket.web.controller.member;

import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.web.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class JoinController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<Object> processJoin(@RequestBody Map<String, String> joinData) {

        List<Map<String, String>> joinDuplications = new ArrayList<>();

        checkDuplication("JoinLoginId", joinData.get("JoinLoginId"), "아이디", joinDuplications);
        checkDuplication("JoinNickname", joinData.get("JoinNickname"), "닉네임", joinDuplications);
        checkDuplication("JoinEmail", joinData.get("JoinEmail"), "이메일", joinDuplications);
        checkDuplication("JoinPhone", joinData.get("JoinPhone"), "전화번호", joinDuplications);

        // 중복된 데이터가 있을 경우
        if (!joinDuplications.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(joinDuplications);
        }

        try {
            // Member 객체를 생성하여 DB에 추가
            Member member = new Member(
                    joinData.get("JoinName"),
                    joinData.get("JoinNickname"),
                    joinData.get("JoinLoginId"),
                    joinData.get("JoinPassword"),
                    joinData.get("JoinPhone"),
                    joinData.get("JoinEmail"),
                    joinData.get("JoinIdentity")
            );
            memberService.insertMember(member);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void checkDuplication(String fieldName, String fieldValue, String fieldDisplayName, List<Map<String, String>> joinDuplications) {
        if (fieldName.equals("JoinLoginId") && memberService.isLoginIdDuplicate(fieldValue)) {
            Map<String, String> duplication = new HashMap<>();
            duplication.put("duplicateParam", fieldDisplayName);
            duplication.put("duplicateMsg", "이미 존재하는 " + fieldDisplayName + " 입니다.");
            joinDuplications.add(duplication);
        }

        if (fieldName.equals("JoinNickname") && memberService.isNicknameDuplicate(fieldValue)) {
            Map<String, String> duplication = new HashMap<>();
            duplication.put("duplicateParam", fieldDisplayName);
            duplication.put("duplicateMsg", "이미 존재하는 " + fieldDisplayName + " 입니다.");
            joinDuplications.add(duplication);
        }

        if (fieldName.equals("JoinEmail") && memberService.isEmailDuplicate(fieldValue)) {
            Map<String, String> duplication = new HashMap<>();
            duplication.put("duplicateParam", fieldDisplayName);
            duplication.put("duplicateMsg", "이미 존재하는 " + fieldDisplayName + " 입니다.");
            joinDuplications.add(duplication);
        }

        if (fieldName.equals("JoinPhone") && memberService.isPhoneDuplicate(fieldValue)) {
            Map<String, String> duplication = new HashMap<>();
            duplication.put("duplicateParam", fieldDisplayName);
            duplication.put("duplicateMsg", "이미 존재하는 " + fieldDisplayName + " 입니다.");
            joinDuplications.add(duplication);
        }
    }
}