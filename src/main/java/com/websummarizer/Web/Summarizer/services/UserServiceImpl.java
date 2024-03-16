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

@Service
public class UserServiceImpl implements UserDetailsService {

//    @Autowired
//    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    public User createUser(User user) {
//        return userRepo.save(user);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("in the user service impl class");
        if(!username.equals("Ethan Singh")) throw new UsernameNotFoundException("NOT ETHAN");
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1,"USER"));

        return new User(1,"Ethan","Singh","email",passwordEncoder.encode("password"),"phone",roles);

    }
}
