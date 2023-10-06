package llustmarket.artmarket.domain.order;


import lombok.*;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Order {
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
