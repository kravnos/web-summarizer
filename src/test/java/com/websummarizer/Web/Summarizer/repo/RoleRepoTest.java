package com.websummarizer.Web.Summarizer.repo;

import com.websummarizer.Web.Summarizer.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleRepoTest {

    @Mock
    private RoleRepo roleRepo;

    @Test
    void testFindByAuthority() {
        // Arrange
        String authority = "ROLE_USER";
        Role role = new Role(authority);
        when(roleRepo.findByAuthority(authority)).thenReturn(Optional.of(role));

        // Act
        Optional<Role> result = roleRepo.findByAuthority(authority);

        // Assert
        assertEquals(role, result.orElse(null));
    }
}
