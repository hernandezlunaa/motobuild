package com.motobuild.controller;

import com.motobuild.model.Build;
import com.motobuild.repository.MotorcycleRepository;
import com.motobuild.service.BuildService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BuildController {

    private final BuildService buildService;
    private final MotorcycleRepository motorcycleRepository;

    public BuildController(BuildService buildService,
                           MotorcycleRepository motorcycleRepository) {
        this.buildService = buildService;
        this.motorcycleRepository = motorcycleRepository;
    }

    @GetMapping("/builds")
    public String showBuilds(Model model) {
        model.addAttribute("builds", buildService.getBuildsForDefaultUser());
        model.addAttribute("motorcycles", motorcycleRepository.findAll());
        return "builds";
    }

    @PostMapping("/builds")
    public String createBuild(@RequestParam String buildName,
                              @RequestParam Integer motorcycleId,
                              @RequestParam(required = false) String description,
                              RedirectAttributes redirectAttributes) {
        Build build = buildService.createBuild(buildName, motorcycleId, description);
        redirectAttributes.addFlashAttribute("successMessage", "Build created successfully.");
        return "redirect:/builds/" + build.getBuildId();
    }

    @GetMapping("/builds/{buildId}")
    public String showBuildDetails(@PathVariable Integer buildId, Model model) {
        Build build = buildService.getBuild(buildId);

        model.addAttribute("build", build);
        model.addAttribute("totalCost", buildService.calculateTotalCost(build));
        model.addAttribute("warnings", buildService.getWarnings(buildId));

        return "build-details";
    }

    @PostMapping("/builds/{buildId}/update-name")
    public String updateBuildName(@PathVariable Integer buildId,
                                  @RequestParam String buildName,
                                  RedirectAttributes redirectAttributes) {
        buildService.updateBuildName(buildId, buildName);
        redirectAttributes.addFlashAttribute("successMessage", "Build name updated.");
        return "redirect:/builds";
    }

    @PostMapping("/builds/{buildId}/update-motorcycle")
    public String updateBuildMotorcycle(@PathVariable Integer buildId,
                                        @RequestParam Integer motorcycleId,
                                        RedirectAttributes redirectAttributes) {
        buildService.updateBuildMotorcycle(buildId, motorcycleId);
        redirectAttributes.addFlashAttribute("successMessage", "Build motorcycle updated.");
        return "redirect:/builds";
    }

    @PostMapping("/builds/{buildId}/delete")
    public String deleteBuild(@PathVariable Integer buildId,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        BuildService.DeletedBuildSnapshot deletedBuild = buildService.deleteBuild(buildId);

        session.setAttribute("lastDeletedBuild", deletedBuild);

        redirectAttributes.addFlashAttribute(
                "deletedBuildMessage",
                "Deleted " + deletedBuild.getBuildName() + "."
        );

        return "redirect:/builds";
    }

    @PostMapping("/builds/undo-delete")
    public String undoDeleteBuild(HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        BuildService.DeletedBuildSnapshot deletedBuild =
                (BuildService.DeletedBuildSnapshot) session.getAttribute("lastDeletedBuild");

        if (deletedBuild == null) {
            redirectAttributes.addFlashAttribute("successMessage", "There is no deleted build to restore.");
            return "redirect:/builds";
        }

        Build restoredBuild = buildService.restoreDeletedBuild(deletedBuild);
        session.removeAttribute("lastDeletedBuild");

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Restored " + restoredBuild.getBuildName() + "."
        );

        return "redirect:/builds";
    }

    @PostMapping("/builds/{buildId}/parts/{partId}/add")
    public String addPartToBuild(@PathVariable Integer buildId,
                                 @PathVariable Integer partId,
                                 HttpServletRequest request,
                                 RedirectAttributes redirectAttributes) {
        String partName = buildService.addPartToBuild(buildId, partId);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Added " + partName + " to build."
        );

        String referer = request.getHeader("Referer");

        if (referer != null && referer.contains("/parts")) {
            String redirectUrl = referer.split("#")[0];
            return "redirect:" + redirectUrl + "#part-" + partId;
        }

        return "redirect:/builds/" + buildId;
    }

    @PostMapping("/builds/{buildId}/parts/{buildPartId}/status")
    public String updateStatus(@PathVariable Integer buildId,
                               @PathVariable Integer buildPartId,
                               @RequestParam String status,
                               RedirectAttributes redirectAttributes) {
        buildService.updateBuildPartStatus(buildId, buildPartId, status);
        redirectAttributes.addFlashAttribute("successMessage", "Part status updated.");
        return "redirect:/builds/" + buildId;
    }

    @PostMapping("/builds/{buildId}/parts/{buildPartId}/remove")
    public String removePart(@PathVariable Integer buildId,
                             @PathVariable Integer buildPartId,
                             @RequestParam(required = false) Integer redirectPartId,
                             HttpServletRequest request,
                             RedirectAttributes redirectAttributes) {
        String partName = buildService.removePartFromBuild(buildId, buildPartId);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Removed " + partName + " from build."
        );

        String referer = request.getHeader("Referer");

        if (referer != null && referer.contains("/parts")) {
            String redirectUrl = referer.split("#")[0];

            if (redirectPartId != null) {
                return "redirect:" + redirectUrl + "#part-" + redirectPartId;
            }

            return "redirect:" + redirectUrl;
        }

        return "redirect:/builds/" + buildId;
    }
}