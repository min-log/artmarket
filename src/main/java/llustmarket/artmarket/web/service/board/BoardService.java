package llustmarket.artmarket.web.service.board;

import llustmarket.artmarket.web.dto.board.AuthorDTO;
import llustmarket.artmarket.web.dto.board.BoardDTO;
import llustmarket.artmarket.web.dto.board.ProductDetailDTO;
import llustmarket.artmarket.web.mapper.board.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;

    public List<BoardDTO> getCategoryList(String category) {
        return boardMapper.getCategoryList(category);
    }

    public List<Map<String, Object>> getCategoryFile(String category, Long productId) {
        return boardMapper.getCategoryFile(category, productId);
    }

    public List<Map<String, Object>> getArrayFile(Long productId) {
        return boardMapper.getArrayFile(productId);
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

    public List<Map<String, Object>> getProductFile(Long productId) {
        return boardMapper.getProductFile(productId);
    }
}
