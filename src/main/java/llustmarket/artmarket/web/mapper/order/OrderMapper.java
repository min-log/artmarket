package llustmarket.artmarket.web.mapper.order;

import llustmarket.artmarket.domain.order.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    Order searchProductIdAndMemberId(Order vo);
}
