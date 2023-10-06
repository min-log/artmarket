package llustmarket.artmarket.web.dto.mypage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class AuthorDTO {
    private String orderId; //의뢰(주문)번호
    private String nickname; //의뢰자명
    private Long totalAmount; //총 결제 금액
    private Date orderDate; //주문 날짜
    private Date deadline;//마감 기한
    private String orderStatus; //주문상태


}
