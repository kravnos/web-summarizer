package com.websummarizer.Web.Summarizer.repo;

import com.websummarizer.Web.Summarizer.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {
    Optional<Object> findByAuthority(String admin);
}
