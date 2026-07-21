package io.project.poseiden.controller;

import io.project.poseiden.model.User;
import io.project.poseiden.service.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for authentication and access-restricted web requests.
 * <p>
 * Endpoints for the login page with secured page for user data,
 * and a custom error page for unauthorized access.
 */
@Controller
@RequestMapping("app")
@AllArgsConstructor
public class LoginController {

    private final CrudService<User> userService;

    /**
     * Show the login page.
     *
     * @return the {@link ModelAndView} for the login view
     */
    @GetMapping("login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    /**
     * Show a secured page listing all users.
     * <p>
     * Endpoint is only accessible to authenticated and authorized users.
     *
     * @return the {@link ModelAndView} for the user list view.
     */
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userService.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    /**
     * Show a custom error page for unauthorized access.
     *
     * @return the {@link ModelAndView} for the 403 error view.
     */
    @GetMapping("error")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }
}
