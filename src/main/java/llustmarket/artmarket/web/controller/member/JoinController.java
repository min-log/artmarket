package llustmarket.artmarket.web.controller.member;

import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.web.dto.member.ConfirmEmailDTO;
import llustmarket.artmarket.web.dto.member.JoinRequestDTO;
import llustmarket.artmarket.web.service.member.EmailService;
import llustmarket.artmarket.web.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class JoinController {
    private final RedisTemplate<String, String> redisTemplate;
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    private final EmailService emailService;

    public JoinController(PasswordEncoder passwordEncoder, MemberService memberService, EmailService emailService, RedisTemplate<String, String> redisTemplate) {
        this.passwordEncoder = passwordEncoder;
        this.memberService = memberService;
        this.emailService = emailService;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/join-confirm")
    public ResponseEntity<Object> sendTokenByEmail(@RequestBody @Valid ConfirmEmailDTO confirmEmail, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Map<String, String>> confirmEmailErrors = new ArrayList<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("confirmEmailErrorParam", fieldName);
                errorMap.put("confirmEmailErrorMsg", errorMessage);
                confirmEmailErrors.add(errorMap);
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(confirmEmailErrors);
        }

        String uuid = UUID.randomUUID().toString();
        StringBuilder numericString = new StringBuilder();
        for (char c : uuid.toCharArray()) {
            if (Character.isDigit(c)) {
                numericString.append(c);
            }
        }
        if (numericString.length() < 6) {
            List<Map<String, String>> confirmEmailErrors = new ArrayList<>();

            String fieldName = "인증번호 생성 오류";
            String errorMessage = "문자열에 6개 이상의 숫자가 필요합니다.";

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("confirmEmailErrorParam", fieldName);
            errorMap.put("confirmEmailErrorMsg", errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(confirmEmailErrors);
        }
        String token = numericString.substring(0, 6);

        // 생성한 토큰으로 저장
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        Boolean lock = ops.setIfAbsent("confirmEmail:" + token, token, 5, TimeUnit.MINUTES);

        if (lock != null && lock) {
            long expirationTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5);

            emailService.sendTokenByEmail(confirmEmail.getConfirmEmail(), token, new Date(expirationTime));

            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            String errorMessage = "이메일 전송이 이미 진행 중입니다. 잠시 후 다시 시도하세요.";

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("confirmEmailErrorParam", "confirmEmail");
            errorMap.put("confirmEmailErrorMsg", errorMessage);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
        }
    }

    @GetMapping("/join-confirm/{token}")
    public ResponseEntity<Object> verifyToken(@PathVariable String token) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        String storedToken = ops.get("confirmEmail:" + token);

        if (storedToken == null || !storedToken.equals(token)) {
            log.error("Token verification failed. Stored Token: {}", storedToken);
            String errorMessage = "유효시간이 지났거나 토큰이 올바르지 않습니다.";

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("confirmEmailErrorParam", "token");
            errorMap.put("confirmEmailErrorMsg", errorMessage);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
        }

        try {
            redisTemplate.delete("confirmEmail:" + token);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            log.error("Error while deleting token from Redis: {}", e.getMessage());
            String errorMessage = "토큰 삭제 중 오류가 발생했습니다.";

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("confirmEmailErrorParam", "token");
            errorMap.put("confirmEmailErrorMsg", errorMessage);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap);
        }
    }


    @PostMapping("/join")
    public ResponseEntity<Object> processJoin(@Valid @RequestBody JoinRequestDTO joinRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<Map<String, String>> joinErrors = new ArrayList<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("joinErrorParam", fieldName);
                errorMap.put("joinErrorMsg", errorMessage);
                joinErrors.add(errorMap);
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(joinErrors);
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
            String hashedPassword = passwordEncoder.encode(joinRequest.getJoinPassword());
            Member member = new Member(
                    joinRequest.getJoinName(),
                    joinRequest.getJoinNickname(),
                    joinRequest.getJoinLoginId(),
                    hashedPassword,
                    joinRequest.getJoinPhone(),
                    joinRequest.getJoinEmail(),
                    joinRequest.getJoinIdentity()
            );
            memberService.insertMember(member);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            List<Map<String, String>> joinErrors = new ArrayList<>();
            String fieldName = String.valueOf(e.getCause());
            String errorMessage = e.getMessage();

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("joinErrorParam", fieldName);
            errorMap.put("joinErrorMsg", errorMessage);
            joinErrors.add(errorMap);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(joinErrors);
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