package com.veganSitavi.service;

// Una clase service es una clase que va estar en todo momento atenta a llamados o consultas

import com.veganSitavi.domain.Categoria;
import com.veganSitavi.repository.CategoriaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {
    
    @Autowired // Para acceder automaticamente sin necesidad de hacer un new
    private CategoriaRepository categoriaRepository;
    
    
    // Método que hace consulta a la base de datos y recupera los registros, se le llama transacción
    // findAll() = SELECT * FROM [Tabla];
    
    @Transactional(readOnly=true)
    public List<Categoria> getCategorias(){
        var lista = categoriaRepository.findAll();
        
        return lista;
    }
    
    @Transactional(readOnly=true)
    public Categoria getCategoria(Categoria categoria){
        return categoriaRepository.findById(categoria.getIdCategoria()).orElse(null);
    }
    @Transactional
    public void save(Categoria categoria){
        categoriaRepository.save(categoria);
    }
    
    @Transactional
    public void delete(Categoria categoria){
        categoriaRepository.delete(categoria);
    }
}