package com.websummarizer.Web.Summarizer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

class UserDiffblueTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link User#User(String)}
     *   <li>{@link User#getAuthorities()}
     *   <li>{@link User#getUsername()}
     *   <li>{@link User#isAccountNonExpired()}
     *   <li>{@link User#isAccountNonLocked()}
     *   <li>{@link User#isCredentialsNonExpired()}
     *   <li>{@link User#isEnabled()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange and Act
        User actualUser = new User("jane.doe@example.org");
        Collection<? extends GrantedAuthority> actualAuthorities = actualUser.getAuthorities();
        String actualUsername = actualUser.getUsername();
        boolean actualIsAccountNonExpiredResult = actualUser.isAccountNonExpired();
        boolean actualIsAccountNonLockedResult = actualUser.isAccountNonLocked();
        boolean actualIsCredentialsNonExpiredResult = actualUser.isCredentialsNonExpired();

        // Assert
        assertEquals("jane.doe@example.org", actualUsername);
        assertNull(actualAuthorities);
        assertTrue(actualIsAccountNonExpiredResult);
        assertTrue(actualIsAccountNonLockedResult);
        assertTrue(actualIsCredentialsNonExpiredResult);
        assertTrue(actualUser.isEnabled());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link User#User(String, String, String, String, String, Set, Provider)}
     *   <li>{@link User#getAuthorities()}
     *   <li>{@link User#getUsername()}
     *   <li>{@link User#isAccountNonExpired()}
     *   <li>{@link User#isAccountNonLocked()}
     *   <li>{@link User#isCredentialsNonExpired()}
     *   <li>{@link User#isEnabled()}
     * </ul>
     */
    @Test
    void testGettersAndSetters2() {
        // Arrange
        HashSet<Role> authorities = new HashSet<>();

        // Act
        User actualUser = new User("Jane", "Doe", "jane.doe@example.org", "iloveyou", "6625550144", authorities,
                Provider.LOCAL);
        Collection<? extends GrantedAuthority> actualAuthorities = actualUser.getAuthorities();
        String actualUsername = actualUser.getUsername();
        boolean actualIsAccountNonExpiredResult = actualUser.isAccountNonExpired();
        boolean actualIsAccountNonLockedResult = actualUser.isAccountNonLocked();
        boolean actualIsCredentialsNonExpiredResult = actualUser.isCredentialsNonExpired();

        // Assert
        assertEquals("jane.doe@example.org", actualUsername);
        assertTrue(actualIsAccountNonExpiredResult);
        assertTrue(actualIsAccountNonLockedResult);
        assertTrue(actualIsCredentialsNonExpiredResult);
        assertTrue(actualUser.isEnabled());
        assertSame(authorities, actualAuthorities);
    }
}
