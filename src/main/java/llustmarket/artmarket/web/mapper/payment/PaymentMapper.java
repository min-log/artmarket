package llustmarket.artmarket.web.mapper.payment;

import llustmarket.artmarket.web.dto.payment.KakaoApproveResponse;
import llustmarket.artmarket.web.dto.payment.KakaoCancelResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {
    void insertPayment(KakaoApproveResponse approveResponse);

    void insertRefund(KakaoCancelResponse cancelResponse);

}
