package jm.homework.pp_3_1_1_boot_2.controller.archive;

import jm.homework.pp_3_1_1_boot_2.model.Role;
import jm.homework.pp_3_1_1_boot_2.model.User;
import jm.homework.pp_3_1_1_boot_2.service.RoleService;
import jm.homework.pp_3_1_1_boot_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

//@Controller
//@RequestMapping("")
public class UserController {

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(@Qualifier("userDetailsServiceImpl")
                                  UserDetailsService userDetailsService,
                          UserService userService,
                          RoleService roleService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.roleService = roleService;
    }


    // --------------- CONTROLLERS ------------------------------

    @GetMapping(value = "/admin")
    public String getAdminPage(Model model) {
        List<User> listUsers = userService.allUsers();
        model.addAttribute("listUsers", listUsers);
        return "/templates/archive/admin.html";
    }

    @PreAuthorize("@securityServiceImpl.preauthorizeFunc(#id, authentication)")
    @GetMapping(value = "/user/acc/{id}")
    public String getPersonal(@PathVariable("id") int id, Model model) {
        User user = userService.showById(id);
        model.addAttribute("user", user);
        return "/templates/archive/user.html";
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable("id") long id, Model model) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "/user/{id}")
    public String editUserFormSecond(@PathVariable("id") long id,
                                     Model model){
        if(!model.containsAttribute("user")){
            User user = userService.showById(id);
            model.addAttribute("user", user);
            Set<Role> rolesAll = roleService.getAllRoles();
            model.addAttribute("rolesAll", rolesAll);
        }
        return "/templates/archive/editUserSecond.html";
    }

    @PatchMapping(value = "/user/{id}")
    public String getEditUserSecond(@ModelAttribute("user") @Valid User user,
                                    BindingResult bindingResult,
                                    @PathVariable("id") long id,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {
        boolean err = false;
        User oldUser = userService.showById(id);

        if ((!oldUser.getEmail().equals(user.getEmail()))
                && userService.isExistingUserByEmail(user.getEmail())) {
            redirectAttributes.addFlashAttribute("errorExist", "this email is already exist");
            err = true;
        }
        if (err || bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("rolesAll", roleService.getAllRoles());
            return String.format("redirect:/user/%d", user.getId());
        }
        userService.updateUser(user);
        return String.format("redirect:/user/acc/%d", user.getId());
    }

}
