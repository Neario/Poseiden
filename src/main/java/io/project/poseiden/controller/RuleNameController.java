package io.project.poseiden.controller;

import io.project.poseiden.model.RuleName;
import io.project.poseiden.service.interfaces.RuleNameService;
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
public class RuleNameController {

    private final RuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/add";
        }
        try {
            ruleNameService.save(ruleName);
            model.addAttribute("ruleNames", ruleNameService.findAll());
            return "ruleName/list";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            return "ruleName/add";
        }
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        RuleName ruleName = ruleNameService.getById(id);
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/update";
        }
        try {
            ruleNameService.update(ruleName);
            return "redirect:/ruleName/list";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            return "ruleName/update";
        }
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Long id, Model model) {
        ruleNameService.deleteById(id);
        return "redirect:/ruleName/list";
    }
}
