package llustmarket.artmarket.web.controller.board;

import llustmarket.artmarket.web.dto.board.BoardDTO;
import llustmarket.artmarket.web.dto.board.BoardFileDTO;
import llustmarket.artmarket.web.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping("/list/{category}")
    public ResponseEntity<Object> getCharacter(@PathVariable(value = "category") String category) {

        List<BoardDTO> boards = boardService.getCategoryList(category);
        List<Map<String, Object>> files = boardService.getCategoryFile(category);

        BoardDTO boardDTO = BoardDTO.builder().build();
        BoardFileDTO boardFileDTO = BoardFileDTO.builder().build();
        List pfiles = new ArrayList<>();

        for (int i = 0; i < files.size(); i++) {
            boardFileDTO.setProductDetailImgs("/" + files.get(i).get("filePath").toString() + "/" + files.get(i).get("fileName").toString());
            pfiles.add(boardFileDTO.getProductDetailImgs());
        }
        boardDTO.setFileList(pfiles);

        Map<Object, Object> products = new LinkedHashMap<>();
        products.put("products", boards);
        products.put("productImgs", pfiles);

        return ResponseEntity.status(HttpStatus.OK).body(products);
    }


    @GetMapping("/array/popular")
    public ResponseEntity<List<BoardDTO>> getPopular() {
        List<BoardDTO> boards = boardService.getPopular();
        return ResponseEntity.status(HttpStatus.OK).body(boards);
    }

    @GetMapping("/array/current")
    public ResponseEntity<List<BoardDTO>> getCurrent() {
        List<BoardDTO> boards = boardService.getCurrent();
        return ResponseEntity.status(HttpStatus.OK).body(boards);
    }

    @GetMapping("/keyword/{categoryKeyword}")
    public ResponseEntity<List<BoardDTO>> getSearch(@PathVariable(value = "categoryKeyword") String categoryKeyword) {
        List<BoardDTO> boards = boardService.getSearch(categoryKeyword);
        return ResponseEntity.status(HttpStatus.OK).body(boards);
    }
}
