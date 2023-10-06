package llustmarket.artmarket.web.controller.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.web.dto.member.JoinSocialDTO;
import llustmarket.artmarket.web.service.member.KakaoUserService;
import llustmarket.artmarket.web.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class KakaoLoginController {
    private final MemberService memberService;
    private final KakaoUserService kakaoUserService;

    @PostMapping("/agree-kakao")
    public ResponseEntity<Object> confirmDuplicateKakao(@RequestBody @Valid JoinSocialDTO request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Map<String, String>> confirmKakakoErrors = new ArrayList<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("confirmKakaoErrorParam", fieldName);
                errorMap.put("confirmKakaoErrorMsg", errorMessage);
                confirmKakakoErrors.add(errorMap);
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(confirmKakakoErrors);
        }
        try {
            List<Map<String, String>> duplications = new ArrayList<>();
            checkDuplication("JoinNickname", request.getJoinNickname(), "닉네임", duplications);
            checkDuplication("JoinEmail", request.getJoinEmail(), "이메일", duplications);
            checkDuplication("JoinPhone", request.getJoinPhone(), "전화번호", duplications);
            if (!duplications.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(duplications);
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            List<Map<String, String>> confirmKakakoErrors = new ArrayList<>();
            String fieldName = String.valueOf(e.getCause());
            String errorMessage = e.getMessage();

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("confirmKakaoErrorParam", fieldName);
            errorMap.put("confirmKakaoErrorMsg", errorMessage);
            confirmKakakoErrors.add(errorMap);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(confirmKakakoErrors);
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

    // 카카오 로그인
    @GetMapping("/kakao-login")
    public void kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        log.info("code={}", code);
        kakaoUserService.kakaoLogin(code, response);
    }

    @PostMapping("/join-kakao")
    public ResponseEntity<Object> joinKakao(@RequestBody JoinSocialDTO request) {
        try {
            Member member = kakaoUserService.registerKakao(request);
            if (member != null) {
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } else {
                log.error("멤버가 널입니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
