package llustmarket.artmarket.web.controller.member;

import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.web.dto.member.UpdatePassDTO;
import llustmarket.artmarket.web.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class UpdateController {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PatchMapping("/update-password")
    public ResponseEntity<Object> updatePassword(@Valid @RequestBody UpdatePassDTO updatePass, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Map<String, String>> updateErrors = new ArrayList<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("updateErrorParam", fieldName);
                errorMap.put("updateErrorMsg", errorMessage);
                updateErrors.add(errorMap);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updateErrors);
        }
        try {
            Member member = memberService.getMemberByMemberId(updatePass.getUpdatePassId());
            if (member != null) {
                String hashedPassword = passwordEncoder.encode(updatePass.getUpdatePassword());
                memberService.updatePasswordByMemberId(member.getMemberId(), hashedPassword);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                List<Map<String, String>> updateErrors = new ArrayList<>();
                String fieldName = "멤버 조회 오류";
                String errorMessage = "member_id에 해당하는 멤버가 없습니다.";

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("updateErrorParam", fieldName);
                errorMap.put("updateErrorMsg", errorMessage);
                updateErrors.add(errorMap);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updateErrors);
            }
        } catch (Exception e) {
            List<Map<String, String>> updateErrors = new ArrayList<>();
            String fieldName = String.valueOf(e.getCause());
            String errorMessage = e.getMessage();

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("updateErrorParam", fieldName);
            errorMap.put("updateErrorMsg", errorMessage);
            updateErrors.add(errorMap);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updateErrors);
        }
    }
}
