package llustmarket.artmarket.web.controller.mypage.member;

import llustmarket.artmarket.web.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MyPageUpdateController {

    private final MemberService memberService;

    @PostMapping("/mypage-update-identity")
    public ResponseEntity<Object> changeIdentity(@RequestBody Map<String, Long> requestMap) {
        Long id = requestMap.get("id");
        try {
            memberService.updateIdentity(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/mypage-update-intro")
    public ResponseEntity<Object> changeIntro(@RequestBody Map<String, String> requestMap) {
        Long id = Long.valueOf(requestMap.get("id"));
        String intro = String.valueOf(requestMap.get("memberIntro"));
        try {
            memberService.updateIntro(intro, id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
