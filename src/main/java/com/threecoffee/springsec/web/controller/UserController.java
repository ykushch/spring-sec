package com.threecoffee.springsec.web.controller;

import com.threecoffee.springsec.domain.User;
import com.threecoffee.springsec.repository.UserRepository;
import com.threecoffee.springsec.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @RequestMapping
    public ModelAndView list() {
        Iterable<User> users = this.userRepository.findAll();
        return new ModelAndView("users/list", "users", users);
    }

    @RequestMapping("/{id}")
    public ModelAndView view(@PathVariable("id") User user) {
        return new ModelAndView("users/view", "user", user);
    }

    @RequestMapping(params = "form", method = RequestMethod.POST)
    public String createForm(@ModelAttribute User user) {
        return "users/form";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView create(@Valid User user,
                               BindingResult result,
                               RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("users/form", "formErrors", result.getAllErrors());
        }

        user = userService.registerNewUser(user);
        redirect.addFlashAttribute("globalMessage", "Successfully created a new user");

        return new ModelAndView("redirect:/user/{user.id}", "user.id", user.getId());
    }

    @RequestMapping("/foo")
    public String foo() {
        throw new RuntimeException("Expected exception in controller");
    }

    @RequestMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        this.userRepository.delete(id);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
    public ModelAndView modifyForm(@PathVariable("id") User user) {
        return new ModelAndView("users/form", "user", user);
    }
}
