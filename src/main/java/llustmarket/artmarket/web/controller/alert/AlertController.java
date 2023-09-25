package llustmarket.artmarket.web.controller.alert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class AlertController {

    @PostMapping("/index/alram")
    public void alramGet(long member_id){

    }

}
