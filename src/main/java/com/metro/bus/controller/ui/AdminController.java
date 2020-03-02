package com.metro.bus.controller.ui;

import com.metro.bus.controller.command.AdminSignupFormCommand;
import com.metro.bus.dto.model.user.UserDto;
import com.metro.bus.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class AdminController {

    private final UserService userService;

    @GetMapping(value = {"/", "/login"})
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping(value = {"/logout"})
    public String logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:login";
    }

    @GetMapping(value = "/signup")
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView("signup");
        modelAndView.addObject("adminSignupFormData", new AdminSignupFormCommand());
        return modelAndView;
    }

    @PostMapping(value = "/signup")
    public ModelAndView createNewAdmin(@Valid @ModelAttribute("adminSignupFormData") AdminSignupFormCommand adminSignupFormCommand, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("signup");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        } else {
            try {
                UserDto newUser = registerAdmin(adminSignupFormCommand);
            } catch (Exception exception) {
                bindingResult.rejectValue("email", "error.adminSignupFormCommand", exception.getMessage());
                return modelAndView;
            }
        }
        return new ModelAndView("login");
    }

    /**
     * Register a new user in the database
     *
     */
    private UserDto registerAdmin(@Valid AdminSignupFormCommand adminSignupRequest) {
        UserDto userDto = new UserDto()
                .setEmail(adminSignupRequest.getEmail())
                .setPassword(adminSignupRequest.getPassword())
                .setFirstName(adminSignupRequest.getFirstName())
                .setLastName(adminSignupRequest.getLastName())
                .setMobileNumber(adminSignupRequest.getMobileNumber())
                .setAdmin(true);
        UserDto admin = userService.signup(userDto); //register the admin

        return admin;
    }

}
