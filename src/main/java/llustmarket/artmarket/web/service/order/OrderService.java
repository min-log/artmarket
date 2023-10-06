package llustmarket.artmarket.web.service.order;

import llustmarket.artmarket.domain.order.Order;
import llustmarket.artmarket.web.dto.board.AuthorDTO;
import llustmarket.artmarket.web.dto.member.MemberDTO;
import llustmarket.artmarket.web.dto.order.OrderDTO;
import llustmarket.artmarket.web.mapper.order.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;


@Log4j2
@RequiredArgsConstructor
@Service
public class OrderService {
    private final ModelMapper modelMapper;
    private final OrderMapper orderMapper;

    public OrderDTO selectOne(long productId, long memberId) {
        Order vo = Order.builder().productId(productId).memberId(memberId).build();
        Order order = orderMapper.searchOneDeadline(vo);
        log.info("order : {}", order);
        if (order == null) return null;
        return modelMapper.map(order, OrderDTO.class);
    }


    public void insertOrder(Order order) {


        order.setOrderId(orderMapper.selectOrderId());
        try {
            orderMapper.insertOrder(order); // OrderVO를 OrderMapper에 삽입
        } catch (DuplicateKeyException d) {
            orderMapper.insertOrder(order);
            //throw new RuntimeException("키 중복되었으니 다시 시도 바랍니다.");
        }
    }


    public List<AuthorDTO> getOrderAuthorList(String memberId) {
        List<AuthorDTO> authorList = orderMapper.selectAuthor(memberId);
        return authorList;
    }


    public List<MemberDTO> getOrderMemberList(String memberId) {
        List<MemberDTO> memberList = orderMapper.selectMember(memberId);
        return memberList;
    }

   /*
    public List<OrderDTO> getSearch() {
        List<OrderDTO> getSearch = orderMapper.getSearch();
        return getSearch;
    }*/


    public void OrderStatus(Order order) {

        orderMapper.updateOrderStatus(order);
    }
}
