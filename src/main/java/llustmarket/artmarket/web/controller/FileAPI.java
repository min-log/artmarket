package llustmarket.artmarket.web.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/file")
public class FileAPI {
    @PostMapping("/register")
    public void fileRegister(MultipartFile file){
        log.info("파일 저장");
        log.info("file : {}",file);
    }
}
