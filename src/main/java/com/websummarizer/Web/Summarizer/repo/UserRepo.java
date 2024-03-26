package com.websummarizer.Web.Summarizer.repo;

import com.websummarizer.Web.Summarizer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository interface for managing User entities.
 */
@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    /**
     * Retrieves a user by its email address.
     *
     * @param username The email address of the user.
     * @return Optional containing the user with the given email, if found.
     */
    Optional<User> findByEmail(String username);
}
