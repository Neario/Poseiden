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
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Configuration de Spring Security
     * Permet de définir les régles d'autorisation, avec une page de connexion custom,
     * avec la gestion de la déconnexion et l'encodage avec BCryptPasswordEncoder des mots de passe
     * L'authentification est basée sur les sessions HTTP (session based)
     *
     * Afin d'accéder à l'application nous devons être authentifié
     * La route /secure/article-details n'est disponible que pour ceux qui ont le role ADMIN
     * La vérification des role utilise {@link CustomUserDetails#getAuthorities()}.
     *
     * @param http est l'objet de configuration fourni par SpringSecurity
     * @return la chaîne de configuration personnalisée
     */
    @Bean
    public SecurityFilterChain configuration(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/app/login").permitAll()
                .requestMatchers("/app/error").permitAll()
                .requestMatchers("/secure/article-details").hasRole("ADMIN")
                .anyRequest().authenticated()
        ).formLogin(formLogin -> formLogin
                .loginPage("/app/login")
                .defaultSuccessUrl("/bidList/list")
                .permitAll()
        ).logout(logout -> logout
                .logoutUrl("/app-logout")
                .logoutSuccessUrl("/app/login?logout")

        ).exceptionHandling(exception -> exception.accessDeniedPage("/app-error"));
        return http.build();
    }

    /**
     * Permet de configurer {@link AuthenticationManager}
     * Permet d'attacher un {@link CustomUserDetailsService} qui charge l'utilisateur
     * et de vérifié le mot mot de passe hash
     * @param http permet de récupérer l'objet {@link HttpSecurity} partagé
     * @param bCryptPasswordEncoder l'encoder Bcrypt pour le mot de passe
     * @return {@link AuthenticationManager} configuré
     */
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

    /**
     * Algorithe afin de hash le mot de passe avec un sel aléatoire
     * @return l'objet {@link BCryptPasswordEncoder}
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
