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
import org.springframework.security.web.AuthenticationEntryPoint; // Importación necesaria
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
    
    // ⭐ NUEVO BEAN: Registra el punto de entrada personalizado
    @Bean
    public AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider())
            
            // ⭐ CAMBIO CLAVE: Manejo de excepciones para usar el EntryPoint
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(customAuthenticationEntryPoint())
            )
            
            .authorizeHttpRequests(authorize -> authorize
                // Rutas públicas
                .requestMatchers("/", "/index", "/servicios/publico", "/login", "/register/**", "/css/**", "/js/**", "/images/**", "/public/**").permitAll()

                // REQUIERE AUTENTICACIÓN PARA EL CARRITO
                .requestMatchers("/carrito/**").authenticated()

                // Módulo /admin solo para ADMINISTRADOR
                .requestMatchers("/admin/**").hasRole("ADMINISTRADOR")

                // Módulos con acceso restringido por rol
                .requestMatchers("/citas/**","/servicios/**","/productos/**").hasAnyRole("CLIENTE", "ADMINISTRADOR", "JEFE_DE_GESTION")
                .requestMatchers("/usuarios/**", "/roles/**", "/inventario/**", "/proveedores/**").hasAnyRole("ADMINISTRADOR", "JEFE_DE_GESTION")
                
                // Cualquier otra solicitud requiere autenticación (incluyendo /dashboard)
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                // Redirige al Dashboard privado después del login
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                // URL que intercepta el cierre de sesión
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // Redirige al index (/) después del cierre de sesión
                .logoutSuccessUrl("/")
                // Limpieza
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }
}