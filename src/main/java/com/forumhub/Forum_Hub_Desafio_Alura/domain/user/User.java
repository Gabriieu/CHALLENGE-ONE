package com.forumhub.Forum_Hub_Desafio_Alura.domain.user;

import com.forumhub.Forum_Hub_Desafio_Alura.domain.user.dto.CreateUserDTO;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.user.dto.UserUpdateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity(name = "User")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private boolean isActive;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoles role = UserRoles.USER;

    public User(CreateUserDTO payload, String hash) {
        this.name = payload.name();
        this.email = payload.email();
        this.password = hash;
        this.isActive = true;
    }


    public void update(UserUpdateDTO payload) {
        if (payload.name() != null) {
            this.name = payload.name();
        }
        if (payload.email() != null) {
            this.email = payload.email();
        }
    }

    public void setHash(String hash) {
        this.password = hash;
    }

    public void delete() {
        this.isActive = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRoles.ADMIN) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.email;
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
