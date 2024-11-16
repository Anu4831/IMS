//package com.inventorysystem.Backend.security;
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//
//import java.time.Duration;
//import java.util.Arrays;
//import java.util.Collections;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//public class SecurityConfiguration {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
//                    @Override
//                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//                        CorsConfiguration corsConfiguration = new CorsConfiguration();
//                        corsConfiguration.setAllowCredentials(true);
//                        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
//                        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
//                        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
//                        corsConfiguration.setMaxAge(Duration.ofMinutes(5L));
//                        return corsConfiguration;
//                    }
//                }))
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/user/login").permitAll() // Allow unauthenticated access to login
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(withDefaults()); // You may also consider using formLogin or any other authentication mechanism
//
//        return httpSecurity.build();
//    }
//}
