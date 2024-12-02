package com.linkedin.api.savedjob.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Optionally disable CSRF (recommended only for APIs)
                .authorizeRequests()
                // Whitelist Swagger URLs
                .requestMatchers(
                        "/swagger-ui.html",
                        "/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/configuration/ui",
                        "/swagger-resources/configuration/security").permitAll()

                // All other requests require authentication
                .anyRequest().permitAll()



                .and()
                .httpBasic();  // Optional: Basic Authentication if needed

        return http.build();
    }




}
