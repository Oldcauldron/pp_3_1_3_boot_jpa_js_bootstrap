package jm.homework.pp_3_1_3_boot_js.service;

import jm.homework.pp_3_1_3_boot_js.model.User;

import java.util.List;

public interface UserService {
    List<User> allUsers();
    User findByEmail(String email);
    void addUser(User user);
    void updateUser(User user);
    boolean isExistingUser(User user);
    boolean isExistingUserByEmail(String email);
    boolean isExistingUserById(long id);
    User showById(long id);
    void deleteUserById(long id);
}
