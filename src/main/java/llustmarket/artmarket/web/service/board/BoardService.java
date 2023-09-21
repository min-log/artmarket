package llustmarket.artmarket.web.service.board;

import llustmarket.artmarket.domain.board.BoardRepository;
import llustmarket.artmarket.web.dto.board.BoardDTO;
import llustmarket.artmarket.web.mapper.board.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    ModelMapper modelMapper;
    final BoardRepository repository;

    public List<BoardDTO> getCharacter() {
        return boardMapper.getCharacter();
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


}
