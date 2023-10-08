package llustmarket.artmarket.web.dto.board;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailDTO {

    @JsonProperty(value = "title")
    private String productTitle;
    private String category;

    private String productDetail;

    private List productDetailImgs;

}
