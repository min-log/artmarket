package llustmarket.artmarket.web.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendTokenByEmail(String to, String token, Date expirationTime) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("인증 번호");
        msg.setText("인증 번호 : " + token + ". 유효 기간 : " + expirationTime);

        javaMailSender.send(msg);
    }
}