package com.websummarizer.Web.Summarizer.controller.history;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class HistoryResAtoDiffblueTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link HistoryResAto#toString()}
     *   <li>{@link HistoryResAto#getHID()}
     *   <li>{@link HistoryResAto#getHistory_content()}
     *   <li>{@link HistoryResAto#getLinkURL()}
     *   <li>{@link HistoryResAto#getShort_link()}
     *   <li>{@link HistoryResAto#getUID()}
     *   <li>{@link HistoryResAto#getUpload_time()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange
        HistoryResAto.HistoryResAtoBuilder short_linkResult = HistoryResAto.builder()
                .HID(1L)
                .UID(1L)
                .history_content("Not all who wander are lost")
                .linkURL("https://example.org/example")
                .short_link("Short link");
        HistoryResAto buildResult = short_linkResult.upload_time(LocalDate.of(1970, 1, 1).atStartOfDay()).build();

        // Act
        String actualToStringResult = buildResult.toString();
        Long actualHID = buildResult.getHID();
        String actualHistory_content = buildResult.getHistory_content();
        String actualLinkURL = buildResult.getLinkURL();
        String actualShort_link = buildResult.getShort_link();
        Long actualUID = buildResult.getUID();
        buildResult.getUpload_time();

        // Assert
        assertEquals(
                "HistoryResAto(HID=1, UID=1, history_content=Not all who wander are lost, linkURL=https://example.org/example,"
                        + " short_link=Short link, upload_time=1970-01-01T00:00)",
                actualToStringResult);
        assertEquals("Not all who wander are lost", actualHistory_content);
        assertEquals("Short link", actualShort_link);
        assertEquals("https://example.org/example", actualLinkURL);
        assertEquals(1L, actualHID.longValue());
        assertEquals(1L, actualUID.longValue());
    }

    /**
     * Method under test:
     * {@link HistoryResAto#HistoryResAto(Long, Long, String, String, String, LocalDateTime)}
     */
    @Test
    void testNewHistoryResAto() {
        // Arrange and Act
        HistoryResAto actualHistoryResAto = new HistoryResAto(1L, 1L, "Not all who wander are lost",
                "https://example.org/example", "Short link", LocalDate.of(1970, 1, 1).atStartOfDay());

        // Assert
        assertEquals("1970-01-01", actualHistoryResAto.getUpload_time().toLocalDate().toString());
        assertEquals("Not all who wander are lost", actualHistoryResAto.getHistory_content());
        assertEquals("Short link", actualHistoryResAto.getShort_link());
        assertEquals("https://example.org/example", actualHistoryResAto.getLinkURL());
        assertEquals(1L, actualHistoryResAto.getHID().longValue());
        assertEquals(1L, actualHistoryResAto.getUID().longValue());
    }
}
