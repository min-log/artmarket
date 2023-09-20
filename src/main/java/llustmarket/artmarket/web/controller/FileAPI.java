package llustmarket.artmarket.web.controller;


import llustmarket.artmarket.web.dto.file.FileDTO;
import llustmarket.artmarket.web.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/file")
public class FileAPI {
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    private final FileService fileService;
    @PostMapping("/register")
    public void fileRegister(MultipartFile file){
        log.info("파일 저장");
        log.info("file : {}",file);
    }


    @GetMapping("/find")
    public ResponseEntity<byte[]> fileImgGet(@RequestParam(value = "filePath") String filePath,
                                             @RequestParam(value = "fileTypeId") long fileTypeId) throws IOException, IOException {
        log.info("파일 불러오기");
        ResponseEntity<byte[]> result = null;
        FileDTO fileDTO = fileService.fileFindOne(filePath, fileTypeId);
        String fileName = fileDTO.getFileName();

        File file = new File(uploadPath + File.separator  + filePath + File.separator + fileName);
        HttpHeaders headers = new HttpHeaders();
        log.info("file --------------------------------");
        headers.add("Content-Type" , Files.probeContentType(file.toPath()));
        // 파일 데이터처리
        result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
        return result;
    }


    @GetMapping("/download")
    public ResponseEntity<?> downloadImage(@PathVariable("fileName") String fileName) {

    }



}
