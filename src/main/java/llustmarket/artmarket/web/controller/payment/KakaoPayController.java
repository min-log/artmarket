package llustmarket.artmarket.web.controller.payment;


import llustmarket.artmarket.web.dto.payment.KakaoApproveResponse;
import llustmarket.artmarket.web.dto.payment.KakaoCancelResponse;
import llustmarket.artmarket.web.dto.payment.KakaoReadyResponse;
import llustmarket.artmarket.web.service.payment.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController //HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
@RequiredArgsConstructor //final 붙거나 @NotNull이 붙은 필드의 생성자 추가
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;

    /**
     * 결제요청
     */
    @PostMapping("/ready")
    public KakaoReadyResponse readyToKakaoPay() {

        return kakaoPayService.kakaoPayReady();
    }

    /**
     * 결제 성공
     */
    @GetMapping("/success-order")
    public ResponseEntity afterPayRequest(@RequestParam("pg_token") String pgToken) {

        KakaoApproveResponse kakaoApprove = kakaoPayService.ApproveResponse(pgToken);

        return new ResponseEntity<>(kakaoApprove, HttpStatus.OK);
    }

    /**
     * 결제 진행 중 취소 및 결제 실패
     */
    @GetMapping("/product/:product_id")
    public String cancel() {
        return "redirect:/product";

    }

    /**
     * 환불
     */
    @PostMapping("/refund")
    public ResponseEntity refund() {

        KakaoCancelResponse kakaoCancelResponse = kakaoPayService.kakaoCancel();

        return new ResponseEntity<>(kakaoCancelResponse, HttpStatus.OK);
    }
}

