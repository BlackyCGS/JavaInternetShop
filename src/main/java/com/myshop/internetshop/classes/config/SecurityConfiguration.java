package com.myshop.internetshop.classes.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
public class SecurityConfiguration {

    String admin = "ADMIN";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CookieClearingLogoutHandler cookies = new CookieClearingLogoutHandler("jwt",
                "jwtr");
        http
                .logout(logout -> {
                    logout.logoutUrl("/api/auth/logout");
                    logout.addLogoutHandler(cookies);
                    logout.logoutSuccessUrl("/api/auth/logout/success");
                })
                .securityMatcher("api/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth").permitAll()
                        .requestMatchers("/api/log").hasRole(admin)
                        .requestMatchers("/api/products").permitAll()
                        .requestMatchers("/api/orders").authenticated()
                        .requestMatchers("/api/parser").hasRole(admin)
                        .requestMatchers("/api/users").authenticated()
                        .requestMatchers("/api/visits").hasRole(admin))
                .csrf(AbstractHttpConfigurer::disable)  //NOSONAR
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // Уточните источник фронтенда
        configuration.setAllowedMethods(List
                .of("GET", "POST", "PUT", "DELETE", "PATCH")); // Указываем методы
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of("http://localhost:5173", "*")); // NOSONAR
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
