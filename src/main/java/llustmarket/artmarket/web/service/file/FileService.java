package llustmarket.artmarket.web.service.file;


import llustmarket.artmarket.domain.file.FileType;
import llustmarket.artmarket.domain.file.FileVO;
import llustmarket.artmarket.web.dto.file.ChatFileUploadDTO;
import llustmarket.artmarket.web.dto.file.FileDTO;
import llustmarket.artmarket.web.dto.file.FileMultipart;
import llustmarket.artmarket.web.mapper.file.FileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Log4j2
@RequiredArgsConstructor
@Service
public class FileService {
    // 파일관련 서비스
    private final FileMapper fileMapper;
    private final ModelMapper modelMapper;
    // 파일 저장 경로
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;


    //파일 저장로직
    public FileDTO fileRegister(FileType filePath, MultipartFile uploadFile) {
        // filePath 파일 저장 경로 (FileType CHAT,REVIEW,PROFILE,PRODUCT)
        String filePathName = String.valueOf(filePath);
        //1. 파일 경로 폴더 생성 -- 폴더가 존재하지 않을 시 생성됨
        String folderPathMake = makeFolder(filePathName);
        //2. 경로와 이름을 나눠야함.
        //실제 파일 이름 ie 나 edge는 전체 경로가 전달된다.
        String originFilename = uploadFile.getOriginalFilename();
        String originFilenamePath = originFilename.substring(originFilename.lastIndexOf("\\") + 1);

        //저장되는 파일명  --- UUID
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "_" + originFilenamePath;

        //저장되는 실제 파일 생성
        String saveUrl = uploadPath + File.separator + folderPathMake + File.separator + fileName;
        Path savePath = Paths.get(saveUrl);

        try {
            //실제 파일저장
            uploadFile.transferTo(savePath);
            // 저장 된 파일 객체 전달
            return FileDTO.builder().filePath(filePathName).fileName(fileName).fileOriginName(originFilename).build();
        } catch (IOException e) {
            e.printStackTrace();
            log.warn("업로드 폴더 생성 실패: " + e.getMessage());
            return null;
        }

    }

    public FileDTO fileFindOne(String filePath, long fileTypeId) {
        try {
            FileVO fileVO = fileMapper.selectOnePathAndId(FileVO.builder().filePath(filePath).fileTypeId(fileTypeId).build());
            return modelMapper.map(fileVO, FileDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public FileDTO fileDownload(String fileName) {
        try {
            FileVO fileVO = fileMapper.selectOnefileName(fileName);
            return modelMapper.map(fileVO, FileDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // 웹소켓 바이트 파일 객체 --> multipartFile로 변환
    public MultipartFile fileConversionMultipartFile(ChatFileUploadDTO chatFileUploadDTO) {

        // DTO에서 파일 데이터 추출
        byte[] fileData = chatFileUploadDTO.getChatFileData();
        String fileName = chatFileUploadDTO.getChatFileName();
        String fileContentType = chatFileUploadDTO.getChatFileType();

        return new FileMultipart(fileData, fileName, fileContentType, fileData.length);

    }


    // 파일 확장자 확인
    public String getExtension(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        String extension = null;
        //파일의 Content Type 이 있을 경우 Content Type 기준으로 확장자 확인
        if (StringUtils.hasText(contentType)) {
            if (contentType.equals("image/jpeg")) {
                extension = "jpg";
            } else if (contentType.equals("image/png")) {
                extension = "png";
            } else if (contentType.equals("image/gif")) {
                extension = "gif";
            }
        } else {
            extension = null;
        }
        return extension;
    }


    //폴더 생성
    protected String makeFolder(String folder) {
        String folderPath = folder.replace("/", File.separator);
        File uploadPathFolder = new File(uploadPath, folderPath);
        if (uploadPathFolder.exists() == false) {
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }


    //파일 제거
    public ResponseEntity<Boolean> fileRemove(String filePath, String fileName) {
        log.info("이미지 제거 로직");
        String srcFileName = null;
        try {
            log.info(fileName);
            srcFileName = URLDecoder.decode(fileName, "utf-8");
            File file = new File(uploadPath + File.separator + filePath + File.separator + srcFileName);
            log.info(file);
            boolean result = file.delete();
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public byte[] getAttachmentImage(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
