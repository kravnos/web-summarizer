package com.websummarizer.Web.Summarizer.controller.history;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class HistoryReqAtoDiffblueTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link HistoryReqAto#toString()}
     *   <li>{@link HistoryReqAto#getHistory_content()}
     *   <li>{@link HistoryReqAto#getLinkURL()}
     *   <li>{@link HistoryReqAto#getShort_link()}
     *   <li>{@link HistoryReqAto#getUID()}
     *   <li>{@link HistoryReqAto#getUpload_time()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange
        HistoryReqAto.HistoryReqAtoBuilder short_linkResult = HistoryReqAto.builder()
                .UID(1L)
                .history_content("Not all who wander are lost")
                .linkURL("https://example.org/example")
                .short_link("Short link");
        HistoryReqAto buildResult = short_linkResult.upload_time(LocalDate.of(1970, 1, 1).atStartOfDay()).build();

        // Act
        String actualToStringResult = buildResult.toString();
        String actualHistory_content = buildResult.getHistory_content();
        String actualLinkURL = buildResult.getLinkURL();
        String actualShort_link = buildResult.getShort_link();
        Long actualUID = buildResult.getUID();
        buildResult.getUpload_time();

        // Assert
        assertEquals(
                "HistoryReqAto(UID=1, history_content=Not all who wander are lost, linkURL=https://example.org/example,"
                        + " short_link=Short link, upload_time=1970-01-01T00:00)",
                actualToStringResult);
        assertEquals("Not all who wander are lost", actualHistory_content);
        assertEquals("Short link", actualShort_link);
        assertEquals("https://example.org/example", actualLinkURL);
        assertEquals(1L, actualUID.longValue());
    }

    /**
     * Method under test:
     * {@link HistoryReqAto#HistoryReqAto(Long, String, String, String, LocalDateTime)}
     */
    @Test
    void testNewHistoryReqAto() {
        // Arrange and Act
        HistoryReqAto actualHistoryReqAto = new HistoryReqAto(1L, "Not all who wander are lost",
                "https://example.org/example", "Short link", LocalDate.of(1970, 1, 1).atStartOfDay());

        // Assert
        assertEquals("1970-01-01", actualHistoryReqAto.getUpload_time().toLocalDate().toString());
        assertEquals("Not all who wander are lost", actualHistoryReqAto.getHistory_content());
        assertEquals("Short link", actualHistoryReqAto.getShort_link());
        assertEquals("https://example.org/example", actualHistoryReqAto.getLinkURL());
        assertEquals(1L, actualHistoryReqAto.getUID().longValue());
    }
}
