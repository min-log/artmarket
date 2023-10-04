package llustmarket.artmarket.web.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class Article {
    private Long articleId;
    private String articleCategory;
    private Date articleDate;
    private int articleTotalOrder;
    private List<String> articleImgs;
}
