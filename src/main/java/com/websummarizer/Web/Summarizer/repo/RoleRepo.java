package com.websummarizer.Web.Summarizer.repo;

import com.websummarizer.Web.Summarizer.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Role entities.
 */
@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    /**
     * Retrieves a role by its authority.
     *
     * @param authority The authority string representing the role.
     * @return Optional containing the role with the given authority, if found.
     */
    Optional<Role> findByAuthority(String authority);
}
