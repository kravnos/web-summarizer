package com.websummarizer.Web.Summarizer.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Domain object for Affiliate (represents a row in table "users")
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="uid")
    private long id;

    @Setter
    @Column(name = "first_name")
    private String first_name;

    @Setter
    @Column(name = "last_name")
    private String last_name;

    @Setter
    @Column(name = "email" , unique = true)
    String email;

    @Setter
    @Column(name = "password")
    String password;

    @Setter
    @Column(name = "phone_number")
    String phone_number;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone_number='" + phone_number + '\'' +
                '}';
    }
}
