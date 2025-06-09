package com.tienda.proyectofinal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final FirebaseAuthenticationProvider firebaseAuthProvider;

    public SecurityConfig(FirebaseAuthenticationProvider firebaseAuthProvider) {
        this.firebaseAuthProvider = firebaseAuthProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(firebaseAuthProvider)

                // Rutas públicas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/api/**").permitAll() // Permitir acceso libre desde Android
                        .anyRequest().authenticated()
                )

                // Login solo para parte web
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/panel", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )

                // Logout
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                )

                // Desactivar CSRF para APIs REST
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**") // no CSRF para llamadas Android
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}


