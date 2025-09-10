package hu.shopix.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Swagger/OpenAPI engedélyezése
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                // ideiglenesen publikus olvasás
                .requestMatchers(HttpMethod.GET, "/categories/**", "/products/**").permitAll()
                // ideiglenesen MINDEN engedélyezett (ha teljesen nyitni akarod most):
                .anyRequest().permitAll()
            )
            // ezek KIKAPCS
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable);
            // ha van saját JWT filtered, most NE add a láncba

        return http.build();
    }
}


