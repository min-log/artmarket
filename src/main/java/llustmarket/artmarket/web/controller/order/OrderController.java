package llustmarket.artmarket.web.controller.order;

import llustmarket.artmarket.domain.order.Order;
import llustmarket.artmarket.web.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //주문 정보
    @PostMapping("/order")
    public void insertMemberOrder(@RequestBody @Valid Order order) {

        orderService.insertOrder(order);
    }

    //작가 회원

    @GetMapping("/mypage-orderAuthor/{member_id}") //멤버아이디 넣어서 시도해야함
    public ResponseEntity getOrderAuthorList(@PathVariable(value = "member_id") String memberId) {

        return new ResponseEntity<>(orderService.getOrderAuthorList(memberId), HttpStatus.OK);
    }

    //일반 회원
    @GetMapping("/mypage-orderMember/{member_id}") //멤버아이디 넣어서 시도해야함
    public ResponseEntity getOrderMemberList(@PathVariable(value = "member_id") String memberId) {

        return new ResponseEntity<>(orderService.getOrderMemberList(memberId), HttpStatus.OK);
    }

   /* @GetMapping("/mypage-order/search")
    public ResponseEntity getSearch() {
        return new ResponseEntity<>(orderService.getSearch(), HttpStatus.OK);
    }*/


    //주문상태 변환
    @PostMapping("/order-status")
    public void changeOrderStatus(@RequestBody Order order) {
        orderService.OrderStatus(order);
    }

}
