package io.project.poseiden.controller;

import io.project.poseiden.model.CurvePoint;
import io.project.poseiden.service.interfaces.CurvePointService;
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
 * Controller for web requests link to {@link CurvePoint} entities.
 * <p>
 * endpoints : create, update, and delete for CurvePoint.
 * business logic to {@link CurvePointService}.
 */
@Controller
@RequiredArgsConstructor
public class CurveController {

    private final CurvePointService curvePointService;

    /**
     * Show the list of all CurvePoint entries.
     *
     * @param model pass data to the view
     * @return the view name displaying the list of CurvePoint
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        model.addAttribute("curvePoints", curvePointService.findAll());
        return "curvePoint/list";
    }

    /**
     * Show the form used to create a new CurvePoint.
     *
     * @param curvePoint an empty {@link CurvePoint} link to the form
     * @return the view name displaying the creation form
     */
    @GetMapping("/curvePoint/add")
    public String addCurveForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    /**
     * Validates and saves a new CurvePoint entry submitted from the creation form.
     * <p>
     * If validation errors the form is reload and added errors to the view
     *
     * @param curvePoint the CurvePoint entry to validate and save
     * @param result the result of the validation with any errors
     * @param model the model used to pass data to the view
     * @return a redirect to the CurvePoint page on success, or the form view on failure
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/add";
        }
        try {
            curvePointService.save(curvePoint);
            model.addAttribute("curvePoints", curvePointService.findAll());
            return "curvePoint/list";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            return "curvePoint/add";
        }
    }

    /**
     * Show the form used to update an existing CurvePoint entry.
     * <p>
     * @param id identifier of the CurvePoint entry to update
     * @param model the model used to pass data to the view
     * @return the update form, or the view list of CurvePoint if the entry is not found
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        try {
            CurvePoint curvePoint = curvePointService.getById(id);
            model.addAttribute("curvePoint", curvePoint);
            return "curvePoint/update";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            model.addAttribute("curvePoints", curvePointService.findAll());
            return "curvePoint/list";
        }
    }

    /**
     * Validates and applies updates to an existing CurvePoint.
     * <p>
     * @param id identifier of the CurvePoint entry to update
     * @param curvePoint the CurvePoint entry with the updated data
     * @param result the result of the validation, with any errors
     * @param model the model used to pass data to the view
     * @return a redirect to the CurvePoint list page on success, or the update form view on failure
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurve(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/update";
        }
        try {
            curvePointService.update(curvePoint);
            return "redirect:/curvePoint/list";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            return "curvePoint/update";
        }

    }

    /**
     * Deletes an existing CurvePoint.
     * <p>
     * @param id identifier of the CurvePoint entry to delete
     * @param model the model used to pass data to the view
     * @return a redirect to the CurvePoint list page on success, or the list view on failure
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurve(@PathVariable("id") Long id, Model model) {
        try {
            curvePointService.deleteById(id);
            return "redirect:/curvePoint/list";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            model.addAttribute("curvePoints", curvePointService.findAll());
            return "curvePoint/list";
        }
    }
}
