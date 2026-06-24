package io.project.poseiden.controller;

import io.project.poseiden.model.Rating;
import io.project.poseiden.service.interfaces.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        model.addAttribute("ratings", ratingService.findAll());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/add";
        }
        try {
            ratingService.save(rating);
            model.addAttribute("ratings", ratingService.findAll());
            return "rating/list";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            return "rating/add";
        }
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        try {
            Rating rating = ratingService.getById(id);
            model.addAttribute("rating", rating);
            return "rating/update";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            model.addAttribute("ratingPoints", ratingService.findAll());
            return "rating/list";
        }

    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/update";
        }
        try {
            ratingService.update(rating);
            return "redirect:/rating/list";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            return "rating/update";
        }
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Long id, Model model) {
        try {
            ratingService.deleteById(id);
            return "redirect:/rating/list";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            model.addAttribute("ratingPoints", ratingService.findAll());
            return "rating/list";
        }
    }
}
