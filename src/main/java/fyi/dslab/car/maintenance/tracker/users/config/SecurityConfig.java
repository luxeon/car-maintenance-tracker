package fyi.dslab.car.maintenance.tracker.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/users",
                        "/auth-code",
                        "/auth",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3" + "/api-docs/**",
                        "/users/*/cars",
                        "/users/*/cars/**").permitAll().anyRequest().authenticated())
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))
                //                .authenticationProvider(authenticationProvider)
                //                .addFilterBefore(jwtAuthenticationFilter,
                //                UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(configurer -> configurer.authenticationEntryPoint(new JwtAuthenticationEntryPoint()));

        return http.build();
    }
}
