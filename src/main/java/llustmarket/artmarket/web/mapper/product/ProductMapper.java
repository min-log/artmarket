package llustmarket.artmarket.web.mapper.product;

import llustmarket.artmarket.domain.product.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {
    Product selectOneByProductId(long productId);
}
