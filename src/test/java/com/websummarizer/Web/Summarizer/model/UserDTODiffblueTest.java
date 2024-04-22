package com.websummarizer.Web.Summarizer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UserDTODiffblueTest {
    /**
     * Method under test: {@link UserDTO#canEqual(Object)}
     */
    @Test
    void testCanEqual() {
        // Arrange, Act and Assert
        assertFalse((new UserDTO("jane.doe@example.org", "iloveyou")).canEqual("Other"));
    }

    /**
     * Method under test: {@link UserDTO#canEqual(Object)}
     */
    @Test
    void testCanEqual2() {
        // Arrange
        UserDTO userDTO = new UserDTO("jane.doe@example.org", "iloveyou");

        // Act and Assert
        assertTrue(userDTO.canEqual(new UserDTO("jane.doe@example.org", "iloveyou")));
    }

    /**
     * Method under test: {@link UserDTO#equals(Object)}
     */
    @Test
    void testEquals() {
        // Arrange, Act and Assert
        assertNotEquals(new UserDTO("jane.doe@example.org", "iloveyou"), null);
        assertNotEquals(new UserDTO("jane.doe@example.org", "iloveyou"), "Different type to UserDTO");
    }

    /**
     * Method under test: {@link UserDTO#equals(Object)}
     */
    @Test
    void testEquals2() {
        // Arrange
        UserDTO userDTO = new UserDTO("john.smith@example.org", "iloveyou");

        // Act and Assert
        assertNotEquals(userDTO, new UserDTO("jane.doe@example.org", "iloveyou"));
    }

    /**
     * Method under test: {@link UserDTO#equals(Object)}
     */
    @Test
    void testEquals3() {
        // Arrange
        UserDTO userDTO = new UserDTO(null, "iloveyou");

        // Act and Assert
        assertNotEquals(userDTO, new UserDTO("jane.doe@example.org", "iloveyou"));
    }

    /**
     * Method under test: {@link UserDTO#equals(Object)}
     */
    @Test
    void testEquals4() {
        // Arrange
        UserDTO userDTO = new UserDTO("jane.doe@example.org", "jane.doe@example.org");

        // Act and Assert
        assertNotEquals(userDTO, new UserDTO("jane.doe@example.org", "iloveyou"));
    }

    /**
     * Method under test: {@link UserDTO#equals(Object)}
     */
    @Test
    void testEquals5() {
        // Arrange
        UserDTO userDTO = new UserDTO("jane.doe@example.org", null);

        // Act and Assert
        assertNotEquals(userDTO, new UserDTO("jane.doe@example.org", "iloveyou"));
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link UserDTO#equals(Object)}
     *   <li>{@link UserDTO#hashCode()}
     * </ul>
     */
    @Test
    void testEqualsAndHashCode() {
        // Arrange
        UserDTO userDTO = new UserDTO("jane.doe@example.org", "iloveyou");

        // Act and Assert
        assertEquals(userDTO, userDTO);
        int expectedHashCodeResult = userDTO.hashCode();
        assertEquals(expectedHashCodeResult, userDTO.hashCode());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link UserDTO#equals(Object)}
     *   <li>{@link UserDTO#hashCode()}
     * </ul>
     */
    @Test
    void testEqualsAndHashCode2() {
        // Arrange
        UserDTO userDTO = new UserDTO("jane.doe@example.org", "iloveyou");
        UserDTO userDTO2 = new UserDTO("jane.doe@example.org", "iloveyou");

        // Act and Assert
        assertEquals(userDTO, userDTO2);
        int expectedHashCodeResult = userDTO.hashCode();
        assertEquals(expectedHashCodeResult, userDTO2.hashCode());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link UserDTO#equals(Object)}
     *   <li>{@link UserDTO#hashCode()}
     * </ul>
     */
    @Test
    void testEqualsAndHashCode3() {
        // Arrange
        UserDTO userDTO = new UserDTO(null, "iloveyou");
        UserDTO userDTO2 = new UserDTO(null, "iloveyou");

        // Act and Assert
        assertEquals(userDTO, userDTO2);
        int expectedHashCodeResult = userDTO.hashCode();
        assertEquals(expectedHashCodeResult, userDTO2.hashCode());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link UserDTO#equals(Object)}
     *   <li>{@link UserDTO#hashCode()}
     * </ul>
     */
    @Test
    void testEqualsAndHashCode4() {
        // Arrange
        UserDTO userDTO = new UserDTO("jane.doe@example.org", null);
        UserDTO userDTO2 = new UserDTO("jane.doe@example.org", null);

        // Act and Assert
        assertEquals(userDTO, userDTO2);
        int expectedHashCodeResult = userDTO.hashCode();
        assertEquals(expectedHashCodeResult, userDTO2.hashCode());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link UserDTO#UserDTO()}
     *   <li>{@link UserDTO#setLogin_email(String)}
     *   <li>{@link UserDTO#setLogin_password(String)}
     *   <li>{@link UserDTO#toString()}
     *   <li>{@link UserDTO#getLogin_email()}
     *   <li>{@link UserDTO#getLogin_password()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange and Act
        UserDTO actualUserDTO = new UserDTO();
        actualUserDTO.setLogin_email("jane.doe@example.org");
        actualUserDTO.setLogin_password("iloveyou");
        String actualToStringResult = actualUserDTO.toString();
        String actualLogin_email = actualUserDTO.getLogin_email();

        // Assert that nothing has changed
        assertEquals("UserDTO(login_email=jane.doe@example.org, login_password=iloveyou)", actualToStringResult);
        assertEquals("iloveyou", actualUserDTO.getLogin_password());
        assertEquals("jane.doe@example.org", actualLogin_email);
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link UserDTO#UserDTO(String, String)}
     *   <li>{@link UserDTO#setLogin_email(String)}
     *   <li>{@link UserDTO#setLogin_password(String)}
     *   <li>{@link UserDTO#toString()}
     *   <li>{@link UserDTO#getLogin_email()}
     *   <li>{@link UserDTO#getLogin_password()}
     * </ul>
     */
    @Test
    void testGettersAndSetters2() {
        // Arrange and Act
        UserDTO actualUserDTO = new UserDTO("jane.doe@example.org", "iloveyou");
        actualUserDTO.setLogin_email("jane.doe@example.org");
        actualUserDTO.setLogin_password("iloveyou");
        String actualToStringResult = actualUserDTO.toString();
        String actualLogin_email = actualUserDTO.getLogin_email();

        // Assert that nothing has changed
        assertEquals("UserDTO(login_email=jane.doe@example.org, login_password=iloveyou)", actualToStringResult);
        assertEquals("iloveyou", actualUserDTO.getLogin_password());
        assertEquals("jane.doe@example.org", actualLogin_email);
    }
}
