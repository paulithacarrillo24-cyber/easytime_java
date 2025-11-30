//package com.easytime_java.security;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//public class SecurityConfig {

    //@Bean
    //public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //http
           // .csrf(csrf -> csrf.disable())               // Desactiva CSRF
           // .authorizeHttpRequests(auth -> auth
             //   .anyRequest().permitAll()              // Permite todas las rutas
           // )
            //.formLogin(form -> form.disable())         // Desactiva login por formulario
            //.httpBasic(basic -> basic.disable());      // Desactiva login básico

        //return http.build();
  //  }
//}
package com.easytime_java.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // puedes dejarlo desactivado si no usas tokens
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/registro", "/home").permitAll() // acceso libre
                .requestMatchers("/usuarios/**").authenticated()             // protegido
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/login")                     // tu login.html
                .defaultSuccessUrl("/usuarios", true)    // redirige a la tabla al loguear
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")       // vuelve al login al cerrar sesión
                .permitAll()
            );

        return http.build();
    }
}


