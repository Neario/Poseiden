package io.project.poseiden.controller;

import io.project.poseiden.model.Trade;
import io.project.poseiden.service.interfaces.TradeService;
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
 * Controller for web requests link to {@link Trade} entities.
 * <p>
 * endpoints : create, update, and delete for Trade.
 * business logic to {@link TradeService}.
 */
@Controller
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    /**
     * Show the list of all Trade entries.
     *
     * @param model pass data to the view
     * @return the view name displaying the list of Trade
     */
    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        model.addAttribute("trades", tradeService.findAll());
        return "trade/list";
    }

    /**
     * Show the form used to create a new Trade.
     *
     * @param trade an empty {@link Trade} link to the form
     * @return the view name displaying the creation form
     */
    @GetMapping("/trade/add")
    public String addUser(Trade trade) {
        return "trade/add";
    }

    /**
     * Validates and saves a new Trade entry submitted from the creation form.
     * <p>
     * @param trade the Trade entry to validate and save
     * @param result the result of the validation with any errors
     * @param model the model used to pass data to the view
     * @return a redirect to the Trade list page on success, or the form view on failure
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/add";
        }
        try {
            tradeService.save(trade);
            model.addAttribute("trades", tradeService.findAll());
            return "trade/list";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            return "trade/add";
        }
    }

    /**
     * Show the form used to update an existing Trade entry.
     * <p>
     * @param id identifier of the Trade entry to update
     * @param model the model used to pass data to the view
     * @return the update form, or the view list of Trade if the entry is not found
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        try {
            Trade trade = tradeService.getById(id);
            model.addAttribute("trade", trade);
            return "trade/update";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            model.addAttribute("trade", tradeService.findAll());
            return "trade/list";
        }

    }

    /**
     * Validates and applies updates to an existing Trade.
     * <p>
     * @param id identifier of the Trade entry to update
     * @param trade the Trade entry with the updated data
     * @param result the result of the validation, with any errors
     * @param model the model used to pass data to the view
     * @return a redirect to the Trade list page on success, or the update form view on failure
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/update";
        }
        try {
            tradeService.update(trade);
            return "redirect:/trade/list";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            return "trade/update";
        }
    }

    /**
     * Deletes an existing Trade.
     * <p>
     * @param id identifier of the Trade entry to delete
     * @param model the model used to pass data to the view
     * @return a redirect to the Trade list page on success, or the list view on failure
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Long id, Model model) {
        try {
            tradeService.deleteById(id);
            return "redirect:/trade/list";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            model.addAttribute("trade", tradeService.findAll());
            return "trade/list";
        }
    }
}
