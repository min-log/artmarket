package llustmarket.artmarket.web.service.member;

import llustmarket.artmarket.web.dto.alert.AlramDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
public class EmailService {

    @Autowired
    private  JavaMailSender javaMailSender;


    public void sendTokenByEmail(String to, String token, Date expirationTime) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("ARTMARKET - 인증 번호");
        msg.setText("인증 번호 : " + token + ". 유효 기간 : " + expirationTime);

        javaMailSender.send(msg);
    }

    public void sendAlertByEmail(String to, AlramDTO alramDTO) {
        MimeMessage message = javaMailSender.createMimeMessage();
        String body = "<div style='display:block; text-aling:center'>";
        body += "<h3>안녕하세요 ARTMARKET 알림이 왔습니다.</h3>";
        body += "<p>상세한 내용은 홈페이지에서 확인이 가능합니다.</p>";
        body += "<ul style='display: block;border: 1px solid #ccc;padding: 20px;border-radius: 5px;'>";
        body += "<li style='list-style: none;font-weight: bold;color:#ccc;margin-bottom: 5px;'> 보낸 사람 : " + alramDTO.getAlramSender() + "</li>";
        body += "<li style='list-style: none;line-height: 2;'> 알림 유형 : " + alramDTO.getAlramType() + "</li>";
        body += "<li style='list-style: none;line-height: 2;'> 알림 날짜 : " + alramDTO.getAlertDate()+ "</li>";
        body += "</ul'>";
        body += "</div'>";

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setSubject("ARTMARKET - 알림");
            messageHelper.setTo(to);
            messageHelper.setFrom("artmerket@naver.com", "ARTMARKET");
            messageHelper.setText(body, true);
            javaMailSender.send(message);
        }catch(Exception e){
            e.printStackTrace();
        }


    }




}