package llustmarket.artmarket.web.dto.board;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BoardDTO {

    private Long productId;

    @JsonProperty(value = "title")
    private String productTitle;

    @JsonProperty(value = "proNickname")
    private String nickname;

}
