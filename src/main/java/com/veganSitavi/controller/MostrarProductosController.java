package com.veganSitavi.controller;


import com.veganSitavi.domain.Categoria;
import com.veganSitavi.service.CategoriaService;
import com.veganSitavi.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class MostrarProductosController {
    
    @Autowired
    private CategoriaService categoriaService;
    
    @Autowired
    private ProductoService productoService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var categorias=categoriaService.getCategorias();
        model.addAttribute("categorias", categorias);
        var productos=productoService.getProductos();
        model.addAttribute("productos", productos);
        
        
        return "/productos/listado";
    }
    
    @GetMapping("/listado/{idCategoria}")
    public String listado(Model model, Categoria categoria) {
        var categorias=categoriaService.getCategorias();
        model.addAttribute("categorias", categorias);
        categoria=categoriaService.getCategoria(categoria);
        
        var productos=categoria.getProductos();
        model.addAttribute("productos", productos);
        
        
        return "/productos/listado";
    }
    
}