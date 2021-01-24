package jm.homework.pp_3_1_1_boot_2.service;

import jm.homework.pp_3_1_1_boot_2.model.Role;

import java.util.Set;

public interface RoleService {
    public Role getRole(String role);
    Set<Role> getAllRoles();
}
