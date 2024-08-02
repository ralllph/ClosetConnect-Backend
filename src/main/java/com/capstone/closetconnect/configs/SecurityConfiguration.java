package com.capstone.closetconnect.configs;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final  JwtAuthenticationFilter jwtAuthFilter;

    private final  AuthenticationProvider authenticationProvider;

    //when app loads up spring security looks for this bean and its responsible for entire security config
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth-> auth
                                .requestMatchers
                                        ("/api/v1/auth/**",
                                                "/api/v1/clothingItems/all",
                                                "/api/v1/clothingItems/cloth/{clothId}",
                                                "api/v1/clothingItems/search/all",
                                                "/api/v1/clothingItems/{clothId}/cloth-image",
                                                "/api/v1/clothingItems/user/{userId}"
                                                )
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(
                        sess-> sess
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Create a new session for each request
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of
                ("http://localhost:3000",
                        "http://localhost:3000/",
                        "https://closetconnect.netlify.app",
                        "https://closetconnect.netlify.app/"));
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.setAllowedHeaders(List.of("*"));
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return source;
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfigurationSource source = corsConfigurationSource();
        return new CorsFilter(source);
    }

}
