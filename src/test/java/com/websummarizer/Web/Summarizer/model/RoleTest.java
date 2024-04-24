package com.websummarizer.Web.Summarizer.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        // Arrange
        long roleId = 1L;
        String authority = "ROLE_USER";

        // Act
        Role role = new Role(authority);
        role.setRoleId(roleId);

        // Assert
        assertEquals(roleId, role.getRoleId());
        assertEquals(authority, role.getAuthority());
    }

    @Test
    void testNoArgsConstructor() {
        // Arrange & Act
        Role role = new Role();

        // Assert
        assertEquals(null, role.getAuthority());
    }

    @Test
    void testGetAuthority() {
        // Arrange
        String authority = "ROLE_ADMIN";
        Role role = new Role(authority);

        // Act & Assert
        assertEquals(authority, role.getAuthority());
    }
}
