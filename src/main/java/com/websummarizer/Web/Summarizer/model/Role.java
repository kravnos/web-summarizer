package com.websummarizer.Web.Summarizer.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * Domain object representing a role.
 */
@Entity
@Table(name = "roles")
@Data
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private long roleId;

    /**
     * Authority string representing the role.
     */
    private String authority;

    public Role(String authority) {
        this.authority = authority;
    }

    public Role() {
        super();
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
