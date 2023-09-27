
package llustmarket.artmarket.web.dto.alert;

import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class AlramDTO {
        private String alramSender;
        private String alramType;
        private String alertDate;


}

