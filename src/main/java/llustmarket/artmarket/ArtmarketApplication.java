package llustmarket.artmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ArtmarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtmarketApplication.class, args);
    }


}
