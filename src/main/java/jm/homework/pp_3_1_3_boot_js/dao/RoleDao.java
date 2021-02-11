package jm.homework.pp_3_1_3_boot_js.dao;

import jm.homework.pp_3_1_3_boot_js.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}
