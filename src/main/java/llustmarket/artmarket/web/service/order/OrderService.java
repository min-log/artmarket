package llustmarket.artmarket.web.service.order;

import llustmarket.artmarket.domain.order.Order;
import llustmarket.artmarket.web.dto.order.OrderDTO;
import llustmarket.artmarket.web.mapper.order.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Log4j2
@RequiredArgsConstructor
@Service
public class OrderService {
    private final ModelMapper modelMapper;
    private final OrderMapper orderMapper;
    public OrderDTO selectOne(long productId,long memberId){
        Order vo = Order.builder().productId(productId).memberId(memberId).build();
        Order order = orderMapper.searchOneDeadline(vo);
        log.info("order : {}",order);
        if(order == null) return null;
        return modelMapper.map(order,OrderDTO.class);
    }
}
