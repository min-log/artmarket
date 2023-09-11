package llustmarket.artmarket.web.dto.product;


import lombok.*;

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
}
