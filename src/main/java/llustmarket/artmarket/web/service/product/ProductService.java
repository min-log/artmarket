package llustmarket.artmarket.web.service.product;

import llustmarket.artmarket.domain.product.Product;
import llustmarket.artmarket.web.dto.product.ProductDTO;
import llustmarket.artmarket.web.mapper.product.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductMapper productMapper;
    private final ModelMapper mapper;


    @Transactional
    public void registerProduct(Product product) {
        productMapper.insertProduct(product);
    }

    @Transactional
    public void modifyProduct(Long productId, String category, String productTitle, String productDetail) {
        productMapper.updateProduct(productId, category, productTitle, productDetail);
    }

    public Product findProductByProductId(Long productId) {
        return productMapper.findProductByProductId(productId);
    }

    @Transactional
    public void deleteProductById(Long productId) {
        productMapper.deleteProductById(productId);
    }

    public ProductDTO selectOne(long productId) {
        Product product = productMapper.selectOneByProductId(productId);
        return mapper.map(product, ProductDTO.class);
    }

    public ProductDTO selectLastByMemberId(Long memberId) {
        Product product = productMapper.selectLastByMemberId(memberId);
        return mapper.map(product, ProductDTO.class);
    }
}