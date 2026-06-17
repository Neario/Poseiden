package io.project.poseiden.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SpringSecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    @Bean
    public SecurityFilterChain springSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/login").permitAll()
                .anyRequest().authenticated()
        ).formLogin(formLogin -> formLogin
                .loginPage("/login")
                .defaultSuccessUrl("/bidList/list")
                .permitAll()
        ).logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")

        );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(
            HttpSecurity http,
            BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(
                AuthenticationManagerBuilder.class
        );

        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
