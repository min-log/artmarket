package llustmarket.artmarket.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @NotNull(message = "상품아이디는 필수입니다.")
    private Long productId;

    @NotNull(message = "멤버아이디는 필수입니다.")
    private Long memberId;

    @NotNull(message = "카테고리는 필수 선택입니다.")
    private String category;

    @NotBlank
    @Size(max = 30, message = "상품 제목은 최대 30자까지 입력 가능합니다.")
    private String productTitle;

    @NotBlank
    @Size(max = 1000, message = "상품 제목은 최대 1000자까지 입력 가능합니다.")
    private String productDetail;

    @NotNull
    private Date productDate = new Date();

    public Product(Long memberId, String category, String productTitle, String productDetail) {
        this.memberId = memberId;
        this.category = category;
        this.productTitle = productTitle;
        this.productDetail = productDetail;
    }
}
