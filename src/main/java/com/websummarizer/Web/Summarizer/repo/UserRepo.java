package com.websummarizer.Web.Summarizer.repo;

import com.websummarizer.Web.Summarizer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface UserRepo extends JpaRepository<User, Long> {
    /**
     * Retrieves a user based on a given email and request_token
     *
     * @param email The email address of the user.
     * @param token The token associated with the user
     * @return Returns the specific user (Null if no user is found)
     */
    @Query("select u from User u  where u.email = ?1 and u.request_token = ?2")
    User getUserByEmailAndResetToken(String email, String token);

    /**
     * Identify a user by their email address and set the reset_token
     *
     * @param email The email address of the user.
     * @param token The token to associate with the user
     */
    @Modifying
    @Query("update User u set u.request_token = ?1 where u.email = ?2")
    void setRequestToken(String token, String email);

    /**
     * Retrieves a user by its email address.
     *
     * @param username The email address of the user.
     * @return Optional containing the user with the given email, if found.
     */
    Optional<User> findByEmail(String username);
}
