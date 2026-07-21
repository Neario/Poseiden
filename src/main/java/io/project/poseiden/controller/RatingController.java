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

/**
 * Controller for web requests link to {@link Rating} entities.
 * <p>
 * endpoints : create, update, and delete for Rating.
 * business logic to {@link RatingService}.
 */
@Controller
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    /**
     * Show the list of all Rating entries.
     *
     * @param model pass data to the view
     * @return the view name displaying the list of Rating
     */
    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        model.addAttribute("ratings", ratingService.findAll());
        return "rating/list";
    }

    /**
     * Show the form used to create a new Rating.
     *
     * @param rating an empty {@link Rating} link to the form
     * @return the view name displaying the creation form
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    /**
     * Validates and saves a new Rating entry submitted from the creation form.
     * <p>
     * @param rating the Rating entry to validate and save
     * @param result the result of the validation with any errors
     * @param model the model used to pass data to the view
     * @return a redirect to the Rating list page on success, or the form view on failure
     */
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

    /**
     * Show the form used to update an existing Rating entry.
     * <p>
     * @param id identifier of the Rating entry to update
     * @param model the model used to pass data to the view
     * @return the update form, or the view list of Rating if the entry is not found
     */
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

    /**
     * Validates and applies updates to an existing Rating.
     * <p>
     * @param id identifier of the Rating entry to update
     * @param rating the Rating entry with the updated data
     * @param result the result of the validation, with any errors
     * @param model the model used to pass data to the view
     * @return a redirect to the Rating list page on success, or the update form view on failure
     */
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

    /**
     * Deletes an existing Rating.
     * <p>
     * @param id identifier of the Rating entry to delete
     * @param model the model used to pass data to the view
     * @return a redirect to the Rating list page on success, or the list view on failure
     */
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
