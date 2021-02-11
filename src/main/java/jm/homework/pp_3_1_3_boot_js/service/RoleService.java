package jm.homework.pp_3_1_3_boot_js.service;

import jm.homework.pp_3_1_3_boot_js.model.Role;

import java.util.Set;

public interface RoleService {
    public Role getRole(String role);
    Set<Role> getAllRoles();
}
