package com.example.blogmanagementsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Username cannot be empty")
    @Column(columnDefinition = "varchar(10) not null", unique = true)
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    @Column(columnDefinition = "varchar(60) not null")
    private String password;

    @Pattern(regexp = "^(ADMIN|USER)$", message = "Role must be either ADMIN or USER")
    @Column(columnDefinition = "varchar(5) check(role in ('ADMIN', 'USER'))")
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Blog> blogs;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
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
}
