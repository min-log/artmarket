package llustmarket.artmarket.web.controller.board;

import llustmarket.artmarket.web.dto.board.BoardDTO;
import llustmarket.artmarket.web.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public byte[] getAttachmentImage(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/list/{category}")
    public ResponseEntity<Object> getCharacter(@PathVariable(value = "category") String category) {
        try {
            List<BoardDTO> boards = boardService.getCategoryList(category);
            List<BoardDTO> boardList = new ArrayList<>();

            for (BoardDTO board : boards) {
                List<Map<String, Object>> files = boardService.getCategoryFile(category, board.getProductId());
                List<String> fileList = new ArrayList<>();

                for (int i = 0; i < files.size(); i++) {
                    fileList.add("\\upload\\" + files.get(i).get("filePath").toString() + "\\" + files.get(i).get("fileName").toString());
                }

                List<byte[]> imageDataList = new ArrayList<>();

                for (String filePath : fileList) {
                    byte[] image = getAttachmentImage(filePath);
                    imageDataList.add(image);
                }

                BoardDTO boardDTO = new BoardDTO(
                        board.getProductId(),
                        board.getProductTitle(),
                        board.getNickname(),
                        imageDataList
                );
                boardList.add(boardDTO);
            }

            Map<Object, Object> products = new LinkedHashMap<>();
            products.put("products", boardList);


            return ResponseEntity.status(HttpStatus.OK).body(products);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @GetMapping("/array/popular")
    public ResponseEntity<Object> getPopular() {
        try {
            List<BoardDTO> boards = boardService.getPopular();
            List<BoardDTO> boardList = new ArrayList<>();

            for (BoardDTO board : boards) {
                List<Map<String, Object>> files = boardService.getArrayFile(board.getProductId());
                List<String> fileList = new ArrayList<>();

                for (int i = 0; i < files.size(); i++) {
                    fileList.add("\\upload\\" + files.get(i).get("filePath").toString() + "\\" + files.get(i).get("fileName").toString());
                }

                List<byte[]> imageDataList = new ArrayList<>();

                for (String filePath : fileList) {
                    byte[] image = getAttachmentImage(filePath);
                    imageDataList.add(image);
                }

                BoardDTO boardDTO = new BoardDTO(
                        board.getProductId(),
                        board.getProductTitle(),
                        board.getNickname(),
                        imageDataList
                );

                boardList.add(boardDTO);
            }

            Map<Object, Object> products = new LinkedHashMap<>();
            products.put("products", boardList);


            return ResponseEntity.status(HttpStatus.OK).body(products);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/array/current")
    public ResponseEntity<Object> getCurrent() {
        try {
            List<BoardDTO> boards = boardService.getCurrent();
            List<BoardDTO> boardList = new ArrayList<>();

            for (BoardDTO board : boards) {
                List<Map<String, Object>> files = boardService.getArrayFile(board.getProductId());
                List<String> fileList = new ArrayList<>();

                for (int i = 0; i < files.size(); i++) {
                    fileList.add("\\upload\\" + files.get(i).get("filePath").toString() + "\\" + files.get(i).get("fileName").toString());
                }

                List<byte[]> imageDataList = new ArrayList<>();

                for (String filePath : fileList) {
                    byte[] image = getAttachmentImage(filePath);
                    imageDataList.add(image);
                }

                BoardDTO boardDTO = new BoardDTO(
                        board.getProductId(),
                        board.getProductTitle(),
                        board.getNickname(),
                        imageDataList
                );
                boardList.add(boardDTO);
            }

            Map<Object, Object> products = new LinkedHashMap<>();
            products.put("products", boardList);


            return ResponseEntity.status(HttpStatus.OK).body(products);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/keyword/{categoryKeyword}")
    public ResponseEntity<Object> getSearch(@PathVariable(value = "categoryKeyword") String categoryKeyword) {
        try {
            List<BoardDTO> boards = boardService.getSearch(categoryKeyword);
            List<BoardDTO> boardList = new ArrayList<>();

            for (BoardDTO board : boards) {
                List<Map<String, Object>> files = boardService.getArrayFile(board.getProductId());
                List<String> fileList = new ArrayList<>();

                for (int i = 0; i < files.size(); i++) {
                    fileList.add("\\upload\\" + files.get(i).get("filePath").toString() + "\\" + files.get(i).get("fileName").toString());
                }

                List<byte[]> imageDataList = new ArrayList<>();

                for (String filePath : fileList) {
                    byte[] image = getAttachmentImage(filePath);
                    imageDataList.add(image);
                }

                BoardDTO boardDTO = new BoardDTO(
                        board.getProductId(),
                        board.getProductTitle(),
                        board.getNickname(),
                        imageDataList
                );
                boardList.add(boardDTO);
            }

            Map<Object, Object> products = new LinkedHashMap<>();
            products.put("products", boardList);


            return ResponseEntity.status(HttpStatus.OK).body(products);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
