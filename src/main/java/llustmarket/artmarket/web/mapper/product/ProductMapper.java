package llustmarket.artmarket.web.mapper.product;

import llustmarket.artmarket.domain.product.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductMapper {
    void insertProduct(Product product);

    void updateProduct(
            @Param("productId") Long productId,
            @Param("category") String category,
            @Param("productTitle") String productTitle,
            @Param("productDetail") String productDetail
    );

    Product findProductByProductId(Long productId);

    void deleteProductById(Long productId);
}