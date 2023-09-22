package llustmarket.artmarket.domain.board;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "product")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private Long memberId;

    @Column(length = 10, nullable = false)
    private String category;
    @Column(length = 30, nullable = false)
    private String productTitle;

    @Column(nullable = false)
    private String productDetail;
    @Column(nullable = false)
    private LocalDateTime productDate;


    public Board(Long memberId, String category, String productTitle, LocalDateTime productDate, String productDetail) {
        this.memberId = memberId;
        this.category = category;
        this.productTitle = productTitle;
        this.productDate = productDate;
        this.productDetail = productDetail;
    }

}
