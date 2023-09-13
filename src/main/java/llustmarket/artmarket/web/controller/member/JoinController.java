package llustmarket.artmarket.web.controller.member;

import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.web.dto.member.JoinRequestDTO;
import llustmarket.artmarket.web.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
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
    public ResponseEntity<Object> processJoin(@Valid @RequestBody JoinRequestDTO joinRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Map<String, String>> joinErrors = new ArrayList<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("joinErrorMsg", errorMessage);
                errorMap.put("joinErrorParam", fieldName);

                joinErrors.add(errorMap);
            }

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(joinErrors);
        }

        List<Map<String, String>> duplications = new ArrayList<>();

        checkDuplication("JoinLoginId", joinRequest.getJoinLoginId(), "아이디", duplications);
        checkDuplication("JoinNickname", joinRequest.getJoinNickname(), "닉네임", duplications);
        checkDuplication("JoinEmail", joinRequest.getJoinEmail(), "이메일", duplications);
        checkDuplication("JoinPhone", joinRequest.getJoinPhone(), "전화번호", duplications);

        if (!duplications.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(duplications);
        }

        try {
            Member member = new Member(
                    joinRequest.getJoinName(),
                    joinRequest.getJoinNickname(),
                    joinRequest.getJoinLoginId(),
                    joinRequest.getJoinPassword(),
                    joinRequest.getJoinPhone(),
                    joinRequest.getJoinEmail(),
                    joinRequest.getJoinIdentity()
            );
            memberService.insertMember(member);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    private void checkDuplication(String fieldName, String fieldValue, String fieldDisplayName, List<Map<String, String>> duplications) {
        if (fieldName.equals("JoinLoginId") && memberService.isLoginIdDuplicate(fieldValue)) {
            Map<String, String> duplication = new HashMap<>();
            duplication.put("duplicateParam", fieldDisplayName);
            duplication.put("duplicateMsg", "이미 존재하는 " + fieldDisplayName + " 입니다.");
            duplications.add(duplication);
        }

        if (fieldName.equals("JoinNickname") && memberService.isNicknameDuplicate(fieldValue)) {
            Map<String, String> duplication = new HashMap<>();
            duplication.put("duplicateParam", fieldDisplayName);
            duplication.put("duplicateMsg", "이미 존재하는 " + fieldDisplayName + " 입니다.");
            duplications.add(duplication);
        }

        if (fieldName.equals("JoinEmail") && memberService.isEmailDuplicate(fieldValue)) {
            Map<String, String> duplication = new HashMap<>();
            duplication.put("duplicateParam", fieldDisplayName);
            duplication.put("duplicateMsg", "이미 존재하는 " + fieldDisplayName + " 입니다.");
            duplications.add(duplication);
        }

        if (fieldName.equals("JoinPhone") && memberService.isPhoneDuplicate(fieldValue)) {
            Map<String, String> duplication = new HashMap<>();
            duplication.put("duplicateParam", fieldDisplayName);
            duplication.put("duplicateMsg", "이미 존재하는 " + fieldDisplayName + " 입니다.");
            duplications.add(duplication);
        }
    }
}