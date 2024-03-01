package com.websummarizer.Web.Summarizer.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Domain object for Affiliate (represents a row in table "users")
 */
@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="uid")
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String LastName;

    @Column(name = "email")
    String email;

    @Column(name = "password")
    String password;

    @Column(name = "phone_number")
    String phoneNumber;
}
