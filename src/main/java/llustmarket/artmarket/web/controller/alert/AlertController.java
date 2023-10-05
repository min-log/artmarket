package llustmarket.artmarket.web.controller.alert;

import llustmarket.artmarket.web.dto.alert.AlramNumberResponseDTO;
import llustmarket.artmarket.web.dto.alert.AlramRequestDTO;
import llustmarket.artmarket.web.dto.alert.Alrams;
import llustmarket.artmarket.web.service.alert.AlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RequiredArgsConstructor
@RestController
public class AlertController {
    private final AlertService alertService;


    @PostMapping("/index")
    public ResponseEntity<Object> alramTotal(@RequestBody AlramRequestDTO dto){
        log.info("# 회원의 알림 수");
        int alramTotal = alertService.searchOneAlertNumber(dto.getAlramTotalID());
        AlramNumberResponseDTO result = AlramNumberResponseDTO.builder().alramTotal(alramTotal).build();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/index/alram")
    public ResponseEntity<Object> alramList(@RequestBody AlramRequestDTO dto){
        log.info("# 알림 리스트");
        Alrams alrams = alertService.searchOneAlert(dto.getAlramId());
        return ResponseEntity.status(HttpStatus.OK).body(alrams);
    }


    @PatchMapping("/index/alram")
    public ResponseEntity<Object> alramListDelete(@RequestBody AlramRequestDTO dto){
        log.info("# 알림 리스트 제거");
        alertService.updateStatus(dto.getAlramAllInId());

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }



}
