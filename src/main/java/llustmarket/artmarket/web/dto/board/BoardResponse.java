package llustmarket.artmarket.web.dto.board;

import llustmarket.artmarket.domain.board.Board;
import lombok.Getter;

@Getter
public class BoardResponse {
    private String product_title;

    public BoardResponse(Board board){
        this.product_title = board.getProduct_title();
    }
}
