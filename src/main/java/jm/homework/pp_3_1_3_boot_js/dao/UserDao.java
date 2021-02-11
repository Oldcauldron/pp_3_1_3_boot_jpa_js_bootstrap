package jm.homework.pp_3_1_3_boot_js.dao;

import jm.homework.pp_3_1_3_boot_js.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {

    User findByName(String name);
    User findByEmail(String email);

//    @Query("select u from User u where u.username = '%'")
//    User loadUserByUsername2(String username);

//    @Query("SELECT f FROM Foo f WHERE LOWER(f.name) = LOWER(:name)")
//    Foo retrieveByName(@Param("name") String name);
}
