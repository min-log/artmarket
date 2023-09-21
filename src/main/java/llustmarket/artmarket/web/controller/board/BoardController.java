package llustmarket.artmarket.web.controller.board;

import llustmarket.artmarket.web.dto.board.BoardDTO;
import llustmarket.artmarket.web.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping("/list/character")
    public ResponseEntity<List<BoardDTO>> getCharacter() {
        List<BoardDTO> boards = boardService.getCharacter();
        return ResponseEntity.status(HttpStatus.OK).body(boards);
    }

    @GetMapping("/list/illust")
    public ResponseEntity<List<BoardDTO>> getIllust() {
        List<BoardDTO> boards = boardService.getIllust();
        return ResponseEntity.status(HttpStatus.OK).body(boards);
    }

    @GetMapping("/list/live")
    public ResponseEntity<List<BoardDTO>> getLive() {
        List<BoardDTO> boards = boardService.getLive();
        return ResponseEntity.status(HttpStatus.OK).body(boards);
    }

    @GetMapping("/list/design")
    public ResponseEntity<List<BoardDTO>> getDesign() {
        List<BoardDTO> boards = boardService.getDesign();
        return ResponseEntity.status(HttpStatus.OK).body(boards);
    }

    @GetMapping("/list/video")
    public ResponseEntity<List<BoardDTO>> getVideo() {
        List<BoardDTO> boards = boardService.getVideo();
        return ResponseEntity.status(HttpStatus.OK).body(boards);
    }


}
