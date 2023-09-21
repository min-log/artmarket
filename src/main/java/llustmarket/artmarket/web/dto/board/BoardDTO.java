package llustmarket.artmarket.web.dto.board;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    private Long productId;

    @JsonProperty(value = "title")
    private String productTitle;

    @JsonProperty(value = "proNickname")
    private String nickname;

}
