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

@Controller
@RequiredArgsConstructor
public class CurveController {

    private final CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        model.addAttribute("curvePoints", curvePointService.findAll());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurveForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

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
