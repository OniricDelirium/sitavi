package com.veganSitavi.controller;

import com.veganSitavi.domain.Contacto;
import com.veganSitavi.service.CorreoService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contacto")
public class ContactoController {

    @Autowired
    private CorreoService correoService;

    @GetMapping("/formulario")
    public String mostrarContacto(Model model) {
        model.addAttribute("contacto", new Contacto());
        return "/contacto/formulario";
    }

    @PostMapping("/guardar")
    public String procesarFormulario(@ModelAttribute Contacto contacto) throws MessagingException {
        String destinatario = "rojaspiedraalejandro29@gmail.com";

        String asunto = "Nuevo mensaje desde Vegan Sitavi";

        String contenidoHtml = "<h2>Formulario de Contacto</h2>"
                + "<p><strong>Nombre:</strong> " + contacto.getNombreCompleto() + "</p>"
                + "<p><strong>Apellidos:</strong> " + contacto.getApellidos() + "</p>"
                + "<p><strong>Correo:</strong> " + contacto.getCorreo() + "</p>"
                + "<p><strong>Mensaje:</strong><br>" + contacto.getMensaje() + "</p>";

        correoService.enviarCorreoHtml(destinatario, asunto, contenidoHtml);

        return "redirect:/contacto/formulario";
    }
}
