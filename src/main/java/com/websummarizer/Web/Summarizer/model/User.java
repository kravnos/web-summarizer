package com.websummarizer.Web.Summarizer.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * Domain object representing a user.
 */
@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private long id;

    /**
     * First name of the user.
     */
    @Column(name = "first_name")
    private String first_name;

    /**
     * Last name of the user.
     */
    @Column(name = "last_name")
    private String last_name;

    /**
     * Email address of the user (unique).
     */
    @Column(name = "email", unique = true)
    private String email;

    /**
     * Password of the user.
     */
    @Column(name = "password")
    private String password;

    /**
     * Phone number of the user.
     */
    @Column(name = "phone_number")
    private String phone_number;

    /**
     * Set of roles assigned to the user.
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role_junction",
            joinColumns = {@JoinColumn(name = "uid")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> authorities;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    public User(String email) {
        this.email=email;
    }

    public User(String first_name, String last_name, String email, String password, String phone_number, Set<Role> authorities, Provider provider) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
        this.authorities = authorities;
        this.provider = provider;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
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
