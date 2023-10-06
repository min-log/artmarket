package llustmarket.artmarket.web.controller.product;

import llustmarket.artmarket.domain.file.FileType;
import llustmarket.artmarket.domain.file.FileVO;
import llustmarket.artmarket.domain.product.Product;
import llustmarket.artmarket.web.dto.file.FileDTO;
import llustmarket.artmarket.web.dto.product.Article;
import llustmarket.artmarket.web.dto.product.ProductDTO;
import llustmarket.artmarket.web.dto.product.ProductRegisterDTO;
import llustmarket.artmarket.web.dto.product.ProductUpdateDTO;
import llustmarket.artmarket.web.mapper.file.FileMapper;
import llustmarket.artmarket.web.mapper.order.OrderMapper;
import llustmarket.artmarket.web.service.file.FileService;
import llustmarket.artmarket.web.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final FileService fileService;
    private final FileMapper fileMapper;
    private final OrderMapper orderMapper;

    @GetMapping("/mypage-articles/{member_id}")
    public ResponseEntity<Object> getArticles(@PathVariable("member_id") Long memberId) {
        try {
            List<Product> memberProducts = productService.findProductByMemberId(memberId);
            List<Article> articles = new ArrayList<>();

            for (Product memberProduct : memberProducts) {
                List<FileVO> productFiles = fileMapper.getFilesByTypeAndId("PRODUCT", memberProduct.getProductId());
                List<String> filePaths = new ArrayList<>();
                List<String> fileNames = new ArrayList<>();

                for (FileVO file : productFiles) {
                    fileNames.add(file.getFileName());
                    filePaths.add("C:" + File.separator + "upload" + File.separator + file.getFilePath() + File.separator + file.getFileName());
                }

                List<byte[]> imageDataList = new ArrayList<>();
                for (String filePath : filePaths) {
                    byte[] imageData = fileService.getAttachmentImage(filePath);
                    imageDataList.add(imageData);
                }

                Article article = new Article(
                        memberProduct.getProductId(),
                        memberProduct.getCategory(),
                        memberProduct.getProductDate(),
                        orderMapper.countOrdersByProductId(memberProduct.getProductId()),
                        fileNames,
                        imageDataList
                );
                articles.add(article);
            }

            return ResponseEntity.ok(articles);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/mypage-articles-in")
    public ResponseEntity<Object> productRegisterProcess(@ModelAttribute @Valid ProductRegisterDTO productRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Map<String, String>> productRegisterErrors = new ArrayList<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("productRegisterErrorParam", fieldName);
                errorMap.put("productRegisterErrorMsg", errorMessage);
                productRegisterErrors.add(errorMap);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productRegisterErrors);
        }
        if (productRequest.getId() != null && productRequest.getCategory() != null && productRequest.getArticleTitle() != null && productRequest.getArticleDetail() != null) {
            try {
                // 상품 파일 저장
                List<MultipartFile> articleFiles = productRequest.getArticleFile();
                if (articleFiles != null && !articleFiles.isEmpty() && articleFiles.size() <= 5) {
                    Product product = new Product(
                            Long.valueOf(productRequest.getId()),
                            productRequest.getCategory(),
                            productRequest.getArticleTitle(),
                            productRequest.getArticleDetail()
                    );
                    productService.registerProduct(product);
                    for (MultipartFile articleFile : articleFiles) {
                        // 파일 확장자 확인
                        String extension = fileService.getExtension(articleFile);
                        if (extension == null || !(extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg") || extension.equals("gif"))) {
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("허용된 확장자는 jpg, png, jpeg, gif 입니다.");
                        }
                        // 파일을 저장하고 데이터베이스에 등록
                        FileDTO uploadedFile = fileService.fileRegister(FileType.PRODUCT, articleFile);
                        // FileVO를 생성하여 데이터베이스에 저장
                        ProductDTO lastProduct = productService.selectLastByMemberId(Long.valueOf(productRequest.getId()));
                        FileVO fileVO = FileVO.builder()
                                .filePath(uploadedFile.getFilePath())
                                .fileTypeId(lastProduct.getProductId())
                                .fileOriginName(uploadedFile.getFileOriginName())
                                .fileName(uploadedFile.getFileName())
                                .fileDate(LocalDateTime.now())
                                .build();

                        fileMapper.insertOne(fileVO);
                    }


                    return ResponseEntity.status(HttpStatus.CREATED).build();
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미지는 최소 1개, 최대 5개까지 허용됩니다.");
                }
            } catch (Exception e) {
                List<Map<String, String>> productRegisterErrors = new ArrayList<>();
                String fieldName = String.valueOf(e.getCause());
                String errorMessage = e.getMessage();

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("productRegisterErrorParam", fieldName);
                errorMap.put("productRegisterErrorMsg", errorMessage);
                productRegisterErrors.add(errorMap);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productRegisterErrors);
            }
        }
        List<Map<String, String>> productErrors = new ArrayList<>();
        String fieldName = "상품 등록 정보 오류";
        String errorMessage = "상품 등록에 필요한 정보를 모두 입력해주세요.";
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("productErrorParam", fieldName);
        errorMap.put("productErrorMsg", errorMessage);
        productErrors.add(errorMap);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productErrors);
    }

    @PatchMapping("/mypage-articles-in")
    public ResponseEntity<Object> productUpdateProcess(@ModelAttribute @Valid ProductUpdateDTO productRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Map<String, String>> productUpdateErrors = new ArrayList<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("productUpdateErrorParam", fieldName);
                errorMap.put("productUpdateErrorMsg", errorMessage);
                productUpdateErrors.add(errorMap);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productUpdateErrors);
        }
        if (productRequest.getArticleModProductId() != null && productRequest.getArticleModCategory() != null &&
                productRequest.getArticleModTitle() != null && productRequest.getArticleModDetail() != null && productRequest.getArticleModImgs() != null) {
            try {
                Product product = productService.findProductByProductId(Long.valueOf(productRequest.getArticleModProductId()));
                if (product != null) {
                    //상품 수정
                    productService.modifyProduct(
                            Long.valueOf(productRequest.getArticleModProductId()),
                            productRequest.getArticleModCategory(),
                            productRequest.getArticleModTitle(),
                            productRequest.getArticleModDetail()
                    );
                    //기존 파일 삭제
                    List<FileVO> filesToDelete = fileMapper.getFilesByTypeAndId("PRODUCT", Long.valueOf(productRequest.getArticleModProductId()));
                    for (FileVO fileToDelete : filesToDelete) {
                        fileService.fileRemove(fileToDelete.getFilePath(), fileToDelete.getFileName());
                    }
                    FileVO fileToDelete = FileVO.builder()
                            .filePath("PRODUCT")
                            .fileTypeId(Long.parseLong(productRequest.getArticleModProductId()))
                            .build();
                    fileMapper.deleteFile(fileToDelete);
                    //파일 새로 추가
                    List<MultipartFile> articleModFiles = productRequest.getArticleModImgs();
                    if (articleModFiles != null && !articleModFiles.isEmpty() && articleModFiles.size() <= 5) {
                        for (MultipartFile articleModFile : articleModFiles) {
                            // 파일 확장자 확인
                            String extension = fileService.getExtension(articleModFile);
                            if (extension == null || !(extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg") || extension.equals("gif"))) {
                                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("허용된 확장자는 jpg, png, jpeg, gif 입니다.");
                            }
                            // 파일을 저장하고 데이터베이스에 등록
                            FileDTO uploadedFile = fileService.fileRegister(FileType.PRODUCT, articleModFile);
                            // FileVO를 생성하여 데이터베이스에 저장
                            FileVO fileVO = FileVO.builder()
                                    .filePath(uploadedFile.getFilePath())
                                    .fileTypeId(Long.parseLong(productRequest.getArticleModProductId()))
                                    .fileOriginName(uploadedFile.getFileOriginName())
                                    .fileName(uploadedFile.getFileName())
                                    .fileDate(LocalDateTime.now())
                                    .build();

                            fileMapper.insertOne(fileVO);
                        }
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미지는 최소 1개, 최대 5개까지 허용됩니다.");
                    }
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                } else {
                    List<Map<String, String>> productModErrors = new ArrayList<>();
                    String fieldName = "product_id 오류";
                    String errorMessage = "DB에 해당 product_id가 없습니다.";
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("productModErrorParam", fieldName);
                    errorMap.put("productModErrorMsg", errorMessage);
                    productModErrors.add(errorMap);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productModErrors);
                }
            } catch (Exception e) {
                List<Map<String, String>> productRegisterErrors = new ArrayList<>();
                String fieldName = String.valueOf(e.getCause());
                String errorMessage = e.getMessage();

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("productRegisterErrorParam", fieldName);
                errorMap.put("productRegisterErrorMsg", errorMessage);
                productRegisterErrors.add(errorMap);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productRegisterErrors);
            }
        }
        List<Map<String, String>> productErrors = new ArrayList<>();
        String fieldName = "상품 수정 정보 오류";
        String errorMessage = "상품 수정에 필요한 정보를 모두 입력해주세요.";
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("productErrorParam", fieldName);
        errorMap.put("productErrorMsg", errorMessage);
        productErrors.add(errorMap);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productErrors);
    }

    @DeleteMapping("/mypage-articles/{product_id}")
    public ResponseEntity<Object> deleteProductById(@PathVariable("product_id") Long productId) {
        try {
            if (productService.findProductByProductId(productId) != null) {
                List<FileVO> filesToDelete = fileMapper.getFilesByTypeAndId("PRODUCT", productId);
                for (FileVO fileToDelte : filesToDelete) {
                    fileService.fileRemove("PRODUCT", fileToDelte.getFileName());
                    fileMapper.deleteFile(fileToDelte);
                }
                productService.deleteProductById(productId);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                List<Map<String, String>> productDeleteErrors = new ArrayList<>();
                String fieldName = "product_id 오류";
                String errorMessage = "product_id에 해당하는 product가 없습니다";

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("productDeleteErrorParam", fieldName);
                errorMap.put("productDeleteErrorMsg", errorMessage);
                productDeleteErrors.add(errorMap);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productDeleteErrors);
            }
        } catch (NumberFormatException e) {
            List<Map<String, String>> productDeleteErrors = new ArrayList<>();
            String fieldName = "product_id 오류";
            String errorMessage = "유효한 숫자 형식이 아닙니다";

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("productDeleteErrorParam", fieldName);
            errorMap.put("productDeleteErrorMsg", errorMessage);
            productDeleteErrors.add(errorMap);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productDeleteErrors);
        } catch (Exception e) {
            List<Map<String, String>> productDeleteErrors = new ArrayList<>();
            String fieldName = String.valueOf(e.getCause());
            String errorMessage = e.getMessage();

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("productDeleteErrorParam", fieldName);
            errorMap.put("productDeleteErrorMsg", errorMessage);
            productDeleteErrors.add(errorMap);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productDeleteErrors);
        }
    }
}
