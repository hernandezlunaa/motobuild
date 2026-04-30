package com.motobuild.controller;

import com.motobuild.repository.MotorcycleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MotorcycleController {

    private final MotorcycleRepository motorcycleRepository;

    public MotorcycleController(MotorcycleRepository motorcycleRepository) {
        this.motorcycleRepository = motorcycleRepository;
    }

    @GetMapping("/motorcycles")
    public String showMotorcycles(Model model) {
        model.addAttribute("motorcycles", motorcycleRepository.findAll());
        return "motorcycles";
    }
}