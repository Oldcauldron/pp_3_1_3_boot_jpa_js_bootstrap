package jm.homework.pp_3_1_1_boot_2.controller;

import jm.homework.pp_3_1_1_boot_2.model.User;
import jm.homework.pp_3_1_1_boot_2.service.RoleService;
import jm.homework.pp_3_1_1_boot_2.service.SecurityService;
import jm.homework.pp_3_1_1_boot_2.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeControllerNew {

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final RoleService roleService;
    private final SecurityService securityService;

    @Value("${other.value}")
    String value;

    public HomeControllerNew(@Qualifier("userDetailsServiceImpl")
                                  UserDetailsService userDetailsService,
                          UserService userService,
                          RoleService roleService,
                          SecurityService securityService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.roleService = roleService;
        this.securityService = securityService;
    }

    @GetMapping(value = "/")
    public String getHomePage(Model model) {
        return "redirect:/logincustom";
    }


    @GetMapping(value = "/logincustom")
    public String getLoginPage(@RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "logout", required = false) String logout,
                               Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "logincust";
    }

    @PostMapping("/registration-b")
    public String registrationB(@ModelAttribute("user") @Valid User user,
                          BindingResult bindingResult,
                          Model model) {
        String errorExist;
        if (bindingResult.hasErrors()) {
            return "admin_b";
        }
        if (userService.isExistingUserByEmail(user.getEmail())) {
            errorExist = "this email is already exist";
            model.addAttribute("errorExist", errorExist);
            return "admin_b";
        }
        userService.addUser(user);

        return String.format("redirect:/admin-b", user.getId());
    }


//    @GetMapping("/registration-b")
//    public String getRegisttrationFormB(@ModelAttribute("user") User user) {
//        return "admin_b";
//    }


//    @GetMapping("/registration")
//    public String getRegisterForm(@ModelAttribute("user") User user) {
//        return "/templates/archive/regForm.html";
//    }

//    @PostMapping("/registration")
//    public String regUser(@ModelAttribute("user") @Valid User user,
//                          BindingResult bindingResult,
//                          Model model) {
//        String errorExist;
//        if (bindingResult.hasErrors()) {
//            return "/templates/archive/regForm.html";
//        }
//        if (userService.isExistingUserByEmail(user.getEmail())) {
//            errorExist = "this email is already exist";
//            model.addAttribute("errorExist", errorExist);
//            return "/templates/archive/regForm.html";
//        }
//        userService.addUser(user);
//        securityService.autoLogin(user);
//
//        return String.format("redirect:/user/acc/%d", user.getId());
//    }



}
