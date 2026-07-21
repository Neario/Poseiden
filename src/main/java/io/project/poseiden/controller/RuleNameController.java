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

/**
 * Controller for web requests link to {@link RuleName} entities.
 * <p>
 * endpoints : create, update, and delete for RuleName.
 * business logic to {@link RuleNameService}.
 */
@Controller
@RequiredArgsConstructor
public class RuleNameController {

    private final RuleNameService ruleNameService;

    /**
     * Show the list of all RuleName entries.
     *
     * @param model pass data to the view
     * @return the view name displaying the list of RuleName
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "ruleName/list";
    }

    /**
     * Show the form used to create a new RuleName.
     *
     * @param ruleName an empty {@link RuleName} link to the form
     * @return the view name displaying the creation form
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
        return "ruleName/add";
    }

    /**
     * Validates and saves a new RuleName entry submitted from the creation form.
     * <p>
     * @param ruleName the RuleName entry to validate and save
     * @param result the result of the validation with any errors
     * @param model the model used to pass data to the view
     * @return a redirect to the RuleName list page on success, or the form view on failure
     */
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

    /**
     * Show the form used to update an existing RuleName entry.
     * <p>
     * @param id identifier of the RuleName entry to update
     * @param model the model used to pass data to the view
     * @return the update form, or the view list of RuleName if the entry is not found
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        try {
            RuleName ruleName = ruleNameService.getById(id);
            model.addAttribute("ruleName", ruleName);
            return "ruleName/update";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            model.addAttribute("ruleNames", ruleNameService.findAll());
            return "ruleName/list";
        }
    }

    /**
     * Validates and applies updates to an existing RuleName.
     * <p>
     * @param id identifier of the RuleName entry to update
     * @param ruleName the RuleName entry with the updated data
     * @param result the result of the validation, with any errors
     * @param model the model used to pass data to the view
     * @return a redirect to the RuleName list page on success, or the update form view on failure
     */
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

    /**
     * Deletes an existing RuleName.
     * <p>
     * @param id identifier of the RuleName entry to delete
     * @param model the model used to pass data to the view
     * @return a redirect to the RuleName list page on success, or the list view on failure
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Long id, Model model) {
        try {
            ruleNameService.deleteById(id);
            return "redirect:/ruleName/list";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            model.addAttribute("ruleName", ruleNameService.findAll());
            return "ruleName/list";
        }
    }
}
