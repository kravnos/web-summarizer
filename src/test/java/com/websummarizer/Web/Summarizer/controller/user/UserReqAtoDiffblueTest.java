package com.websummarizer.Web.Summarizer.controller.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UserReqAtoDiffblueTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link UserReqAto#toString()}
     *   <li>{@link UserReqAto#getAccount_llm()}
     *   <li>{@link UserReqAto#getEmail()}
     *   <li>{@link UserReqAto#getFirst_name()}
     *   <li>{@link UserReqAto#getLast_name()}
     *   <li>{@link UserReqAto#getPassword()}
     *   <li>{@link UserReqAto#getPhone_number()}
     *   <li>{@link UserReqAto#getRequest_token()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange
        UserReqAto buildResult = UserReqAto.builder()
                .account_llm("3")
                .email("jane.doe@example.org")
                .first_name("Jane")
                .last_name("Doe")
                .password("iloveyou")
                .phone_number("6625550144")
                .request_token("ABC123")
                .build();

        // Act
        String actualToStringResult = buildResult.toString();
        String actualAccount_llm = buildResult.getAccount_llm();
        String actualEmail = buildResult.getEmail();
        String actualFirst_name = buildResult.getFirst_name();
        String actualLast_name = buildResult.getLast_name();
        String actualPassword = buildResult.getPassword();
        String actualPhone_number = buildResult.getPhone_number();

        // Assert
        assertEquals("3", actualAccount_llm);
        assertEquals("6625550144", actualPhone_number);
        assertEquals("ABC123", buildResult.getRequest_token());
        assertEquals("Doe", actualLast_name);
        assertEquals("Jane", actualFirst_name);
        assertEquals(
                "UserReqAto(first_name=Jane, last_name=Doe, email=jane.doe@example.org, password=iloveyou, phone_number"
                        + "=6625550144, request_token=ABC123)",
                actualToStringResult);
        assertEquals("iloveyou", actualPassword);
        assertEquals("jane.doe@example.org", actualEmail);
    }

    /**
     * Method under test:
     * {@link UserReqAto#UserReqAto(String, String, String, String, String, String, String)}
     */
    @Test
    void testNewUserReqAto() {
        // Arrange and Act
        UserReqAto actualUserReqAto = new UserReqAto("Jane", "Doe", "jane.doe@example.org", "iloveyou", "6625550144",
                "ABC123", "3");

        // Assert
        assertEquals("3", actualUserReqAto.getAccount_llm());
        assertEquals("6625550144", actualUserReqAto.getPhone_number());
        assertEquals("ABC123", actualUserReqAto.getRequest_token());
        assertEquals("Doe", actualUserReqAto.getLast_name());
        assertEquals("Jane", actualUserReqAto.getFirst_name());
        assertEquals("iloveyou", actualUserReqAto.getPassword());
        assertEquals("jane.doe@example.org", actualUserReqAto.getEmail());
    }
}
