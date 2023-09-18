package llustmarket.artmarket.web.controller.member;

import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.web.dto.member.UpdatePassDTO;
import llustmarket.artmarket.web.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/api")
@RestController
public class UpdateController {

    @Autowired
    MemberService memberService;

    @PatchMapping("/update-password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePassDTO updatePass, BindingResult bindingResult) {
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
            memberService.updatePasswordByMemberId(member.getMemberId(), updatePass.getUpdatePassword());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
