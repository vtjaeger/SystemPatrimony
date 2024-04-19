package br.patrimony.system.models;

import br.patrimony.system.dtos.requests.user.UserRequest;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tb_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    private UserRole role;

    public User() {
    }

    public User(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        switch (this.role) {
            case ADMIN:
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
                authorities.add(new SimpleGrantedAuthority("ROLE_COORDINATOR"));
                authorities.add(new SimpleGrantedAuthority("ROLE_SPECIALIST"));
                authorities.add(new SimpleGrantedAuthority("ROLE_ANALYST"));
                authorities.add(new SimpleGrantedAuthority("ROLE_ASSISTANT"));
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                break;

            case MANAGER:
                authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
                authorities.add(new SimpleGrantedAuthority("ROLE_COORDINATOR"));
                authorities.add(new SimpleGrantedAuthority("ROLE_SPECIALIST"));
                authorities.add(new SimpleGrantedAuthority("ROLE_ANALYST"));
                authorities.add(new SimpleGrantedAuthority("ROLE_ASSISTANT"));
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                break;

            case COORDINATOR:
                authorities.add(new SimpleGrantedAuthority("ROLE_COORDINATOR"));
                authorities.add(new SimpleGrantedAuthority("ROLE_SPECIALIST"));
                authorities.add(new SimpleGrantedAuthority("ROLE_ANALYST"));
                authorities.add(new SimpleGrantedAuthority("ROLE_ASSISTANT"));
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                break;

            case SPECIALIST:
                authorities.add(new SimpleGrantedAuthority("ROLE_SPECIALIST"));
                authorities.add(new SimpleGrantedAuthority("ROLE_ANALYST"));
                authorities.add(new SimpleGrantedAuthority("ROLE_ASSISTANT"));
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                break;

            case ANALYST:
                authorities.add(new SimpleGrantedAuthority("ROLE_ANALYST"));
                authorities.add(new SimpleGrantedAuthority("ROLE_ASSISTANT"));
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                break;

            case ASSISTANT:
                authorities.add(new SimpleGrantedAuthority("ROLE_ASSISTANT"));
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                break;
            default:
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                break;
        }

        return authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.login;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}