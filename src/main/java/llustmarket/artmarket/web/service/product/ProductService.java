package llustmarket.artmarket.web.service.product;

import llustmarket.artmarket.domain.member.Member;
import llustmarket.artmarket.domain.product.Product;
import llustmarket.artmarket.web.dto.member.MemberDTO;
import llustmarket.artmarket.web.dto.product.ProductDTO;
import llustmarket.artmarket.web.mapper.product.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductMapper productMapper;
    private final ModelMapper mapper;

    public ProductDTO selectOne(long productId){
        Product product = productMapper.selectOneByProductId(productId);
        return mapper.map(product, ProductDTO.class);
    }
}
