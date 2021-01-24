package jm.homework.pp_3_1_1_boot_2.dao;

import jm.homework.pp_3_1_1_boot_2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public interface UserDao extends JpaRepository<User, Long> {

    User findByName(String name);
    User findByEmail(String email);

//    @Query("select u from User u where u.username = '%'")
//    User loadUserByUsername2(String username);

//    @Query("SELECT f FROM Foo f WHERE LOWER(f.name) = LOWER(:name)")
//    Foo retrieveByName(@Param("name") String name);
}
