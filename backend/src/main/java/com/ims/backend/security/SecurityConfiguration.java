package com.ims.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity // Enables use of @PreAuthorize and @Secured
public class SecurityConfiguration {

  // BCrypt is used to store and verify passwords securely
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // Defines access rules and enables basic HTTP auth
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
            .csrf(AbstractHttpConfigurer::disable)

            .authorizeHttpRequests(cfg -> cfg
                    // Admin-only: full access to user management
                    .requestMatchers("/api/users/**").hasRole("ADMIN")

                    // Users and Admins can manage these entities
                    .requestMatchers("/api/instruments/**").hasAnyRole("USER", "ADMIN")
                    .requestMatchers("/api/students/**").hasAnyRole("USER", "ADMIN")
                    .requestMatchers("/api/locations/**").hasAnyRole("USER", "ADMIN")

                    // Everything else (frontend routes, static assets) is public
                    .anyRequest().permitAll()
            )

            // Use HTTP Basic auth for development/testing
            .httpBasic(Customizer.withDefaults())

            .build();
  }
}
