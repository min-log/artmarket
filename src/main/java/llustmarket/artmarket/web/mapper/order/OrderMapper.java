package llustmarket.artmarket.web.mapper.order;

import llustmarket.artmarket.domain.order.Order;
import llustmarket.artmarket.web.dto.board.AuthorDTO;
import llustmarket.artmarket.web.dto.member.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {
    Order searchProductIdAndMemberId(Order vo);

    @Select("SELECT COUNT(quantity) FROM `order` WHERE product_id = #{productId}")
    int countOrdersByProductId(Long productId);

    Order searchOneDeadline(Order vo);

    String selectOrderId();

    void insertOrder(Order order);

    List<AuthorDTO> selectAuthor(String memberId);

    List<MemberDTO> selectMember(String memberId);

    //List<OrderDTO> getSearch();

    void updateOrderStatus(Order order);
}
