package llustmarket.artmarket.web.dto.board;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDTO {

    private List authorPofile;

    @JsonProperty(value = "authorNickname")
    private String nickname;

    @JsonProperty(value = "authorIntro")
    private String memberIntro;


}
