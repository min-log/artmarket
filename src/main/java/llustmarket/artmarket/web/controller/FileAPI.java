package llustmarket.artmarket.web.controller;


import llustmarket.artmarket.web.dto.chat.ChatRoomResponseDTO;
import llustmarket.artmarket.web.dto.file.FileDTO;
import llustmarket.artmarket.web.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
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
        // 저는 파일 저장 부분
        log.info("file : {}",file);
    }


    @GetMapping("/find")
    public ResponseEntity<?> fileImgGet(@RequestParam(value = "filePath") String filePath,
                                             @RequestParam(value = "fileTypeId") long fileTypeId) throws IOException, IOException {
        log.info("# 이미지 파일 불러오기");
        ResponseEntity<byte[]> result = null;

        FileDTO fileDTO = fileService.fileFindOne(filePath, fileTypeId);
        if(fileDTO == null) {
            String errorMessage = "{\"errorMessage\": \"파일이 존재하지 않거나, 올바른 파일 경로가 아닙니다.\"}";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
        }
        String fileName = fileDTO.getFileName();
        File file = new File(uploadPath + File.separator  + filePath + File.separator + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type" , Files.probeContentType(file.toPath()));
        // 파일 데이터처리
        result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
        return result;
    }


    @GetMapping("/download")
    public ResponseEntity<?> fileDownload(@RequestParam("fileUpName") String fileUpName,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("# 클라이언트 파일 저장");

        FileDTO fileDTO = fileService.fileDownload(fileUpName);
        if(fileDTO == null) {
            String errorMessage = "{\"errorMessage\": \"파일이 존재하지 않습니다.\"}";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
        }

        File file = new File(uploadPath + File.separator  + fileDTO.getFilePath() + File.separator + fileUpName);
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

        //User-Agent : 어떤 운영체제로  어떤 브라우저를 서버( 홈페이지 )에 접근하는지 확인함
        String header = request.getHeader("User-Agent");
        String fileName = fileDTO.getFileOriginName();

        if ((header.contains("MSIE")) || (header.contains("Trident")) || (header.contains("Edge"))) {
            //인터넷 익스플로러 10이하 버전, 11버전, 엣지에서 인코딩
            fileName = URLEncoder.encode(fileDTO.getFileOriginName(), "UTF-8");
        } else {
            //나머지 브라우저에서 인코딩
            fileName = new String(fileDTO.getFileOriginName().getBytes("UTF-8"), "iso-8859-1");
        }
        //형식을 모르는 파일첨부용 contentType
        response.setContentType("application/octet-stream");
        //다운로드와 다운로드될 파일이름
        response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\"");
        //파일복사
        FileCopyUtils.copy(in, response.getOutputStream());
        in.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();
        String result = "{\"successMessage\": \"파일 저장이 성공했습니다.\"}";
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }



}
