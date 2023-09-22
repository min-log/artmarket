package llustmarket.artmarket.web.dto.order;


import lombok.*;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderDTO {
    private String orderId;
    private long productId;
    private long memberId;
    private String productName;
    private LocalDateTime orderDate;
    private long totalAmount;
    private int quantity;
    private LocalDateTime deadline;
    private String orderStatus;
    
}
