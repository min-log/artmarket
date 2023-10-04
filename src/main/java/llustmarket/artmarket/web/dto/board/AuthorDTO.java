package llustmarket.artmarket.web.dto.board;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDTO {

    private String authorPofile;

    @JsonProperty(value = "authorNickname")
    private String nickname;

}
