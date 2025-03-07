package com.veganSitavi.controller;

// Clases que tienen anotación controller van a estar guardadas en un indece para cuando llegue un url (petición de usuario) responda con algo

import com.veganSitavi.domain.Categoria;
import com.veganSitavi.service.CategoriaService;
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
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService; // Recupero el servicio
    
    @GetMapping("/listado")
    public String listado(Model model){
        var categorias=categoriaService.getCategorias(true); // Recuperando el array list
        model.addAttribute("categorias",categorias); // inyectando el array list para que se vea en el html
        
        return"/categoria/listado";
    }
    
    @GetMapping("/eliminar/{idCategoria}")
    public String eliminar(Categoria categoria) {
        categoriaService.delete(categoria);
        
        return "redirect:/categoria/listado";
    }
    
    @GetMapping("/modificar/{idCategoria}")
    public String modificar(Categoria categoria, Model model) {
        categoria=categoriaService.getCategoria(categoria);
        model.addAttribute("categoria", categoria);
        
        return "/categoria/modifica";
    }
    
    @Autowired
    private FirebaseStorageService firebaseStorageService;
    
    @PostMapping("/guardar")
    public String guardar(Categoria categoria, @RequestParam("imagenFile") MultipartFile imagenFile) {
        if (!imagenFile.isEmpty()){
            //Guardar en la nube
            categoriaService.save(categoria);
            String ruta=firebaseStorageService.cargaImagen(imagenFile, "categoria", categoria.getIdCategoria());
           categoria.setRutaImagen(ruta);
           }
        categoriaService.save(categoria);

        return "redirect:/categoria/listado";
    }
}
