package jm.homework.pp_3_1_1_boot_2.controller;

import jm.homework.pp_3_1_1_boot_2.model.PreparedRoles;
import jm.homework.pp_3_1_1_boot_2.model.Role;
import jm.homework.pp_3_1_1_boot_2.model.User;
import jm.homework.pp_3_1_1_boot_2.service.RoleService;
import jm.homework.pp_3_1_1_boot_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@RequestMapping("")
public class UserController {

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final RoleService roleService;

//    @Autowired
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
        return "admin";
    }

    @GetMapping(value = "/user/acc/{id}")
    public String getPersonal(@PathVariable("id") int id, Model model) {
        User user = userService.showById(id);
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping(value = "/user/{id}")
    public String editUserForm(@PathVariable("id") long id,
                               Model model){
        User user = userService.showById(id);

        if(!model.containsAttribute("preparedRoles")){
            PreparedRoles preparedRoles = getNewPreparedRole(user);
            model.addAttribute("preparedRoles", preparedRoles);
        }
        model.addAttribute("user", user);
        return "editUser";
    }

    @PatchMapping(value = "/user/{id}")
    public String getEditUser(@ModelAttribute("preparedRoles") @Valid PreparedRoles preparedRoles,
                              BindingResult bindingResult,
                              @PathVariable("id") long id,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        User user = userService.showById(id);
        boolean err = false;


        if ((!user.getEmail().equals(preparedRoles.getEmail()))
                && userService.isExistingUserByName(preparedRoles.getEmail())) {
            redirectAttributes.addFlashAttribute("errorExist", "this email is already exist");
            err = true;
        }
        if (err || bindingResult.hasErrors()) {
            preparedRoles.setAllRoles(roleService.getAllRoles());
            preparedRoles.setUserRoles(user);
            redirectAttributes.addFlashAttribute("preparedRoles", preparedRoles);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.preparedRoles", bindingResult);
            return String.format("redirect:/user/%d", user.getId());
        }


        userService.updateUserOfPreparedRoles(user, preparedRoles);
        return String.format("redirect:/user/acc/%d", user.getId());
    }


    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable("id") long id, Model model) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }




    @GetMapping(value = "/user/second/{id}")
    public String editUserFormSecond(@PathVariable("id") long id,
                               Model model){
        User user = userService.showById(id);

//        if(!model.containsAttribute("user")){
//            model.addAttribute("user", user);
//        }
        Set<Role> rolesAll = roleService.getAllRoles();
        model.addAttribute("rolesAll", rolesAll);
        model.addAttribute("user", user);
        return "editUserSecond";
    }

    @PatchMapping(value = "/user/second/{id}")
    public String getEditUserSecond(@ModelAttribute("user") @Valid User user,
                              BindingResult bindingResult,
                              @PathVariable("id") long id,
                              Model model,
                              RedirectAttributes redirectAttributes) {
//        User user = userService.showById(id);
        boolean err = false;




//        if ((!user.getEmail().equals(preparedRoles.getEmail()))
//                && userService.isExistingUserByName(preparedRoles.getEmail())) {
//            redirectAttributes.addFlashAttribute("errorExist", "this email is already exist");
//            err = true;
//        }
//        if (err || bindingResult.hasErrors()) {
//            preparedRoles.setAllRoles(roleService.getAllRoles());
//            preparedRoles.setUserRoles(user);
//            redirectAttributes.addFlashAttribute("preparedRoles", preparedRoles);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.preparedRoles", bindingResult);
//            return String.format("redirect:/user/%d", user.getId());
//        }

//        userService.updateUserOfPreparedRoles(user, preparedRoles);
        userService.updateUser(user);
        return String.format("redirect:/user/acc/%d", user.getId());
    }







    private PreparedRoles getNewPreparedRole(User user) {
        PreparedRoles preparedRoles = new PreparedRoles();
        preparedRoles.setAllRoles(roleService.getAllRoles());
        preparedRoles.setUserRoles(user);
        preparedRoles.setEmail(user.getEmail());
        preparedRoles.setName(user.getName());
        preparedRoles.setAge(user.getAge());
        preparedRoles.setPassword(user.getPassword());
        return preparedRoles;
    }




}
