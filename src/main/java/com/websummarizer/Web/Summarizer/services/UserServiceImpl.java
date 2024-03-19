package com.websummarizer.Web.Summarizer.services;

import com.websummarizer.Web.Summarizer.model.Role;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserDetailsService {


    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("in the user service impl class");
        logger.info("returning the following "+userRepo.findByEmail(email));
        return userRepo.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User is not valid"));
    }
}
