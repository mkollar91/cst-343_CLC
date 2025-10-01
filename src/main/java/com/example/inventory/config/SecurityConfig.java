package com.example.inventory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

        @Bean
        public SecurityFilterChain security(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/css/**", "/js/**").permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(login -> login
                                                .loginPage("/login").permitAll()
                                                .defaultSuccessUrl("/products", true))
                                .logout(logout -> logout
                                                .logoutUrl("/logout") // POST endpoint
                                                .logoutSuccessUrl("/login?logout") // redirect back to login
                                                .invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID")
                                                .permitAll());
                return http.build();
        }

        @Bean
        public UserDetailsService users() {
                return new InMemoryUserDetailsManager(
                                User.withUsername("admin")
                                                .password("{noop}admin123") // plaintext for demo
                                                .roles("ADMIN")
                                                .build());
        }
}
