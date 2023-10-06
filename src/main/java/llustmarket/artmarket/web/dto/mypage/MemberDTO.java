package llustmarket.artmarket.web.dto.mypage;


import lombok.Data;

import java.util.Date;


@Data
public class MemberDTO {
    private String orderId; //의뢰(주문)번호
    private String nickname; //작가명
    private Long totalAmount; //총 결제 금액
    private Date orderDate; //주문 날짜
    private Date deadline;//마감 기한
    private String orderStatus; //주문상태


}
