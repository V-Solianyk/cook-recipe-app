package com.example.cookrecipeappilication.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.example.cookrecipeappilication.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, "/register", "/login")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/recipes").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.POST, "/recipes/{parentId}").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/recipes/{id}").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/recipes/{id}").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/recipes/{id}")
                        .hasAnyRole("MANAGER", "CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/recipes/versions/{id}")
                        .hasAnyRole("MANAGER", "CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/recipes/by-date-and-description")
                        .hasAnyRole("MANAGER", "CUSTOMER")
                        .requestMatchers(HttpMethod.PATCH, "/users/{id}").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/auth/**",
                                "/v3/api-docs/**", "/v3/api-docs.yaml",
                                "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
