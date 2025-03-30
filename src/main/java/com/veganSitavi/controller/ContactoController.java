package com.veganSitavi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contacto")
public class ContactoController {

    @GetMapping("/formulario")
    public String mostrarContacto() {
        return "contacto/formulario"; 
    }
}