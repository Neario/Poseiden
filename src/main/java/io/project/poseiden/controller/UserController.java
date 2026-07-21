package io.project.poseiden.controller;

import io.project.poseiden.model.User;
import io.project.poseiden.service.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller for web requests link to {@link User} entities.
 * <p>
 * endpoints : create, update, and delete for User.
 * business logic to {@link UserService}.
 */
@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Show the list of all User entries.
     *
     * @param model pass data to the view
     * @return the view name displaying the list of User
     */
    @RequestMapping("/user/list")
    public String home(Model model)
    {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    /**
     * Show the form used to create a new User.
     *
     * @param user an empty {@link User} link to the form
     * @return the view name displaying the creation form
     */
    @GetMapping("/user/add")
    public String addUser(User user) {
        return "user/add";
    }

    /**
     * Validates and saves a new User entry submitted from the creation form.
     * <p>
     * @param user the User entry to validate and save
     * @param result the result of the validation with any errors
     * @param model the model used to pass data to the view
     * @return a redirect to the User list page on success, or the form view on failure
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/add";
        }
        try {
            userService.save(user);
            model.addAttribute("users", userService.findAll());
            return "redirect:/user/list";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            return "user/add";
        }
    }

    /**
     * Show the form used to update an existing User entry.
     * <p>
     * @param id identifier of the User entry to update
     * @param model the model used to pass data to the view
     * @return the update form, or the view list of User if the entry is not found
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        try {
            User user = userService.getById(id);
            user.setPassword("");
            model.addAttribute("user", user);
            return "user/update";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            model.addAttribute("users", userService.findAll());
            return "user/list";
        }

    }

    /**
     * Validates and applies updates to an existing User.
     * <p>
     * @param id identifier of the User entry to update
     * @param user the User entry with the updated data
     * @param result the result of the validation, with any errors
     * @param model the model used to pass data to the view
     * @return a redirect to the User list page on success, or the update form view on failure
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            user.setId(id);
            userService.update(user);
            model.addAttribute("users", userService.findAll());
            return "redirect:/user/list";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            return "user/update";
        }
    }

    /**
     * Deletes an existing User.
     * <p>
     * @param id identifier of the User entry to delete
     * @param model the model used to pass data to the view
     * @return a redirect to the User list page on success, or the list view on failure
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        try {
            userService.deleteById(id);
            model.addAttribute("users", userService.findAll());
            return "redirect:/user/list";
        } catch (Exception exception) {
            model.addAttribute("errors", List.of(exception.getMessage()));
            model.addAttribute("users", userService.findAll());
            return "user/list";
        }

    }
}