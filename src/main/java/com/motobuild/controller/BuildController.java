package com.motobuild.controller;

import com.motobuild.model.Build;
import com.motobuild.repository.MotorcycleRepository;
import com.motobuild.service.AuthService;
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
    private final AuthService authService;

    public BuildController(BuildService buildService,
                           MotorcycleRepository motorcycleRepository,
                           AuthService authService) {
        this.buildService = buildService;
        this.motorcycleRepository = motorcycleRepository;
        this.authService = authService;
    }

    @GetMapping("/builds")
    public String showBuilds(@RequestParam(required = false) Integer motorcycleId,
                             HttpSession session,
                             Model model) {
        Integer userId = authService.getLoggedInUserId(session);

        if (userId == null) {
            return "redirect:/login";
        }

        model.addAttribute("builds", buildService.getBuildsForUser(userId));
        model.addAttribute("motorcycles", motorcycleRepository.findAll());
        model.addAttribute("selectedMotorcycleId", motorcycleId);
        model.addAttribute("loggedInUser", authService.getLoggedInUser(session));

        return "builds";
    }

    @PostMapping("/builds")
    public String createBuild(@RequestParam String buildName,
                              @RequestParam Integer motorcycleId,
                              @RequestParam(required = false) String description,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        Integer userId = authService.getLoggedInUserId(session);

        if (userId == null) {
            return "redirect:/login";
        }

        Build build = buildService.createBuild(userId, buildName, motorcycleId, description);
        redirectAttributes.addFlashAttribute("successMessage", "Build created successfully.");

        return "redirect:/builds/" + build.getBuildId();
    }

    @GetMapping("/builds/{buildId}")
    public String showBuildDetails(@PathVariable Integer buildId,
                                   HttpSession session,
                                   Model model) {
        Integer userId = authService.getLoggedInUserId(session);

        if (userId == null) {
            return "redirect:/login";
        }

        Build build = buildService.getBuild(buildId, userId);

        model.addAttribute("build", build);
        model.addAttribute("totalCost", buildService.calculateTotalCost(build));
        model.addAttribute("cartCost", buildService.calculateCartCost(build));
        model.addAttribute("warnings", buildService.getWarnings(buildId, userId));
        model.addAttribute("loggedInUser", authService.getLoggedInUser(session));

        return "build-details";
    }

    @PostMapping("/builds/{buildId}/update-name")
    public String updateBuildName(@PathVariable Integer buildId,
                                  @RequestParam String buildName,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        Integer userId = authService.getLoggedInUserId(session);

        if (userId == null) {
            return "redirect:/login";
        }

        buildService.updateBuildName(userId, buildId, buildName);
        redirectAttributes.addFlashAttribute("successMessage", "Build name updated.");

        return "redirect:/builds";
    }

    @PostMapping("/builds/{buildId}/update-motorcycle")
    public String updateBuildMotorcycle(@PathVariable Integer buildId,
                                        @RequestParam Integer motorcycleId,
                                        HttpSession session,
                                        RedirectAttributes redirectAttributes) {
        Integer userId = authService.getLoggedInUserId(session);

        if (userId == null) {
            return "redirect:/login";
        }

        buildService.updateBuildMotorcycle(userId, buildId, motorcycleId);
        redirectAttributes.addFlashAttribute("successMessage", "Build motorcycle updated.");

        return "redirect:/builds";
    }

    @PostMapping("/builds/{buildId}/delete")
    public String deleteBuild(@PathVariable Integer buildId,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        Integer userId = authService.getLoggedInUserId(session);

        if (userId == null) {
            return "redirect:/login";
        }

        BuildService.DeletedBuildSnapshot deletedBuild = buildService.deleteBuild(userId, buildId);
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
        Integer userId = authService.getLoggedInUserId(session);

        if (userId == null) {
            return "redirect:/login";
        }

        BuildService.DeletedBuildSnapshot deletedBuild =
                (BuildService.DeletedBuildSnapshot) session.getAttribute("lastDeletedBuild");

        if (deletedBuild == null) {
            redirectAttributes.addFlashAttribute("successMessage", "There is no deleted build to restore.");
            return "redirect:/builds";
        }

        Build restoredBuild = buildService.restoreDeletedBuild(userId, deletedBuild);
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
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        Integer userId = authService.getLoggedInUserId(session);

        if (userId == null) {
            return "redirect:/login";
        }

        String partName = buildService.addPartToBuild(userId, buildId, partId);

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
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        Integer userId = authService.getLoggedInUserId(session);

        if (userId == null) {
            return "redirect:/login";
        }

        buildService.updateBuildPartStatus(userId, buildId, buildPartId, status);
        redirectAttributes.addFlashAttribute("successMessage", "Part status updated.");

        return "redirect:/builds/" + buildId;
    }

    @PostMapping("/builds/{buildId}/parts/{buildPartId}/remove")
    public String removePart(@PathVariable Integer buildId,
                             @PathVariable Integer buildPartId,
                             @RequestParam(required = false) Integer redirectPartId,
                             HttpServletRequest request,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        Integer userId = authService.getLoggedInUserId(session);

        if (userId == null) {
            return "redirect:/login";
        }

        String partName = buildService.removePartFromBuild(userId, buildId, buildPartId);

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