package llustmarket.artmarket.domain.alert;


import lombok.*;

import java.time.LocalDateTime;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Alert {
    private long alertId;
    private long memberId;
    private String alertWriter;
    private String alertIdentity;
    private String alertType;
    private long alertPath;
    private boolean alertStatus;
    private int alertCount;
    private LocalDateTime alertDate;
}
