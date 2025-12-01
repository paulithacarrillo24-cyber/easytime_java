//package com.easytime_java.repository;

//import com.easytime_java.model.Usuario;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

//@Repository
//public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Si necesitas búsquedas personalizadas luego, aquí se agregan.
//}


package com.easytime_java.repository;

import com.easytime_java.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("SELECT u FROM Usuario u WHERE " +
           "LOWER(u.nombre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "u.documento LIKE CONCAT('%', :keyword, '%')")
    List<Usuario> buscarPorNombreODocumento(String keyword);
}
