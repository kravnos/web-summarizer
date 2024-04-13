package com.websummarizer.Web.Summarizer.services;

import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserDetailsService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());


    @Autowired
    private UserRepo userRepo;

    public User createUser(User user) {
        return userRepo.save(user);
    }

    // These methods are used for: password and request token
    public void setPassword(User user) {
        userRepo.setPassword(user.getPassword(), user.getEmail());
    }

    public User getUserByEmail(String email) {
        return userRepo.getUserByEmail(email);
    }

    public User getUserByEmailAndResetToken(String email, String token) {
        return userRepo.getUserByEmailAndResetToken(email, token);
    }

    public int setPasswordRequestToken(String token, User user) {
        return userRepo.setRequestToken(token, user.getEmail());
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
}
