package jm.homework.pp_3_1_1_boot_2.service;

import jm.homework.pp_3_1_1_boot_2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class SecurityServiceImpl implements SecurityService {

    private AuthenticationManager authenticationManager;
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private UserDetailsService userDetailsService;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    @Autowired
    public void setAuthenticationManagerBuilder(AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }
    @Autowired
    public void setUserDetailsService(@Qualifier("userDetailsServiceImpl")
                                      UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Override
    public void autoLogin(User user) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
//
//        authenticationManager.authenticate(authenticationToken);
//
//        if (authenticationToken.isAuthenticated()) {
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

}
