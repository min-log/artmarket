package llustmarket.artmarket.web.controller.product;

import llustmarket.artmarket.domain.product.Product;
import llustmarket.artmarket.web.dto.product.ProductRequestDTO;
import llustmarket.artmarket.web.service.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/mypage-articles-in")
    public ResponseEntity<?> productProcess(@RequestBody @Valid ProductRequestDTO productRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Map<String, String>> productRegisterErrors = new ArrayList<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("productRegisterErrorParam", fieldName);
                errorMap.put("productRegisterErrorMsg", errorMessage);
                productRegisterErrors.add(errorMap);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productRegisterErrors);
        }
        if (productRequest.getId() != null && productRequest.getCategory() != null && productRequest.getArticleTitle() != null && productRequest.getArticleDetail() != null) {
            try {
                Product product = new Product(
                        productRequest.getId(),
                        productRequest.getCategory(),
                        productRequest.getArticleTitle(),
                        productRequest.getArticleDetail()
                );
                productService.registerProduct(product);
                return ResponseEntity.status(HttpStatus.CREATED).build();

            } catch (Exception e) {
                List<Map<String, String>> productRegisterErrors = new ArrayList<>();
                String fieldName = String.valueOf(e.getCause());
                String errorMessage = e.getMessage();

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("productRegisterErrorParam", fieldName);
                errorMap.put("productRegisterErrorMsg", errorMessage);
                productRegisterErrors.add(errorMap);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productRegisterErrors);
            }
        } else if (productRequest.getArticleModProductId() != null && productRequest.getArticleModCategory() != null && productRequest.getArticleModTitle() != null && productRequest.getArticleModDetail() != null) {
            try {
                Product product = productService.findProductByProductId(productRequest.getArticleModProductId());
                if (product != null) {
                    productService.modifyProduct(
                            productRequest.getArticleModProductId(),
                            productRequest.getArticleModCategory(),
                            productRequest.getArticleModTitle(),
                            productRequest.getArticleModDetail()
                    );
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                } else {
                    List<Map<String, String>> productModErrors = new ArrayList<>();
                    String fieldName = "product_id 오류";
                    String errorMessage = "DB에 해당 product_id가 없습니다.";
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("productModErrorParam", fieldName);
                    errorMap.put("productModErrorMsg", errorMessage);
                    productModErrors.add(errorMap);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productModErrors);
                }
            } catch (Exception e) {
                log.error("Product 수정 실패: {}", e.getMessage());
                List<Map<String, String>> productModifyErrors = new ArrayList<>();
                String fieldName = String.valueOf(e.getCause());
                String errorMessage = e.getMessage();

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("productModifyErrorParam", fieldName);
                errorMap.put("productModifyErrorMsg", errorMessage);
                productModifyErrors.add(errorMap);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productModifyErrors);
            }
        }
        List<Map<String, String>> productErrors = new ArrayList<>();
        String fieldName = "정보 오류";
        String errorMessage = "정보를 모두 입력해주세요.";
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("productErrorParam", fieldName);
        errorMap.put("productErrorMsg", errorMessage);
        productErrors.add(errorMap);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productErrors);
    }
}