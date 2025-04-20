package com.ims.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity // Enables method-level security annotations like @PreAuthorize
public class SecurityConfiguration {

  // Password encoder using BCrypt — secure hashing for even in-memory credentials
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // Temporary in-memory user setup
  @Bean
  public UserDetailsService userDetailsService() {
    var user = User.withUsername("user")
            .password(passwordEncoder().encode("password")) // 🔒 'user' with role USER TODO: ABSTRACT THIS before release
            .roles("USER")
            .build();

    var admin = User.withUsername("admin")
            .password(passwordEncoder().encode("adminpass")) // 🔒 'admin' with roles ADMIN + USER TODO: ABSTRACT THIS before release
            .roles("ADMIN", "USER")
            .build();

    return new InMemoryUserDetailsManager(user, admin);
  }

  // Main security filter configuration
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
            // Disable CSRF for simplicity (API use only — safe when not using browser forms)
            .csrf(AbstractHttpConfigurer::disable)

            // Configure route-based authorization
            .authorizeHttpRequests(cfg -> cfg

                    // Admin-only: endpoints for user management
                    .requestMatchers("/api/users/**").hasRole("ADMIN")

                    // Instruments: accessible by both USER and ADMIN roles
                    .requestMatchers("/api/instruments/**").hasAnyRole("USER", "ADMIN")
                    .requestMatchers("/api/students/**").hasAnyRole("USER", "ADMIN")
                    .requestMatchers("/api/locations/**").hasAnyRole("USER", "ADMIN")

                    // Public or non-protected endpoints can be accessed freely
                    .anyRequest().permitAll()
            )

            // Enable HTTP Basic authentication (username/password popup or Insomnia test)
            .httpBasic(Customizer.withDefaults())

            .build();
  }
}
