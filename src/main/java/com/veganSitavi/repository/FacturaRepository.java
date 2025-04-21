package com.veganSitavi.repository;

import com.veganSitavi.domain.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository <Factura,Long> {
     
}
