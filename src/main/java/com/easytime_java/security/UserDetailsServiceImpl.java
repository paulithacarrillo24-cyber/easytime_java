package com.easytime_java.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.easytime_java.model.Usuario;
import com.easytime_java.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
public UserDetails loadUserByUsername(String correoUser) throws UsernameNotFoundException {
    Usuario usuario = usuarioRepository.findByCorreoUser(correoUser)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correoUser));

    return User.builder()
            .username(usuario.getCorreoUser())   // login por correo
            .password(usuario.getCONTRA_USER())  // contraseña encriptada con BCrypt
            .roles(usuario.getROL_USER())        // rol en BD
            .build();
}
}