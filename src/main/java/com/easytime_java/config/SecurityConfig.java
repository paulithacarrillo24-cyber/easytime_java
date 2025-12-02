package com.easytime_java.config;

import com.easytime_java.Service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); 
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(authorize -> authorize
                // Acceso público: /registro, /, /login y todos los recursos estáticos
                .requestMatchers("/", "/login", "/register/**", "/css/**", "/js/**", "/images/**", "/public/**").permitAll() 
                
                // Módulo /admin solo para ADMINISTRADOR
                .requestMatchers("/admin/**").hasRole("ADMINISTRADOR") 
                
                // Módulos con acceso restringido por rol
                .requestMatchers("/citas/**").hasAnyRole("CLIENTE", "ADMINISTRADOR", "JEFE_DE_GESTION") 
                .requestMatchers("/usuarios/**", "/roles/**", "/inventario/**", "/productos/**", "/servicios/**")
                    .hasAnyRole("ADMINISTRADOR", "JEFE_DE_GESTION") // Ejemplo: Solo admins y jefes de gestión pueden acceder a estos módulos
                
                // Cualquier otra solicitud requiere autenticación
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                // ⭐ CORRECCIÓN CLAVE: Redirigir a /home o /dashboard
                .defaultSuccessUrl("/dashboard", true) 
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );
        return http.build();
    }
}