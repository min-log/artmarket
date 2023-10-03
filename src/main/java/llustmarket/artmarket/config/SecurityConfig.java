package llustmarket.artmarket.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**", "/", "/chat/*", "/apiChat", "/sub", "/topic", "/pub").permitAll() // 웹소켓 관련 시큐리티 제거
                .antMatchers("/logout").permitAll()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated();
        http
                .logout()
                .invalidateHttpSession(true)
                .deleteCookies("loginCookie")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK));
        // 세션 타임아웃 설정
        http.sessionManagement()
                .sessionFixation().migrateSession()
                .maximumSessions(1) // 동일한 사용자로부터 한 번에 받을 수 있는 최대 세션 수
                .maxSessionsPreventsLogin(false) // false일 경우, 새 세션을 생성하고 기존 세션을 무효화함
                .expiredUrl("/") // 세션이 만료되면 이동할 URL
                .sessionRegistry(sessionRegistry()); // 세션 관리자
        http.csrf().disable();
        return http.build();
    }
}
