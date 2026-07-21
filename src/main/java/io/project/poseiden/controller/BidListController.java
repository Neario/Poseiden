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

/**
 * Controller for web requests link to {@link BidList} entities.
 * <p>
 * endpoints : create, update, and delete for BidList.
 * business logic to {@link BidListService}.
 */
@Controller
@RequiredArgsConstructor
public class BidListController {

    private final BidListService bidListService;

    /**
     * Show the list of all BidList entries.
     *
     * @param model pass data to the view
     * @return the view name displaying the list of BidList
     */
    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        model.addAttribute("bidLists", bidListService.findAll());
        return "bidList/list";
    }

    /**
     * Show the form used to create a new BidList.
     *
     * @param bid an empty {@link BidList} link to the form
     * @return the view name displaying the creation form
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    /**
     * Validates and saves a new BidList entry submitted from the creation form.
     * <p>
     * @param bid the BidList entry to validate and save
     * @param result the result of the validation with any errors
     * @param model the model used to pass data to the view
     * @return a redirect to the BidList list page on success, or the form view on failure
     */
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

    /**
     * Show the form used to update an existing BidList entry.
     * <p>
     * @param id identifier of the BidList entry to update
     * @param model the model used to pass data to the view
     * @return the update form, or the view list of BidList if the entry is not found
     */
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

    /**
     * Validates and applies updates to an existing BidList.
     * <p>
     * @param id identifier of the BidList entry to update
     * @param bidList the BidList entry with the updated data
     * @param result the result of the validation, with any errors
     * @param model the model used to pass data to the view
     * @return a redirect to the BidList list page on success, or the update form view on failure
     */
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

    /**
     * Deletes an existing BidList.
     * <p>
     * @param id identifier of the BidList entry to delete
     * @param model the model used to pass data to the view
     * @return a redirect to the BidList list page on success, or the list view on failure
     */
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
