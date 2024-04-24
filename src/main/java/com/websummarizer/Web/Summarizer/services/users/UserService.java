package com.websummarizer.Web.Summarizer.services.users;

import com.websummarizer.Web.Summarizer.common.exceptions.CCNotFoundException;
import com.websummarizer.Web.Summarizer.controller.user.UserReqAto;
import com.websummarizer.Web.Summarizer.controller.user.UserResAto;
import com.websummarizer.Web.Summarizer.controller.user.UsersResAto;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.services.history.HistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Add a new user
    public UserResAto addUser(UserReqAto reqSto) {
        var user = userRepository.save(UserMapper.mapUserReqAtoToEto(reqSto));
        return UserMapper.mapUserEtoToResAto(user);
    }

    // Retrieve a user by its ID
    public UserResAto getUser(long id) {
        Optional<User> maybeFoundUser = userRepository.findById(id);

        return maybeFoundUser.map(UserMapper::mapUserEtoToResAto).orElseThrow(CCNotFoundException::new);
    }

    public String getUserName(long id) {
        String username = "You";
        try {
            username = userRepository.findById(id).get().getFirst_name();
        } catch (Exception e){

        }

        return username;
    }

    // Update an existing user
    public UserResAto updateUser(Long id, UserReqAto userReqAto){

        Optional<User> maybeExisting = userRepository.findById(id);

        return maybeExisting.map(user -> {
            UserMapper.updateUser(user, userReqAto);
            userRepository.save(user);
            return UserMapper.mapUserEtoToResAto(user);
        }).orElseThrow();
    }

    // Delete a user by its ID
    public void deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new CCNotFoundException(String.format("User record doesn't exist [id=%s]", id));
        }
        userRepository.deleteById(id);
    }

    // Retrieve all users
    public UsersResAto findAllUser() {
        var users = new ArrayList<UserResAto>();
        userRepository.findAll().forEach((user) -> users.add(UserMapper.mapUserEtoToResAto(user)));

        return UsersResAto.builder()
                .users(users)
                .build();
    }

    public Long getFoundId(String email){
        long id = 1l;

        Optional<User> maybeFoundEmail = userRepository.findByEmail(email);
        try {
            id = maybeFoundEmail.get().getId();
        }catch (Exception e){

        }

        return id;
    }

    public User getFoundUser(String email){
//        long id = 1l;

        Optional<User> maybeFoundEmail = userRepository.findByEmail(email);
//        try {
//            id = maybeFoundEmail.get().getId();
//        }catch (Exception e){
//
//        }

        return maybeFoundEmail.get();
    }
}