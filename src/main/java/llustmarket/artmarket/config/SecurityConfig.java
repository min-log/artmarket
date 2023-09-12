package llustmarket.artmarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable() // CSRF 사용 해제
                .authorizeRequests()
                .antMatchers("/**").permitAll() // 예시: 특정 URL은 모든 사용자에게 허용
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/home") // 로그인 페이지 지정
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }
}