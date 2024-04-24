package com.websummarizer.Web.Summarizer.services.history;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.websummarizer.Web.Summarizer.model.History;
import com.websummarizer.Web.Summarizer.model.Provider;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.model.HistoryResAto;

import java.time.LocalDate;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

class HistoryMapperDiffblueTest {
    /**
     * Method under test: {@link HistoryMapper#mapHistoryEtoResAto(History)}
     */
    @Test
    void testMapHistoryEtoResAto() {
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

        History history = new History();
        history.setHistoryContent("Not all who wander are lost");
        history.setId(1L);
        history.setShort_link("Short link");
        history.setUploadTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        history.setUser(user);

        // Act
        HistoryResAto actualMapHistoryEtoResAtoResult = HistoryMapper.mapHistoryEtoResAto(history);

        // Assert
        assertEquals("1970-01-01", actualMapHistoryEtoResAtoResult.getUpload_time().toLocalDate().toString());
        assertEquals("Not all who wander are lost", actualMapHistoryEtoResAtoResult.getHistory_content());
        assertEquals("Short link", actualMapHistoryEtoResAtoResult.getShort_link());
        assertEquals(1L, actualMapHistoryEtoResAtoResult.getHID().longValue());
        assertEquals(1L, actualMapHistoryEtoResAtoResult.getUID().longValue());
    }
}
