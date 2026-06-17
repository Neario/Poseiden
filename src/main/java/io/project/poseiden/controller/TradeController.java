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

@Controller
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        model.addAttribute("trades", tradeService.findAll());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade trade) {
        return "trade/add";
    }

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

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Trade trade = tradeService.getById(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/update";
        }
        try {
            trade.update(trade);
            return "redirect:/trade/list";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            return "trade/update";
        }
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Long id, Model model) {
        tradeService.deleteById(id);
        return "redirect:/trade/list";
    }
}
