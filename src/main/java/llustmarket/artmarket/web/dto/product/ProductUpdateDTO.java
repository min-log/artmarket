package llustmarket.artmarket.web.dto.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class ProductUpdateDTO {

    @NotNull
    private Long articleModProductId;

    @NotNull
    private String articleModCategory;

    @NotBlank
    @Size(max = 30, message = "상품 제목은 최대 30자까지 입력 가능합니다.")
    private String articleModTitle;

    @NotBlank
    @Size(max = 1000, message = "상품 제목은 최대 1000자까지 입력 가능합니다.")
    private String articleModDetail;

}
