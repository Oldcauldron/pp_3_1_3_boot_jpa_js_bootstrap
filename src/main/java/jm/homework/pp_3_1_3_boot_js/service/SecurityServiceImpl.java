package jm.homework.pp_3_1_3_boot_js.service;

import jm.homework.pp_3_1_3_boot_js.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class SecurityServiceImpl implements SecurityService {

    private UserService userService;
    private UserDetailsService userDetailsService;

    @Autowired
    public SecurityServiceImpl(UserService userService,
                               @Qualifier("userDetailsServiceImpl")
                               UserDetailsService userDetailsService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public User getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }

        if (userService.isExistingUserByEmail(email)) {
            return (User) userDetailsService.loadUserByUsername(email);
        }
        return new User();
    }
}
