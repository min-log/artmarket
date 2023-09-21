package llustmarket.artmarket.web.dto.board;

import llustmarket.artmarket.domain.board.Board;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardDTO {

    private Long productId;
    private String title;

    public BoardDTO(Board board) {
        this.productId = board.getProductId();
        this.title = board.getProductTitle();
    }
}
