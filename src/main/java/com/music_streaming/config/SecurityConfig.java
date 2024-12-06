package com.music_streaming.config;

import com.music_streaming.middlewares.JwtFilter;
import com.music_streaming.middlewares.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

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

        http.sessionManagement(
                session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS)
        );
        return http.build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://10.0.2.2:8080","http://10.0.2.2","http://76.38.163.141:8080","http://192.168.1.250:33946","http://localhost:8080","http://127.0.0.1:8080","http://music-streaming-backend-uh0v.onrender.com/") // Emulator URL
//                .allowedOrigins("*")
                .allowedOrigins("http://10.0.2.2:*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                ;
    }


//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration corsConfig = new CorsConfiguration();
//        corsConfig.addAllowedOrigin("http://10.0.2.2:8080");
//        corsConfig.addAllowedOrigin("http://localhost:8080");
//        corsConfig.addAllowedOrigin("http://10.0.2.2"); // Emulator IP
//        corsConfig.addAllowedOrigin("http://76.38.163.141:8080"); // Emulator IP
//        corsConfig.addAllowedOrigin("http://192.168.1.250:33946"); // Emulator IP
//        corsConfig.addAllowedOrigin("http://127.0.0.1:8080"); // Emulator IP
//        corsConfig.addAllowedOrigin("http://music-streaming-backend-uh0v.onrender.com/"); // Emulator IP
//        corsConfig.addAllowedMethod("*");  // Allow all HTTP methods
//        corsConfig.addAllowedHeader("*");  // Allow all headers
//        corsConfig.setAllowCredentials(true);  // Allow credentials
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfig);  // Apply CORS configuration to all endpoints
//
//        return source;
//    }

}
