package com.motobuild.controller;

import com.motobuild.repository.PartCategoryRepository;
import com.motobuild.repository.PartRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

@Controller
public class PartController {

    private final PartRepository partRepository;
    private final PartCategoryRepository partCategoryRepository;

    public PartController(PartRepository partRepository,
                          PartCategoryRepository partCategoryRepository) {
        this.partRepository = partRepository;
        this.partCategoryRepository = partCategoryRepository;
    }

    @GetMapping("/parts")
    public String showParts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String sort,
            Model model
    ) {
        model.addAttribute("parts",
                partRepository.filterParts(search, categoryId, maxPrice, sort));

        model.addAttribute("categories", partCategoryRepository.findAll());

        model.addAttribute("search", search);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("sort", sort);

        return "parts";
    }
}