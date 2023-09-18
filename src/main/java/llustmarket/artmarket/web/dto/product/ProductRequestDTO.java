package llustmarket.artmarket.web.dto.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class ProductRequestDTO {

    private Long id;

    private String category;

    @Size(max = 30, message = "상품 제목은 최대 30자까지 입력 가능합니다.")
    private String articleTitle;

    @Size(max = 1000, message = "상품 제목은 최대 1000자까지 입력 가능합니다.")
    private String articleDetail;

    private Long articleModProductId;

    private String articleModCategory;

    @Size(max = 30, message = "상품 제목은 최대 30자까지 입력 가능합니다.")
    private String articleModTitle;

    @Size(max = 1000, message = "상품 제목은 최대 1000자까지 입력 가능합니다.")
    private String articleModDetail;

}
