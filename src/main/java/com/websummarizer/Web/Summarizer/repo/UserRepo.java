package com.websummarizer.Web.Summarizer.repo;

import com.websummarizer.Web.Summarizer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepo extends JpaRepository<User, Long> {
    //  Run this method to update a user's password based on the user's email
    @Modifying
    @Query("update User u set u.password = ?1 where u.email = ?2")
    void setEmail(String password, String email);
}
