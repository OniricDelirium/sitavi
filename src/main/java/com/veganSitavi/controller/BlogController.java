package com.veganSitavi.controller;

import com.veganSitavi.domain.Receta;
import com.veganSitavi.domain.Receta;
import com.veganSitavi.service.FirebaseStorageService;
import com.veganSitavi.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private RecetaService recetaService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var recetas = recetaService.getRecetas();
        model.addAttribute("recetas", recetas);
        return "/blog/listado";
    }

    @GetMapping("/detalle/{idReceta}")
    public String detalle(@PathVariable("idReceta") Long idReceta, Model model) {
        var receta = recetaService.getRecetaPorId(idReceta);
        model.addAttribute("receta", receta);
        return "/blog/detalle";
    }
    
    @GetMapping("/eliminar/{idReceta}")
    public String eliminar(Receta receta) {
        recetaService.delete(receta);
        
        return "redirect:/blog/listado";
    }
    
    @GetMapping("/modificar/{idReceta}")
    public String modificar(Receta receta, Model model) {
        receta=recetaService.getReceta(receta);
        model.addAttribute("receta", receta);
        var recetas=recetaService.getRecetas();
        model.addAttribute("recetas", recetas);
        return "/blog/modifica";
    }

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @PostMapping("/guardar")
    public String guardar(Receta receta, @RequestParam("imagenFile") MultipartFile imagenFile) {
        if (!imagenFile.isEmpty()) {
            //Guardar en la nube
            recetaService.save(receta);
            String ruta = firebaseStorageService.cargaImagen(imagenFile, "receta", receta.getIdReceta());
            receta.setImagen(ruta);
        }
        recetaService.save(receta);

        return "redirect:/blog/listado";
    }
}
