package com.websummarizer.Web.Summarizer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

class HistoryDiffblueTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link History#History()}
     *   <li>{@link History#setHistoryContent(String)}
     *   <li>{@link History#setId(Long)}
     *   <li>{@link History#setLinkURL(String)}
     *   <li>{@link History#setShortLink(String)}
     *   <li>{@link History#setUploadTime(LocalDateTime)}
     *   <li>{@link History#setUser(User)}
     *   <li>{@link History#getHistoryContent()}
     *   <li>{@link History#getId()}
     *   <li>{@link History#getLinkURL()}
     *   <li>{@link History#getShortLink()}
     *   <li>{@link History#getUploadTime()}
     *   <li>{@link History#getUser()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange and Act
        History actualHistory = new History();
        actualHistory.setHistoryContent("Not all who wander are lost");
        actualHistory.setId(1L);
        actualHistory.setLinkURL("https://example.org/example");
        actualHistory.setShortLink("Short Link");
        LocalDateTime uploadTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        actualHistory.setUploadTime(uploadTime);
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
        actualHistory.setUser(user);
        String actualHistoryContent = actualHistory.getHistoryContent();
        Long actualId = actualHistory.getId();
        String actualLinkURL = actualHistory.getLinkURL();
        String actualShortLink = actualHistory.getShortLink();
        LocalDateTime actualUploadTime = actualHistory.getUploadTime();
        User actualUser = actualHistory.getUser();

        // Assert that nothing has changed
        assertEquals("Not all who wander are lost", actualHistoryContent);
        assertEquals("Short Link", actualShortLink);
        assertEquals("https://example.org/example", actualLinkURL);
        assertEquals(1L, actualId.longValue());
        assertSame(user, actualUser);
        assertSame(uploadTime, actualUploadTime);
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>
     * {@link History#History(Long, User, String, String, String, LocalDateTime)}
     *   <li>{@link History#setHistoryContent(String)}
     *   <li>{@link History#setId(Long)}
     *   <li>{@link History#setLinkURL(String)}
     *   <li>{@link History#setShortLink(String)}
     *   <li>{@link History#setUploadTime(LocalDateTime)}
     *   <li>{@link History#setUser(User)}
     *   <li>{@link History#getHistoryContent()}
     *   <li>{@link History#getId()}
     *   <li>{@link History#getLinkURL()}
     *   <li>{@link History#getShortLink()}
     *   <li>{@link History#getUploadTime()}
     *   <li>{@link History#getUser()}
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
        History actualHistory = new History(1L, user, "Not all who wander are lost", "https://example.org/example",
                "Short Link", LocalDate.of(1970, 1, 1).atStartOfDay());
        actualHistory.setHistoryContent("Not all who wander are lost");
        actualHistory.setId(1L);
        actualHistory.setLinkURL("https://example.org/example");
        actualHistory.setShortLink("Short Link");
        LocalDateTime uploadTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        actualHistory.setUploadTime(uploadTime);
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
        actualHistory.setUser(user2);
        String actualHistoryContent = actualHistory.getHistoryContent();
        Long actualId = actualHistory.getId();
        String actualLinkURL = actualHistory.getLinkURL();
        String actualShortLink = actualHistory.getShortLink();
        LocalDateTime actualUploadTime = actualHistory.getUploadTime();
        User actualUser = actualHistory.getUser();

        // Assert that nothing has changed
        assertEquals("Not all who wander are lost", actualHistoryContent);
        assertEquals("Short Link", actualShortLink);
        assertEquals("https://example.org/example", actualLinkURL);
        assertEquals(1L, actualId.longValue());
        assertEquals(user, actualUser);
        assertSame(user2, actualUser);
        assertSame(uploadTime, actualUploadTime);
    }
}
