package org.example.crud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserProfile implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name")
    @NotBlank(message = "Поле должно быть заполнено")
    @Size(min = 3, max = 50, message = "Имя должно содержать от 3 до 50 симовлов")
    private String username;

    @Column(name = "Lastname")
    @NotBlank(message = "Поле должно быть заполнено")
    @Size(min = 3, max = 50, message = "Имя должно содержать от 3 до 50 симовлов")
    private String lastname;

    @Column(name = "Age")
    @NotNull(message = "Поле должно быть заполнено")
    @Min(value = 18, message = "Возраст должен быть не меньше 18")
    private short age;

    @Column(name = "Email")
    @NotBlank(message = "Поле должно быть заполнено")
    @Email(message = "Почта должны быть типа: user@example.com")
    private String email;

    @Column
    @Size(min = 3, message = "Пароль должен быть минимум из 3-х символов")
    private String password;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles().stream()
                .map(role -> (GrantedAuthority) role)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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