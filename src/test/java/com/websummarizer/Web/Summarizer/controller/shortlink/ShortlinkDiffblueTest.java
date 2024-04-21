package com.websummarizer.Web.Summarizer.controller.shortlink;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.websummarizer.Web.Summarizer.controller.BitLyController;
import com.websummarizer.Web.Summarizer.controller.HistoryController;
import com.websummarizer.Web.Summarizer.controller.history.HistoryReqAto;
import com.websummarizer.Web.Summarizer.services.users.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {Shortlink.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ShortlinkDiffblueTest {
    @MockBean
    private BitLyController bitLyController;

    @MockBean
    private HistoryController historyController;

    @Autowired
    private Shortlink shortlink;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link Shortlink#Shortlink(String, String, HttpSession)}
     */
    @Test
    void testShortlink() {
        // Arrange
        when(historyController.addHistory(Mockito.<HistoryReqAto>any())).thenReturn(null);
        when(userService.getFoundId(Mockito.<String>any())).thenReturn(1L);

        // Act
        shortlink.Shortlink("https://example.org/example", "Output", new MockHttpSession());

        // Assert
        verify(historyController).addHistory(isA(HistoryReqAto.class));
        verify(userService).getFoundId(isNull());
    }

    /**
     * Method under test: {@link Shortlink#codeShort(String)}
     */
    @Test
    void testCodeShort() {
        // Test done manually as DiffBlue was unable to come up with a full test

        // Arrange and Act
        assertNotNull(shortlink.codeShort("https://example.org/example"));
    }

    /**
     * Method under test: {@link Shortlink#checkPassword(String)}
     */
    @Test
    @Disabled
    void testCheckPassword() {
        // Currently disable because this method is still a WIP
        // Arrange, Act and Assert
        assertFalse(shortlink.checkPassword("iloveyou"));
    }
}
