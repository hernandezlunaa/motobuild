package com.motobuild.controller;

import com.motobuild.model.Build;
import com.motobuild.model.BuildPart;
import com.motobuild.repository.PartCategoryRepository;
import com.motobuild.repository.PartRepository;
import com.motobuild.service.BuildService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PartController {

    private final PartRepository partRepository;
    private final PartCategoryRepository partCategoryRepository;
    private final BuildService buildService;

    public PartController(PartRepository partRepository,
                          PartCategoryRepository partCategoryRepository,
                          BuildService buildService) {
        this.partRepository = partRepository;
        this.partCategoryRepository = partCategoryRepository;
        this.buildService = buildService;
    }

    @GetMapping("/parts")
    public String showParts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer buildId,
            Model model
    ) {
        if (maxPrice != null && maxPrice.compareTo(BigDecimal.ZERO) < 0) {
            maxPrice = BigDecimal.ZERO;
        }

        model.addAttribute("parts",
                partRepository.filterParts(search, categoryId, maxPrice, sort));

        model.addAttribute("categories", partCategoryRepository.findAll());
        model.addAttribute("builds", buildService.getBuildsForDefaultUser());

        model.addAttribute("search", search);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("sort", sort);
        model.addAttribute("selectedBuildId", buildId);

        Build selectedBuild = null;
        Map<Integer, Integer> addedBuildPartIdsByPartId = new HashMap<>();

        if (buildId != null) {
            selectedBuild = buildService.getBuild(buildId);

            if (selectedBuild.getBuildParts() != null) {
                for (BuildPart buildPart : selectedBuild.getBuildParts()) {
                    if (!"removed".equals(buildPart.getStatus())) {
                        addedBuildPartIdsByPartId.put(
                                buildPart.getPart().getPartId(),
                                buildPart.getBuildPartId()
                        );
                    }
                }
            }
        }

        model.addAttribute("selectedBuild", selectedBuild);
        model.addAttribute("addedBuildPartIdsByPartId", addedBuildPartIdsByPartId);

        return "parts";
    }
}