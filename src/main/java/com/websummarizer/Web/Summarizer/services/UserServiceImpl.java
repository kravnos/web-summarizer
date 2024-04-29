package com.websummarizer.Web.Summarizer.services;

import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserDetailsService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Autowired
    private UserRepo userRepo;

    /**
     * Creates a new user.
     *
     * @param user The user to create.
     * @return The created user.
     */
    public User createUser(User user) {
        return userRepo.save(user);
    }

    /**
     * Retrieves a user by email and reset token.
     *
     * @param email The email of the user.
     * @param token The reset token associated with the user.
     * @return The user object containing user details (null if not found).
     */
    public User getUserByEmailAndResetToken(String email, String token) {
        return userRepo.getUserByEmailAndResetToken(email, token);
    }

    /**
     * Sets the reset token for a user.
     *
     * @param token The reset token.
     * @param user  The user to set the token for.
     */
    public void setPasswordRequestToken(String token, User user) {
        userRepo.setRequestToken(token, user.getEmail());
    }

    /**
     * Loads user details by username.
     *
     * @param email The email of the user.
     * @return UserDetails object containing user details.
     * @throws UsernameNotFoundException if user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Loading user by username: " + email);
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
    }

    /**
     * Retrieves the username of a user by their ID.
     *
     * @param id The ID of the user.
     * @return The username.
     */
    public String getUserName(long id) {
        String username = "You";
        try {
            username = userRepo.findById(id).get().getFirst_name();
        } catch (Exception e) {
            // Handle exceptions
        }
        return username;
    }

    /**
     * Retrieves the ID of a user by their email.
     *
     * @param email The email of the user.
     * @return The ID of the user.
     */
    public Long getFoundId(String email) {
        long id = 1L; // Default value

        Optional<User> maybeFoundEmail = userRepo.findByEmail(email);
        try {
            id = maybeFoundEmail.get().getId();
        } catch (Exception e) {
            // Handle exceptions
        }

        return id;
    }

    /**
     * Retrieves the user by their email.
     *
     * @param email The email of the user.
     * @return The user object.
     */
    public User getFoundUser(String email) {
        Optional<User> maybeFoundEmail = userRepo.findByEmail(email);
        return maybeFoundEmail.orElse(null); // Return null if not found
    }
}
