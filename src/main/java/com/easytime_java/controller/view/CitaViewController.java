package com.easytime_java.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CitaViewController {

    @GetMapping("/cita")
    public String mostrarCita() {
        return "Cita"; // Esto busca templates/Cita.html
    }
}

