package llustmarket.artmarket.web.dto.board;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    private Long productId;

    @JsonProperty(value = "title")
    private String productTitle;

    @JsonProperty(value = "proNickname")
    private String nickname;

    @JsonProperty(value = "productImgs")
    private List fileList;
    
}
