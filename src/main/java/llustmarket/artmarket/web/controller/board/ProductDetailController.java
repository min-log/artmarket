package llustmarket.artmarket.web.controller.board;

import llustmarket.artmarket.domain.file.FileType;
import llustmarket.artmarket.domain.file.FileVO;
import llustmarket.artmarket.domain.product.Product;
import llustmarket.artmarket.web.dto.board.AuthorDTO;
import llustmarket.artmarket.web.dto.board.ProductDetailDTO;
import llustmarket.artmarket.web.mapper.file.FileMapper;
import llustmarket.artmarket.web.mapper.product.ProductMapper;
import llustmarket.artmarket.web.service.board.BoardService;
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
@RequestMapping("/product")
public class ProductDetailController {

    @Autowired
    BoardService boardService;

    @Autowired
    ProductMapper productMapper;
    @Autowired
    FileMapper fileMapper;

    public byte[] getAttachmentImage(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @GetMapping("/{productId}")
    public ResponseEntity<Object> getProdustDetail(@PathVariable(value = "productId") Long productId) {
        try {
            AuthorDTO authors = boardService.getAuthor(productId);
            Product product = productMapper.selectOneByProductId(productId);
            ProductDetailDTO details = boardService.getProductDetail(productId);
            List<Map<String, Object>> files = boardService.getProductFile(productId);


            List<byte[]> profileDataList = new ArrayList<>();

            FileVO memberProfile = fileMapper.selectOnePathAndId(FileVO.builder().filePath(String.valueOf(FileType.PROFILE)).fileTypeId(product.getMemberId()).build());
            if (memberProfile != null) {
                String profileFile = ("\\upload\\" + memberProfile.getFilePath() + "\\" + memberProfile.getFileName());
                byte[] profileImage = getAttachmentImage(profileFile);
                profileDataList.add(profileImage);
            }


            List<String> productFiles = new ArrayList<>();

            for (int i = 0; i < files.size(); i++) {
                productFiles.add("\\upload\\" + files.get(i).get("filePath").toString() + "\\" + files.get(i).get("fileName").toString());
            }

            List<byte[]> imageDataList = new ArrayList<>();

            for (String filePath : productFiles) {
                byte[] image = getAttachmentImage(filePath);
                imageDataList.add(image);
            }


            ProductDetailDTO productDetailDTO = new ProductDetailDTO(
                    details.getProductTitle(),
                    details.getCategory(),
                    details.getProductDetail(),
                    imageDataList
            );

            AuthorDTO authorDTO = new AuthorDTO(
                    profileDataList,
                    authors.getNickname(),
                    authors.getMemberIntro()
            );

            Map<Object, Object> result = new LinkedHashMap<>();
            result.put("author", authorDTO);
            result.put("productDetail", productDetailDTO);


            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

}
