package jm.homework.pp_3_1_1_boot_2.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Set;

// Для того, чтобы в дальнейшим использовать класс User в Spring Security, он должен реализовывать интерфейс UserDetails.
// UserDetails можно представить, как адаптер между БД пользователей и тем что требуется Spring Security внутри SecurityContextHolder
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    @NotEmpty(message = "email should not be empty!")
    @Email
    private String email; // уникальное значение

    @Column(name = "password")
    @NotEmpty(message = "Password should not be empty!")
    private String password;

    @Column(name = "name")
    @NotEmpty(message = "name should not be empty!")
    private String name; // уникальное значение

    @Column(name = "age")
    private int age;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
                joinColumns = {@JoinColumn(name = "user_id")},
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
    }

    public User(Long id,
                @NotEmpty(message = "email should not be empty!") @Email String email,
                @NotEmpty(message = "Password should not be empty!") String password,
                @NotEmpty(message = "name should not be empty!") String name,
                int age) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return email;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String username) {
        this.email = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
}

