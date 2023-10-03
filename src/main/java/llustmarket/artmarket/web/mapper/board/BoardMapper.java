package llustmarket.artmarket.web.mapper.board;

import llustmarket.artmarket.web.dto.board.AuthorDTO;
import llustmarket.artmarket.web.dto.board.BoardDTO;
import llustmarket.artmarket.web.dto.board.BoardFileDTO;
import llustmarket.artmarket.web.dto.board.ProductDetailDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface BoardMapper {
    List<BoardDTO> getCharacter();

    List<BoardFileDTO> getCharacterFile();

    List<BoardDTO> getIllust();

    List<BoardDTO> getLive();

    List<BoardDTO> getDesign();

    List<BoardDTO> getVideo();

    List<BoardDTO> getPopular();

    List<BoardDTO> getCurrent();

    List<BoardDTO> getSearch(String categoryKeyword);

    AuthorDTO getAuthor(Long productId);

    ProductDetailDTO getProductDetail(Long productId);

    List<BoardFileDTO> getProductFile(Long productId);

}
