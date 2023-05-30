package com.burravlev.task.user.domain.entity;

import com.burravlev.task.token.model.Token;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Public username used for credentials
    @NotEmpty
    @Column(name = "public_username", unique = true, nullable = false)
    private String publicUsername;
    @Email
    @NotEmpty
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    private String firstName;
    private String lastName;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //Returns JWT subject
    @Override
    public String getUsername() {
        return id.toString();
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
