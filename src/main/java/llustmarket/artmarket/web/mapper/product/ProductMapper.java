package llustmarket.artmarket.web.mapper.product;

import llustmarket.artmarket.domain.product.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    Long insertProduct(Product product);

    void updateProduct(
            @Param("productId") Long productId,
            @Param("category") String category,
            @Param("productTitle") String productTitle,
            @Param("productDetail") String productDetail
    );

    Product findProductByProductId(Long productId);

    List<Product> findProductByMemberId(Long memberId);

    void deleteProductById(Long productId);

    Product selectOneByProductId(long productId);

    Product selectLastByMemberId(Long memberId);
}
