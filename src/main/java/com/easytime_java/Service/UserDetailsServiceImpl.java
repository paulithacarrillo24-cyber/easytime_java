package com.easytime_java.Service;

import com.easytime_java.model.Usuario;
import com.easytime_java.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<Usuario> optionalUsuario = usuarioRepository.findByCorreoUser(username);

        Usuario usuario = optionalUsuario.orElseThrow(() -> 
            new UsernameNotFoundException("Usuario no encontrado con correo: " + username));

        // 1. Obtiene las autoridades (Esto ahora funciona gracias al FetchType.EAGER en Usuario.java)
        Collection<GrantedAuthority> authorities = getAuthorities(usuario);
        
        // 2. Manejo del estado (enabled)
        // Se asegura que si EST_USER es NULL, el usuario sea considerado deshabilitado (false).
        boolean enabled = usuario.getEstUser() != null && usuario.getEstUser();
        
        // 3. Mapea la información a un objeto UserDetails
        return new User(
            usuario.getCorreoUser(),     // Username (el correo)
            usuario.getPassword(),       // Contraseña CIFRADA 
            enabled,                     // Estado (enabled/disabled)
            true,                        // account non expired
            true,                        // credentials non expired
            true,                        // account non locked
            authorities                  // Lista de roles/autoridades
        );
    }
    
    /**
     * Mapea el objeto Rol (obtenido por la relación JPA) a una colección de GrantedAuthority.
     */
    private Collection<GrantedAuthority> getAuthorities(Usuario usuario) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        // Accede al nombre del Rol a través de la relación JPA.
        if (usuario.getRol() != null && usuario.getRol().getNomRol() != null) {
            
            // Construye el nombre del rol con el prefijo "ROLE_"
            String roleName = "ROLE_" + usuario.getRol().getNomRol().toUpperCase(); 
            
            authorities.add(new SimpleGrantedAuthority(roleName));
        } else {
            // Manejo de seguridad: si no tiene rol, asignamos un rol por defecto seguro
            authorities.add(new SimpleGrantedAuthority("ROLE_CLIENTE"));
        }
        
        return authorities;
    }
}