package com.vladavekin.phonebook.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Table(name = "usr")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull()
    @JoinColumn(name = "username")
    @Size(min = 3, message = "User name minimum 3 char")
    private String username;

    @NotNull
    @JoinColumn(name = "password")
    @Size(min = 5, message = "Password minimum 5 char")
    private String password;

    @Transient
    @Size(min = 5, message = "Password minimum 5 char")
    private String password2;

    @NotNull
    @JoinColumn(name = "full_name")
    @Size(min = 5, message = "Full name minimum 5 char")
    private String fullName;

    @NotNull
    @JoinColumn(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PhoneBookData> phoneBookData;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
        return active;
    }
}
