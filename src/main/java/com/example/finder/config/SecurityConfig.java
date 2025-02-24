package com.example.finder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        // Rutas públicas: login, registro y recursos estáticos
                        .requestMatchers("/", "/register", "/login", "/api/auth/**", "/css/**", "/js/**").permitAll()
                        // El resto requiere autenticación
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        // Página de login personalizada (se muestra cuando el usuario no está autenticado)
                        .loginPage("/login")
                        // URL a la que se envían las credenciales (debe coincidir con el atributo action del formulario)
                        .loginProcessingUrl("/api/auth/login")
                        // Si el login es exitoso, redirige a bienvenido.html
                        .defaultSuccessUrl("/swipe", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );
        return http.build();    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password encryption
    }
}