package jm.homework.pp_3_1_1_boot_2.model;

import jm.homework.pp_3_1_1_boot_2.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PreparedRoles {

    RoleDao roleDao;
    private Set<String> allRoles;
    private Set<String> actualRoles;

    @NotEmpty(message = "email should not be empty!")
    @Email
    private String email;

    @NotEmpty(message = "Password should not be empty!")
    private String password;

    @NotEmpty(message = "name should not be empty!")
    private String name; // уникальное значение

    @Column(name = "age")
    private int age;

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public Set<String> getAllRoles() {
        return allRoles;
    }
    public void setAllRoles(Set<Role> allRolesInDb) {
        this.allRoles = allRolesInDb.stream().map(Role::getRole).collect(Collectors.toSet());
    }

    public Set<String> getActualRoles() {
        return actualRoles;
    }
    public void setActualRoles(Set<String> actualRoles) {
        this.actualRoles = actualRoles;
    }

    public void setUserRoles(User user) {
        this.actualRoles = user.getRoles().stream().map(Role::getRole).collect(Collectors.toSet());
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public boolean isNotErrorsTest() {
        if (this.email.isEmpty() || this.password.isEmpty()) {
            return false;
        }
        return  true;
    }

    public String getMessageIfPasswordEmpty() {
        if (this.password.isEmpty()) {
            return  "Password should not be empty!";
        }
        return null;
    }

    public String getMessageIfEmailEmpty() {
        if (this.email.isEmpty()) {
            return  "email should not be empty!";
        }
        return null;
    }




}
