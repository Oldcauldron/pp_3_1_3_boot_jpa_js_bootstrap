package jm.homework.pp_3_1_1_boot_2.controller;

import jm.homework.pp_3_1_1_boot_2.exception_handler.NoUserWithSuchIdException;
import jm.homework.pp_3_1_1_boot_2.exception_handler.UserIncorrectData;
import jm.homework.pp_3_1_1_boot_2.exception_handler.UserWithSuchEmailExist;
import jm.homework.pp_3_1_1_boot_2.model.User;
import jm.homework.pp_3_1_1_boot_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public User apiGetAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }
        return userService.findByEmail(email);
    }

    @GetMapping("/users")
    public List<User> apiGetAllUsers() {
        return userService.allUsers();
    }

    @GetMapping("/users/{id}")
    public User apiGetOneUser(@PathVariable("id") long id) {
        return userService.showById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<UserIncorrectData> apiAddNewUser(@Valid @RequestBody User user,
                                                BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            String error = bindingResult.getFieldErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining("; "));
            return new ResponseEntity<>(new UserIncorrectData(error), HttpStatus.BAD_REQUEST);
        }
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserIncorrectData> apiUpdateUser(@PathVariable("id") long id,
                              @Valid @RequestBody User user,
                              BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            String error = bindingResult.getFieldErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining("; "));
            return new ResponseEntity<>(new UserIncorrectData(error), HttpStatus.BAD_REQUEST);
        }
        try {
            userService.addUser(user);
        } catch (DataIntegrityViolationException e) {
//            System.out.println("\n\n\n 11111111111 \n\n\n");
            throw new UserWithSuchEmailExist("Not updated, user with such email exist");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public String apiDeleteUser(@PathVariable("id") long id) {
        try {
            userService.deleteUserById(id);
            return String.format("User with id = %d was deleted", id);
        } catch (EmptyResultDataAccessException e) {
            String message = String.format("User with id = %d , do not exist", id);
            throw new NoUserWithSuchIdException(message);
        }
    }


}