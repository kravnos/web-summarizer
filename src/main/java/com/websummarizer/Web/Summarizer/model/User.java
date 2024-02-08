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
    private String firstName;

    @Setter
    @Column(name = "last_name")
    private String LastName;

    @Setter
    @Column(name = "email")
    String email;

    @Setter
    @Column(name = "password")
    String password;

    @Setter
    @Column(name = "phone_number")
    String phoneNumber;
}
