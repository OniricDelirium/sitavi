package com.veganSitavi.repository;

import com.veganSitavi.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria,Long>{
    
    // Con esta interfaz se tiene acceso a todo lo de MySQL
}
