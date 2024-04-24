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

    public User createUser(User user) {
        return userRepo.save(user);
    }

    /**
     * Loads user details by email.
     *
     * @param email The email of the user.
     * @param token The token associated with the user
     * @return User object containing user details (Null if not found).
     */
    public User getUserByEmailAndResetToken(String email, String token) {
        return userRepo.getUserByEmailAndResetToken(email, token);
    }

    /**
     * Loads user details by email.
     *
     * @param token The token associated with the user
     * @param user The user that is having its reset token set
     */
    public void setPasswordRequestToken(String token, User user) {
        userRepo.setRequestToken(token, user.getEmail());
    }

    /**
     * Loads user details by email.
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

    public String getUserName(long id) {
        String username = "You";
        try {
            username = userRepo.findById(id).get().getFirst_name();
        } catch (Exception e){

        }

        return username;
    }

    public Long getFoundId(String email){
        long id = 1l;

        Optional<User> maybeFoundEmail = userRepo.findByEmail(email);
        try {
            id = maybeFoundEmail.get().getId();
        }catch (Exception e){

        }

        return id;
    }

    public User getFoundUser(String email){
//        long id = 1l;

        Optional<User> maybeFoundEmail = userRepo.findByEmail(email);
//        try {
//            id = maybeFoundEmail.get().getId();
//        }catch (Exception e){
//
//        }

        return maybeFoundEmail.get();
    }
}

