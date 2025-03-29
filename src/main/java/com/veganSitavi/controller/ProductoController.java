package com.veganSitavi.controller;

import com.veganSitavi.domain.Producto;
import com.veganSitavi.service.CategoriaService;
import com.veganSitavi.service.ProductoService;
import com.veganSitavi.service.FirebaseStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/producto")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var productos=productoService.getProductos();
        model.addAttribute("productos", productos);
        var categorias=categoriaService.getCategorias();
        model.addAttribute("categorias", categorias);
        return "/producto/listado";
    }
    
    @GetMapping("/eliminar/{idProducto}")
    public String eliminar(Producto producto) {
        productoService.delete(producto);
        
        return "redirect:/producto/listado";
    }
    
    @GetMapping("/modificar/{idProducto}")
    public String modificar(Producto producto, Model model) {
        producto=productoService.getProducto(producto);
        model.addAttribute("producto", producto);
        var productos=productoService.getProductos();
        model.addAttribute("productos", productos);
        var categorias=categoriaService.getCategorias();
        model.addAttribute("categorias", categorias);
        return "/producto/modifica";
    }
    
    @Autowired
    private FirebaseStorageService firebaseStorageService;
    
    @PostMapping("/guardar")
    public String guardar(Producto producto, @RequestParam("imagenFile") MultipartFile imagenFile) {
        if (!imagenFile.isEmpty()){
            //Guardar en la nube
            productoService.save(producto);
            String ruta=firebaseStorageService.cargaImagen(imagenFile, "producto", producto.getIdProducto());
           producto.setImagen(ruta);
           }
        productoService.save(producto);

        return "redirect:/producto/listado";
    }
}