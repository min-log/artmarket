package llustmarket.artmarket.web.controller.board;

import llustmarket.artmarket.domain.file.FileType;
import llustmarket.artmarket.domain.file.FileVO;
import llustmarket.artmarket.domain.product.Product;
import llustmarket.artmarket.web.dto.board.AuthorDTO;
import llustmarket.artmarket.web.dto.board.BoardFileDTO;
import llustmarket.artmarket.web.dto.board.ProductDetailDTO;
import llustmarket.artmarket.web.mapper.file.FileMapper;
import llustmarket.artmarket.web.mapper.product.ProductMapper;
import llustmarket.artmarket.web.service.board.BoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/product")
public class ProductDetailController {

    @Autowired
    BoardService boardService;

    @Autowired
    ProductMapper productMapper;
    @Autowired
    FileMapper fileMapper;


    @GetMapping("/{productId}")
    public ResponseEntity<Object> getProdustDetail(@PathVariable(value = "productId") Long productId, BoardFileDTO dto) {

        AuthorDTO authors = boardService.getAuthor(productId);
        Product product = productMapper.selectOneByProductId(productId);
        ProductDetailDTO details = boardService.getProductDetail(productId);
        List<BoardFileDTO> files = boardService.getProductFile(productId);

        FileVO memberProfile = fileMapper.selectOnePathAndId(FileVO.builder().filePath(String.valueOf(FileType.PROFILE)).fileTypeId(product.getMemberId()).build());
        if (memberProfile != null) {
            authors.setAuthorPofile("/" + memberProfile.getFilePath() + "/" + memberProfile.getFileName());
        }

//        FileVO productFile = fileMapper.selectOnePathAndId(FileVO.builder().filePath(String.valueOf(FileType.PRODUCT)).fileTypeId(product.getMemberId()).build());
//        BoardFileDTO boardFile = BoardFileDTO.builder().build();
//        boardFile.setProductDetailImgs("/" + productFile.getFilePath() + "/" + productFile.getFileName());

        Map<Object, Object> result = new LinkedHashMap<>();
        result.put("author", authors);
        result.put("productDetail", details);
        result.put("productDetailImgs", files);


        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
