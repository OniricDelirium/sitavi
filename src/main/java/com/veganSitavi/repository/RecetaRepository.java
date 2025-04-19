package com.veganSitavi.repository;

import com.veganSitavi.domain.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetaRepository extends JpaRepository<Receta,Long>{
    
}
