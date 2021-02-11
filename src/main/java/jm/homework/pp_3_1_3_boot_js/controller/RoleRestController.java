package jm.homework.pp_3_1_3_boot_js.controller;

import jm.homework.pp_3_1_3_boot_js.model.Role;
import jm.homework.pp_3_1_3_boot_js.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("api/")
public class RoleRestController {

    RoleService roleService;

    @Autowired
    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public Set<Role> getAllRoles() {
        return roleService.getAllRoles();
    }
}
