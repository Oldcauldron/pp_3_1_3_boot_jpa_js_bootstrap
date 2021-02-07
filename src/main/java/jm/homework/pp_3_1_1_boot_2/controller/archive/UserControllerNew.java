//package jm.homework.pp_3_1_1_boot_2.controller;
//
//import jm.homework.pp_3_1_1_boot_2.model.User;
//import jm.homework.pp_3_1_1_boot_2.service.RoleService;
//import jm.homework.pp_3_1_1_boot_2.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Controller
//@RequestMapping("")
//public class UserControllerNew {
//
//    private final UserDetailsService userDetailsService;
//    private final UserService userService;
//    private final RoleService roleService;
//
//    @Autowired
//    public UserControllerNew(@Qualifier("userDetailsServiceImpl")
//                                  UserDetailsService userDetailsService,
//                          UserService userService,
//                          RoleService roleService) {
//        this.userDetailsService = userDetailsService;
//        this.userService = userService;
//        this.roleService = roleService;
//    }


    // --------------- CONTROLLERS ------------------------------

//    @GetMapping(value = "/main")
//    public String getMainPage(Model model) {
////        allNecessaryAddingToModel(model);
//        return "main";
//    }


//    private Model allNecessaryAddingToModel(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        Set<String> roles = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
//
//        String email;
//        Object principal = authentication.getPrincipal();
//        if (principal instanceof UserDetails) {
//            email = ((UserDetails)principal).getUsername();
//        } else {
//            email = principal.toString();
//        }
//
//        User user = (User) userService.findByEmail(email);
//        List<User> listUsers = userService.allUsers();
//
//        model.addAttribute("user", new User());
//        model.addAttribute("rolesAll", roleService.getAllRoles());
//        model.addAttribute("listUsers", listUsers);
//        model.addAttribute("email", email);
//        model.addAttribute("roles", roles);
//        model.addAttribute("authUser", user);
//
//        return model;
//    }

//}
