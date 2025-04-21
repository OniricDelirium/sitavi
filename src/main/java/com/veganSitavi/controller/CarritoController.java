package com.veganSitavi.controller;

// Clases que tienen anotación controller van a estar guardadas en un indice para cuando llegue un url (petición de usuario) responda con algo

import com.veganSitavi.domain.Item;
import com.veganSitavi.domain.Producto;
import com.veganSitavi.service.ItemService;
import com.veganSitavi.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private ItemService itemService;
    
    @Autowired
    private ProductoService productoService;
    
    // ModelAndView: guardar un poco del modelo y html(Vista)
    
    @GetMapping("/agregar/{idProducto}")  
    public ModelAndView agregar(Model model,Item item){
        Item item2 = itemService.getItem(item); // se devuelve el item de la lista o null
        
        if(item2 == null){
            // No esta en la lista, se debe recuperar la info desde productos (tabla BD)
            Producto producto = productoService.getProducto(item);
            
            item2 = new Item(producto);
            
        }
        
        itemService.save(item2);
        
        var lista = itemService.getItems();
        var totalVenta = itemService.getTotal();
        
        model.addAttribute("listaItems",lista);
        model.addAttribute("listaTotal",lista.size());
        model.addAttribute("totalVenta",totalVenta);
        
        return new ModelAndView("/carrito/fragmentos :: verCarrito");  
    }                                         
    
    
    @GetMapping("/listado")  
    public String listado(Model model){
        var lista = itemService.getItems();
        var totalCompra = itemService.getTotal();
        
        model.addAttribute("listaItems",lista);
        model.addAttribute("totalCompra", totalCompra);
        
        return "/carrito/listado";  
    }                       
    
     @GetMapping("/eliminar/{idProducto}")
    public String eliminar(Model model, Item item) {
        itemService.delete(item);
        return "redirect:/carrito/listado";
    }

    @GetMapping("/modificar/{idProducto}")
    public String modificar(Model model, Item item) {
        item = itemService.getItem(item);
        model.addAttribute("item",item);
        return "/carrito/modifica";
    }
    
    @PostMapping("/guardar")
    public String guardar(Item item) {
        itemService.update(item);
        return "redirect:/carrito/listado";
    }
    
}
