package llustmarket.artmarket.web.dto.alert;


import lombok.*;

import java.time.LocalDateTime;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class AlertDTO {
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
