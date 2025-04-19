package com.veganSitavi.service;

import com.veganSitavi.domain.Receta;
import com.veganSitavi.repository.RecetaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;

    @Transactional(readOnly = true)
    public List<Receta> getRecetas() {
        var lista = recetaRepository.findAll();
        return lista;
    }

    @Transactional(readOnly = true)
    public Receta getReceta(Receta receta) {
        return recetaRepository.findById(receta.getIdReceta()).orElse(null);
    }

    @Transactional(readOnly = true)
    public Receta getRecetaPorId(Long idReceta) {
        return recetaRepository.findById(idReceta).orElse(null);
    }

    @Transactional
    public void save(Receta receta) {
        recetaRepository.save(receta);
    }

    @Transactional
    public void delete(Receta receta) {
        recetaRepository.delete(receta);
    }
}
