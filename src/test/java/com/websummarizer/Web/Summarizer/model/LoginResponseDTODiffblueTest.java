package com.websummarizer.Web.Summarizer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

class LoginResponseDTODiffblueTest {
    /**
     * Method under test: {@link LoginResponseDTO#canEqual(Object)}
     */
    @Test
    void testCanEqual() {
        // Arrange, Act and Assert
        assertFalse((new LoginResponseDTO()).canEqual("Other"));
    }

    /**
     * Method under test: {@link LoginResponseDTO#canEqual(Object)}
     */
    @Test
    void testCanEqual2() {
        // Arrange
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();

        // Act and Assert
        assertTrue(loginResponseDTO.canEqual(new LoginResponseDTO()));
    }

    /**
     * Method under test: {@link LoginResponseDTO#equals(Object)}
     */
    @Test
    void testEquals() {
        // Arrange, Act and Assert
        assertNotEquals(new LoginResponseDTO(), null);
        assertNotEquals(new LoginResponseDTO(), "Different type to LoginResponseDTO");
    }

    /**
     * Method under test: {@link LoginResponseDTO#equals(Object)}
     */
    @Test
    void testEquals2() {
        // Arrange
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(new User(), "Jwt");

        // Act and Assert
        assertNotEquals(loginResponseDTO, new LoginResponseDTO());
    }

    /**
     * Method under test: {@link LoginResponseDTO#equals(Object)}
     */
    @Test
    void testEquals3() {
        // Arrange
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();

        // Act and Assert
        assertNotEquals(loginResponseDTO, new LoginResponseDTO(new User(), "Jwt"));
    }

    /**
     * Method under test: {@link LoginResponseDTO#equals(Object)}
     */
    @Test
    void testEquals4() {
        // Arrange
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setJwt("Jwt");

        // Act and Assert
        assertNotEquals(loginResponseDTO, new LoginResponseDTO());
    }

    /**
     * Method under test: {@link LoginResponseDTO#equals(Object)}
     */
    @Test
    void testEquals5() {
        // Arrange
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(mock(User.class), "Jwt");

        // Act and Assert
        assertNotEquals(loginResponseDTO, new LoginResponseDTO());
    }

    /**
     * Method under test: {@link LoginResponseDTO#equals(Object)}
     */
    @Test
    void testEquals6() {
        // Arrange
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();

        LoginResponseDTO loginResponseDTO2 = new LoginResponseDTO();
        loginResponseDTO2.setJwt("Jwt");

        // Act and Assert
        assertNotEquals(loginResponseDTO, loginResponseDTO2);
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link LoginResponseDTO#equals(Object)}
     *   <li>{@link LoginResponseDTO#hashCode()}
     * </ul>
     */
    @Test
    void testEqualsAndHashCode() {
        // Arrange
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();

        // Act and Assert
        assertEquals(loginResponseDTO, loginResponseDTO);
        int expectedHashCodeResult = loginResponseDTO.hashCode();
        assertEquals(expectedHashCodeResult, loginResponseDTO.hashCode());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link LoginResponseDTO#equals(Object)}
     *   <li>{@link LoginResponseDTO#hashCode()}
     * </ul>
     */
    @Test
    void testEqualsAndHashCode2() {
        // Arrange
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        LoginResponseDTO loginResponseDTO2 = new LoginResponseDTO();

        // Act and Assert
        assertEquals(loginResponseDTO, loginResponseDTO2);
        int expectedHashCodeResult = loginResponseDTO.hashCode();
        assertEquals(expectedHashCodeResult, loginResponseDTO2.hashCode());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link LoginResponseDTO#equals(Object)}
     *   <li>{@link LoginResponseDTO#hashCode()}
     * </ul>
     */
    @Test
    void testEqualsAndHashCode3() {
        // Arrange
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(new User(), "Jwt");
        LoginResponseDTO loginResponseDTO2 = new LoginResponseDTO(new User(), "Jwt");

        // Act and Assert
        assertEquals(loginResponseDTO, loginResponseDTO2);
        int expectedHashCodeResult = loginResponseDTO.hashCode();
        assertEquals(expectedHashCodeResult, loginResponseDTO2.hashCode());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link LoginResponseDTO#LoginResponseDTO()}
     *   <li>{@link LoginResponseDTO#setJwt(String)}
     *   <li>{@link LoginResponseDTO#setUser(User)}
     *   <li>{@link LoginResponseDTO#toString()}
     *   <li>{@link LoginResponseDTO#getJwt()}
     *   <li>{@link LoginResponseDTO#getUser()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange and Act
        LoginResponseDTO actualLoginResponseDTO = new LoginResponseDTO();
        actualLoginResponseDTO.setJwt("Jwt");
        User user = new User();
        user.setAuthorities(new HashSet<>());
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(1L);
        user.setLast_name("Doe");
        user.setLlmSelection("Llm Selection");
        user.setPassword("iloveyou");
        user.setPhone_number("6625550144");
        user.setProvider(Provider.LOCAL);
        user.setRequest_token("ABC123");
        actualLoginResponseDTO.setUser(user);
        String actualToStringResult = actualLoginResponseDTO.toString();
        String actualJwt = actualLoginResponseDTO.getJwt();

        // Assert that nothing has changed
        assertEquals("Jwt", actualJwt);
        assertEquals("LoginResponseDTO(user=User(id=1, first_name=Jane, last_name=Doe, email=jane.doe@example.org,"
                + " password=iloveyou, phone_number=6625550144, request_token=ABC123, authorities=[], provider=LOCAL,"
                + " llmSelection=Llm Selection), jwt=Jwt)", actualToStringResult);
        assertSame(user, actualLoginResponseDTO.getUser());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link LoginResponseDTO#LoginResponseDTO(User, String)}
     *   <li>{@link LoginResponseDTO#setJwt(String)}
     *   <li>{@link LoginResponseDTO#setUser(User)}
     *   <li>{@link LoginResponseDTO#toString()}
     *   <li>{@link LoginResponseDTO#getJwt()}
     *   <li>{@link LoginResponseDTO#getUser()}
     * </ul>
     */
    @Test
    void testGettersAndSetters2() {
        // Arrange
        User user = new User();
        user.setAuthorities(new HashSet<>());
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(1L);
        user.setLast_name("Doe");
        user.setLlmSelection("Llm Selection");
        user.setPassword("iloveyou");
        user.setPhone_number("6625550144");
        user.setProvider(Provider.LOCAL);
        user.setRequest_token("ABC123");

        // Act
        LoginResponseDTO actualLoginResponseDTO = new LoginResponseDTO(user, "Jwt");
        actualLoginResponseDTO.setJwt("Jwt");
        User user2 = new User();
        user2.setAuthorities(new HashSet<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirst_name("Jane");
        user2.setId(1L);
        user2.setLast_name("Doe");
        user2.setLlmSelection("Llm Selection");
        user2.setPassword("iloveyou");
        user2.setPhone_number("6625550144");
        user2.setProvider(Provider.LOCAL);
        user2.setRequest_token("ABC123");
        actualLoginResponseDTO.setUser(user2);
        String actualToStringResult = actualLoginResponseDTO.toString();
        String actualJwt = actualLoginResponseDTO.getJwt();
        User actualUser = actualLoginResponseDTO.getUser();

        // Assert that nothing has changed
        assertEquals("Jwt", actualJwt);
        assertEquals("LoginResponseDTO(user=User(id=1, first_name=Jane, last_name=Doe, email=jane.doe@example.org,"
                + " password=iloveyou, phone_number=6625550144, request_token=ABC123, authorities=[], provider=LOCAL,"
                + " llmSelection=Llm Selection), jwt=Jwt)", actualToStringResult);
        assertEquals(user, actualUser);
        assertSame(user2, actualUser);
    }
}
