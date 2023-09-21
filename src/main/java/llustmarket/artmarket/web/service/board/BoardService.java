package llustmarket.artmarket.web.service.board;

import llustmarket.artmarket.web.dto.board.BoardDTO;
import llustmarket.artmarket.web.mapper.board.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;

    public List<BoardDTO> getCharacter() {
        return boardMapper.getCharacter();
    }


}
