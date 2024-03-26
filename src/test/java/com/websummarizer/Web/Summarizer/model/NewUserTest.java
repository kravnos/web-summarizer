package com.websummarizer.Web.Summarizer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class NewUserTest {
    /**
     * Method under test: {@link User#toString()}
     */
    @Test
    void testToString() {
        // Arrange
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(1L);
        user.setLast_name("Doe");
        user.setPassword("iloveyou");
        user.setPhone_number("6625550144");

        // Act and Assert
        assertEquals("User{id=1, first_name='Jane', last_name='Doe', email='jane.doe@example.org', password='iloveyou',"
                + " phone_number='6625550144'}", user.toString());
    }
}
