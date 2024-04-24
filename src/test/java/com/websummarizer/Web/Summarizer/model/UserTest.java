package com.websummarizer.Web.Summarizer.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        long id = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password";
        String phoneNumber = "1234567890";
        Set<Role> authorities = new HashSet<>();
        authorities.add(new Role("ROLE_USER"));
        String llmSelection = "selection";
        Provider provider = Provider.LOCAL;
        String requestToken = "token";

        // Act
        User user = new User(id, firstName, lastName, email, password, phoneNumber, requestToken, authorities, provider, llmSelection);

        // Assert
        assertEquals(id, user.getId());
        assertEquals(firstName, user.getFirst_name());
        assertEquals(lastName, user.getLast_name());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(phoneNumber, user.getPhone_number());
        assertEquals(requestToken, user.getRequest_token());
        assertEquals(authorities, user.getAuthorities());
        assertEquals(llmSelection, user.getLlmSelection());
        assertEquals(provider, user.getProvider());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        User user1 = new User("john.doe@example.com");
        User user2 = new User("john.doe@example.com");
        User user3 = new User("jane.doe@example.com");

        // Assert
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
        assertTrue(!user1.equals(user3));
    }

    @Test
    void testConstructorAndGetters2() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password";
        String phoneNumber = "1234567890";
        Set<Role> authorities = new HashSet<>();
        authorities.add(new Role("ROLE_USER"));
        String llmSelection = "selection";
        Provider provider = Provider.LOCAL;

        // Act
        User user = new User(firstName, lastName, email, password, phoneNumber, authorities, llmSelection, provider);

        // Assert
        assertEquals(firstName, user.getFirst_name());
        assertEquals(lastName, user.getLast_name());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(phoneNumber, user.getPhone_number());
        assertEquals(authorities, user.getAuthorities());
        assertEquals(llmSelection, user.getLlmSelection());
        assertEquals(provider, user.getProvider());
    }

    @Test
    void testSetterAndGetters() {
        // Arrange
        User user = new User();
        String email = "john.doe@example.com";
        String llmSelection = "selection";
        Provider provider = Provider.LOCAL;

        // Act
        user.setEmail(email);
        user.setLlmSelection(llmSelection);
        user.setProvider(provider);

        // Assert
        assertEquals(email, user.getEmail());
        assertEquals(llmSelection, user.getLlmSelection());
        assertEquals(provider, user.getProvider());
    }

    @Test
    void testUserDetailsMethods() {
        // Arrange
        User user = new User();

        // Assert
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
        assertEquals(user.getEmail(), user.getUsername());
    }

    @Test
    void testBuilder() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password";
        String phoneNumber = "1234567890";
        Set<Role> authorities = new HashSet<>();
        authorities.add(new Role("ROLE_USER"));
        String llmSelection = "selection";
        Provider provider = Provider.LOCAL;

        // Act
        User user = User.builder()
                .first_name(firstName)
                .last_name(lastName)
                .email(email)
                .password(password)
                .phone_number(phoneNumber)
                .authorities(authorities)
                .llmSelection(llmSelection)
                .provider(provider)
                .build();

        // Assert
        assertNotNull(user);
        assertEquals(firstName, user.getFirst_name());
        assertEquals(lastName, user.getLast_name());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(phoneNumber, user.getPhone_number());
        assertEquals(authorities, user.getAuthorities());
        assertEquals(llmSelection, user.getLlmSelection());
        assertEquals(provider, user.getProvider());
    }
}
