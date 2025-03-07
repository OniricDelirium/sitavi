package com.veganSitavi.repository;

import com.veganSitavi.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto,Long>{
    
}
