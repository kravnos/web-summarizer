package com.websummarizer.Web.Summarizer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BitlyRequestDiffblueTest {
    /**
     * Method under test: {@link BitlyRequest#canEqual(Object)}
     */
    @Test
    void testCanEqual() {
        // Arrange, Act and Assert
        assertFalse((new BitlyRequest()).canEqual("Other"));
    }

    /**
     * Method under test: {@link BitlyRequest#canEqual(Object)}
     */
    @Test
    void testCanEqual2() {
        // Arrange
        BitlyRequest bitlyRequest = new BitlyRequest();
        bitlyRequest.setLongURL("https://example.org/example");

        BitlyRequest bitlyRequest2 = new BitlyRequest();
        bitlyRequest2.setLongURL("https://example.org/example");

        // Act and Assert
        assertTrue(bitlyRequest.canEqual(bitlyRequest2));
    }

    /**
     * Method under test: {@link BitlyRequest#equals(Object)}
     */
    @Test
    void testEquals() {
        // Arrange
        BitlyRequest bitlyRequest = new BitlyRequest();
        bitlyRequest.setLongURL("https://example.org/example");

        // Act and Assert
        assertNotEquals(bitlyRequest, null);
    }

    /**
     * Method under test: {@link BitlyRequest#equals(Object)}
     */
    @Test
    void testEquals2() {
        // Arrange
        BitlyRequest bitlyRequest = new BitlyRequest();
        bitlyRequest.setLongURL("https://example.org/example");

        // Act and Assert
        assertNotEquals(bitlyRequest, "Different type to BitlyRequest");
    }

    /**
     * Method under test: {@link BitlyRequest#equals(Object)}
     */
    @Test
    void testEquals3() {
        // Arrange
        BitlyRequest bitlyRequest = new BitlyRequest();
        bitlyRequest.setLongURL("Long URL");

        BitlyRequest bitlyRequest2 = new BitlyRequest();
        bitlyRequest2.setLongURL("https://example.org/example");

        // Act and Assert
        assertNotEquals(bitlyRequest, bitlyRequest2);
    }

    /**
     * Method under test: {@link BitlyRequest#equals(Object)}
     */
    @Test
    void testEquals4() {
        // Arrange
        BitlyRequest bitlyRequest = new BitlyRequest();
        bitlyRequest.setLongURL(null);

        BitlyRequest bitlyRequest2 = new BitlyRequest();
        bitlyRequest2.setLongURL("https://example.org/example");

        // Act and Assert
        assertNotEquals(bitlyRequest, bitlyRequest2);
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link BitlyRequest#equals(Object)}
     *   <li>{@link BitlyRequest#hashCode()}
     * </ul>
     */
    @Test
    void testEqualsAndHashCode() {
        // Arrange
        BitlyRequest bitlyRequest = new BitlyRequest();
        bitlyRequest.setLongURL("https://example.org/example");

        // Act and Assert
        assertEquals(bitlyRequest, bitlyRequest);
        int expectedHashCodeResult = bitlyRequest.hashCode();
        assertEquals(expectedHashCodeResult, bitlyRequest.hashCode());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link BitlyRequest#equals(Object)}
     *   <li>{@link BitlyRequest#hashCode()}
     * </ul>
     */
    @Test
    void testEqualsAndHashCode2() {
        // Arrange
        BitlyRequest bitlyRequest = new BitlyRequest();
        bitlyRequest.setLongURL("https://example.org/example");

        BitlyRequest bitlyRequest2 = new BitlyRequest();
        bitlyRequest2.setLongURL("https://example.org/example");

        // Act and Assert
        assertEquals(bitlyRequest, bitlyRequest2);
        int expectedHashCodeResult = bitlyRequest.hashCode();
        assertEquals(expectedHashCodeResult, bitlyRequest2.hashCode());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link BitlyRequest#equals(Object)}
     *   <li>{@link BitlyRequest#hashCode()}
     * </ul>
     */
    @Test
    void testEqualsAndHashCode3() {
        // Arrange
        BitlyRequest bitlyRequest = new BitlyRequest();
        bitlyRequest.setLongURL(null);

        BitlyRequest bitlyRequest2 = new BitlyRequest();
        bitlyRequest2.setLongURL(null);

        // Act and Assert
        assertEquals(bitlyRequest, bitlyRequest2);
        int expectedHashCodeResult = bitlyRequest.hashCode();
        assertEquals(expectedHashCodeResult, bitlyRequest2.hashCode());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>default or parameterless constructor of {@link BitlyRequest}
     *   <li>{@link BitlyRequest#setLongURL(String)}
     *   <li>{@link BitlyRequest#toString()}
     *   <li>{@link BitlyRequest#getLongURL()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange and Act
        BitlyRequest actualBitlyRequest = new BitlyRequest();
        actualBitlyRequest.setLongURL("https://example.org/example");
        String actualToStringResult = actualBitlyRequest.toString();

        // Assert that nothing has changed
        assertEquals("BitlyRequest(longURL=https://example.org/example)", actualToStringResult);
        assertEquals("https://example.org/example", actualBitlyRequest.getLongURL());
    }
}
