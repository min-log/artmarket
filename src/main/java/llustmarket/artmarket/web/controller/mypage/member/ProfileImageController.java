package llustmarket.artmarket.web.controller.mypage.member;

import llustmarket.artmarket.domain.file.FileType;
import llustmarket.artmarket.domain.file.FileVO;
import llustmarket.artmarket.web.dto.file.FileDTO;
import llustmarket.artmarket.web.dto.mypage.member.ProfileImageDTO;
import llustmarket.artmarket.web.mapper.file.FileMapper;
import llustmarket.artmarket.web.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProfileImageController {

    private final FileService fileService;
    private final FileMapper fileMapper;


    @PutMapping("/mypage-profile")
    public ResponseEntity<String> handleProfileImageUpload(@ModelAttribute ProfileImageDTO profileImageDTO) {
        try {
            String profileFileType = profileImageDTO.getProfileFileType();
            Long profileFileTypeId = profileImageDTO.getProfileFileTypeId();
            log.info("profileFileType={}, profileFileTypeId={}", profileFileType, profileFileTypeId);
            
            FileVO file = FileVO.builder()
                    .filePath(profileFileType)
                    .fileTypeId(profileFileTypeId)
                    .build();
            // 프로필 이미지가 이미 존재하면 삭제
            FileVO fileToDelete = fileMapper.selectOnePathAndId(file);

            log.info("fileToDelete={}", fileToDelete);

            if (fileToDelete != null) {
                fileService.fileRemove(fileToDelete.getFilePath(), fileToDelete.getFileName());
            }

            MultipartFile profileImage = profileImageDTO.getProfileImage();

            // 파일 확장자 확인
            String extension = fileService.getExtension(profileImage);
            if (extension == null || !(extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg") || extension.equals("gif"))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("허용된 확장자는 jpg, png, jpeg, gif 입니다.");
            }

            // 파일을 저장하고 데이터베이스에 등록
            FileDTO uploadedFile = fileService.fileRegister(FileType.PROFILE, profileImage);

            // FileVO를 생성하여 데이터베이스에 저장
            FileVO fileVO = FileVO.builder()
                    .filePath(uploadedFile.getFilePath())
                    .fileTypeId(profileFileTypeId)
                    .fileOriginName(uploadedFile.getFileOriginName())
                    .fileName(uploadedFile.getFileName())
                    .fileDate(LocalDateTime.now())
                    .build();

            // 데이터베이스에 저장
            fileMapper.insertOne(fileVO);

            return ResponseEntity.status(HttpStatus.OK).body("파일 업로드에 성공했습니다");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }
}