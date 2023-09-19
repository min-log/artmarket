package llustmarket.artmarket.web.service.product;

import llustmarket.artmarket.domain.product.Product;
import llustmarket.artmarket.web.mapper.product.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;

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
}