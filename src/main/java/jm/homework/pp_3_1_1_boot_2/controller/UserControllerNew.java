package jm.homework.pp_3_1_1_boot_2.controller;

import jm.homework.pp_3_1_1_boot_2.model.PreparedRoles;
import jm.homework.pp_3_1_1_boot_2.model.Role;
import jm.homework.pp_3_1_1_boot_2.model.User;
import jm.homework.pp_3_1_1_boot_2.service.RoleService;
import jm.homework.pp_3_1_1_boot_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("")
public class UserControllerNew {

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserControllerNew(@Qualifier("userDetailsServiceImpl")
                                  UserDetailsService userDetailsService,
                          UserService userService,
                          RoleService roleService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.roleService = roleService;
    }


    // --------------- CONTROLLERS ------------------------------
//
//    @GetMapping(value = "/admin")
//    public String getAdminPage(Model model) {
//        List<User> listUsers = userService.allUsers();
//        model.addAttribute("listUsers", listUsers);
//        return "admin";
//    }
//
//    @PreAuthorize("@securityServiceImpl.preauthorizeFunc(#id, authentication)")
//    @GetMapping(value = "/user/acc/{id}")
//    public String getPersonal(@PathVariable("id") int id, Model model) {
//        User user = userService.showById(id);
//        model.addAttribute("user", user);
//        return "user";
//    }
//
    @DeleteMapping(value = "/user-b/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        userService.deleteUserById(id);
        return "redirect:/admin-b";
    }

    @PatchMapping(value = "/user-b/{id}")
    public String getUserFormSecond(@ModelAttribute("user") User user,
                                    @PathVariable("id") long id,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {
        boolean err = false;
        User oldUser = userService.showById(id);

        boolean emailEmpty = user.getEmail().isEmpty();
        boolean nameEmpty = user.getName().isEmpty();
        boolean passwordEmpty = user.getPassword().isEmpty();

        if ((!oldUser.getEmail().equals(user.getEmail()))
                && userService.isExistingUserByEmail(user.getEmail())) {
            redirectAttributes.addFlashAttribute("errorExist", "this email is already exist");
            err = true;
        }

        if (err || emailEmpty || nameEmpty || passwordEmpty) {
            redirectAttributes.addFlashAttribute("emailEmpty", emailEmpty);
            redirectAttributes.addFlashAttribute("nameEmpty", nameEmpty);
            redirectAttributes.addFlashAttribute("passwordEmpty", passwordEmpty);
//            redirectAttributes.addFlashAttribute("rolesAll", roleService.getAllRoles());
            return String.format("redirect:/admin-b");
        }
        userService.updateUser(user);
        return String.format("redirect:/admin-b");
    }


    @GetMapping(value = "/admin-b")
    public String getAdminPage(Model model) {
        allNecessaryAddingToModel(model);
        return "admin_b";
    }

    @GetMapping(value = "/user-b/{id}")
    public String getUserPage(@PathVariable("id") long id, Model model) {
        allNecessaryAddingToModel(model);
        return "user_b";
    }


//    @PatchMapping("/user-b2/{id}")
//    public String getCommonUserEdit(@PathVariable("id") long id,
////                                    @RequestParam(value = "id", required = false) long id,
////                                    @RequestParam(value = "email", required = false) String email,
////                                    @RequestParam(value = "name", required = false) String name,
////                                    @RequestParam(value = "age", required = false) String age,
////                                    @RequestParam(value = "roles", required = false) List<Role> roles,
//                                    @ModelAttribute("user") User user,
//                                    Model model
//                                    ) {
//        System.out.println(user.getId());
//        System.out.println(user.getEmail());
//        System.out.println(user.getName());
//        System.out.println(user.getAge());
//        System.out.println(user.getRoles());
//        System.out.println(user.getPassword());
//
//        return "redirect:/admin-b";
//    }

    private String getEmailByAuthenticatedUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        }
        return principal.toString();
    }

    private Model allNecessaryAddingToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        String email;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }

        User user = (User) userService.findByEmail(email);
        List<User> listUsers = userService.allUsers();

        model.addAttribute("user", new User());
        model.addAttribute("rolesAll", roleService.getAllRoles());
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("email", email);
        model.addAttribute("roles", roles);
        model.addAttribute("authUser", user);

        return model;
    }



}
