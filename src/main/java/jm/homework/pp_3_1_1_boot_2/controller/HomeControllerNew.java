package jm.homework.pp_3_1_1_boot_2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class HomeControllerNew {

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

    @GetMapping(value = "/main")
    public String getMainPage(Model model) {
        return "main";
    }

}
