package jm.homework.pp_3_1_1_boot_2.service;

import jm.homework.pp_3_1_1_boot_2.model.archive.PreparedRoles;
import jm.homework.pp_3_1_1_boot_2.model.User;

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
//    void deleteUser(User user);
//    void updateUserOfPreparedRoles(User user, PreparedRoles preparedRoles);
//    boolean isExistingUserByName(String name);
}
