package com.veganSitavi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quienesSomos")
public class QuienesSomosController {

    @GetMapping("/listado")
    public String mostrarListado() {
        return "quienesSomos/listado"; 
    }
}