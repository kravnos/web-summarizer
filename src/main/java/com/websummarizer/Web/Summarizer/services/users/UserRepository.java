package com.websummarizer.Web.Summarizer.services.users;

import com.websummarizer.Web.Summarizer.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> findByEmail(String email); //SELECT * FROM users WHERE email = ...
}
