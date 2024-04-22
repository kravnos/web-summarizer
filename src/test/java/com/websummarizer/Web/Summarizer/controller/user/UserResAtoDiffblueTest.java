package com.websummarizer.Web.Summarizer.controller.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UserResAtoDiffblueTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link UserResAto#toString()}
     *   <li>{@link UserResAto#getEmail()}
     *   <li>{@link UserResAto#getFirst_name()}
     *   <li>{@link UserResAto#getId()}
     *   <li>{@link UserResAto#getLast_name()}
     *   <li>{@link UserResAto#getPassword()}
     *   <li>{@link UserResAto#getPhone_number()}
     *   <li>{@link UserResAto#getRequest_token()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange
        UserResAto buildResult = UserResAto.builder()
                .email("jane.doe@example.org")
                .first_name("Jane")
                .id(1L)
                .last_name("Doe")
                .password("iloveyou")
                .phone_number("6625550144")
                .request_token("ABC123")
                .build();

        // Act
        String actualToStringResult = buildResult.toString();
        String actualEmail = buildResult.getEmail();
        String actualFirst_name = buildResult.getFirst_name();
        Long actualId = buildResult.getId();
        String actualLast_name = buildResult.getLast_name();
        String actualPassword = buildResult.getPassword();
        String actualPhone_number = buildResult.getPhone_number();

        // Assert
        assertEquals("6625550144", actualPhone_number);
        assertEquals("ABC123", buildResult.getRequest_token());
        assertEquals("Doe", actualLast_name);
        assertEquals("Jane", actualFirst_name);
        assertEquals("UserResAto(id=1, first_name=Jane, last_name=Doe, email=jane.doe@example.org, password=iloveyou,"
                + " phone_number=6625550144, request_token=ABC123)", actualToStringResult);
        assertEquals("iloveyou", actualPassword);
        assertEquals("jane.doe@example.org", actualEmail);
        assertEquals(1L, actualId.longValue());
    }

    /**
     * Method under test:
     * {@link UserResAto#UserResAto(Long, String, String, String, String, String, String)}
     */
    @Test
    void testNewUserResAto() {
        // Arrange and Act
        UserResAto actualUserResAto = new UserResAto(1L, "Jane", "Doe", "jane.doe@example.org", "iloveyou", "6625550144",
                "ABC123");

        // Assert
        assertEquals("6625550144", actualUserResAto.getPhone_number());
        assertEquals("ABC123", actualUserResAto.getRequest_token());
        assertEquals("Doe", actualUserResAto.getLast_name());
        assertEquals("Jane", actualUserResAto.getFirst_name());
        assertEquals("iloveyou", actualUserResAto.getPassword());
        assertEquals("jane.doe@example.org", actualUserResAto.getEmail());
        assertEquals(1L, actualUserResAto.getId().longValue());
    }
}
