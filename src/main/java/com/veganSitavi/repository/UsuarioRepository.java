package com.veganSitavi.repository;

import com.veganSitavi.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {    
    
    //Se usa para el proceso de Login
    Usuario findByUsername(String username);
    
    //Se usa para buscar un registro de usuario en el proceso de activacion de un nuevo usuario
    
    Usuario findByUsernameAndPassword(String username, String Password);
    
    //Se utiliza para validar si dentro de la tabla usuario ya existe un registro con el username o correo indicados
    Usuario findByUsernameOrCorreo(String username, String correo);
    boolean existsByUsernameOrCorreo(String username, String correo);
}
