package com.music_streaming.config;


import com.music_streaming.middlewares.JwtFilter;
import com.music_streaming.middlewares.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
public class SecurityConfig {

    @Bean
    public HttpFirewall allowAllFirewall() {
        return new StrictHttpFirewall();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Uses BCrypt hashing algorithm
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Disable CSRF for stateless REST API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Allow auth endpoints
                        .anyRequest().authenticated() // Protect other endpoints
                )
                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class); // Add your JWT filter

        return http.build();
    }
}
