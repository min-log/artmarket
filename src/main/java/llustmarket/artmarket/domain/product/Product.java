package llustmarket.artmarket.domain.product;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Product {
    private long productId;
    private long memberId;
    private String productTitle;
    private String productDetail;
    private LocalDateTime productDate;
}
