package com.websummarizer.Web.Summarizer.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
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


    public User(String firstName, String lastName, String email, String password, String phoneNumber) {
        this.firstName = firstName;
        LastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public User() {

    }
}
