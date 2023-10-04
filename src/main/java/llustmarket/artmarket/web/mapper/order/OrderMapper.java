package llustmarket.artmarket.web.mapper.order;

import llustmarket.artmarket.domain.order.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {
    Order searchProductIdAndMemberId(Order vo);

    @Select("SELECT COUNT(quantity) FROM `order` WHERE product_id = #{productId}")
    int countOrdersByProductId(Long productId);
}
