package llustmarket.artmarket.web.dto.product;


import lombok.*;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductDTO {
    private long productId;
    private long memberId;
    private String productTitle;
    private String productDetail;
    private LocalDateTime productDate;
}
