package llustmarket.artmarket.domain.board;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", updatable = false)
    private Long product_id;

    @Column(name = "product_title", nullable = false)
    private String product_title;


    @Builder
    public Board(String product_title){
        this.product_title = product_title;
    }

}
