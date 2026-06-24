package io.project.poseiden.controller;

import io.project.poseiden.model.BidList;
import io.project.poseiden.service.interfaces.BidListService;
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
public class BidListController {

    private final BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        model.addAttribute("bidLists", bidListService.findAll());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/add";
        }
        try {
            bidListService.save(bid);
            return "redirect:/bidList/list";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            return "bidList/add";
        }
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        try {
            BidList bidList = bidListService.getById(id);
            model.addAttribute("bidList", bidList);
            return "bidList/update";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            model.addAttribute("bidLists", bidListService.findAll());
            return "bidList/list";
        }
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Long id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/update";
        }
        try {
            bidListService.update(bidList);
            model.addAttribute("bidLists", bidListService.findAll());
            return "redirect:/bidList/list";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            return "bidList/update";
        }
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Long id, Model model) {
        try {
            bidListService.deleteById(id);
            return "redirect:/bidList/list";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            model.addAttribute("bidLists", bidListService.findAll());
            return "bidList/list";
        }

    }
}
