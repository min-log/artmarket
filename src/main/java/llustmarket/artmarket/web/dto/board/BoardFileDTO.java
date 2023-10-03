package llustmarket.artmarket.web.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardFileDTO {

    private String filePath;

    private String fileName;

    private String productDetailImgs;


}
