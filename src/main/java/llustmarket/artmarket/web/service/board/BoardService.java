package llustmarket.artmarket.web.service.board;

import llustmarket.artmarket.domain.board.BoardRepository;
import llustmarket.artmarket.web.dto.board.AuthorDTO;
import llustmarket.artmarket.web.dto.board.BoardDTO;
import llustmarket.artmarket.web.dto.board.BoardFileDTO;
import llustmarket.artmarket.web.dto.board.ProductDetailDTO;
import llustmarket.artmarket.web.mapper.board.BoardMapper;
import llustmarket.artmarket.web.mapper.file.FileMapper;
import llustmarket.artmarket.web.mapper.product.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;

    final BoardRepository boardRepository;

    @Autowired
    ProductMapper productMapper;
    @Autowired
    FileMapper fileMapper;


    public List<BoardDTO> getCharacter() {
        return boardMapper.getCharacter();
    }

    public List<BoardFileDTO> getCharacterFile() {
        return boardMapper.getCharacterFile();
    }

    public List<BoardDTO> getIllust() {
        return boardMapper.getIllust();
    }

    public List<BoardDTO> getLive() {
        return boardMapper.getLive();
    }

    public List<BoardDTO> getDesign() {
        return boardMapper.getDesign();
    }

    public List<BoardDTO> getVideo() {
        return boardMapper.getVideo();
    }

    public List<BoardDTO> getPopular() {
        return boardMapper.getPopular();
    }

    public List<BoardDTO> getCurrent() {
        return boardMapper.getCurrent();
    }

    public List<BoardDTO> getSearch(String categoryKeyword) {
        return boardMapper.getSearch(categoryKeyword);
    }

    public AuthorDTO getAuthor(Long productId) {
        return boardMapper.getAuthor(productId);
    }

    public ProductDetailDTO getProductDetail(Long productId) {
        return boardMapper.getProductDetail(productId);
    }

    public List<BoardFileDTO> getProductFile(Long productId) {
        return boardMapper.getProductFile(productId);
    }
}
