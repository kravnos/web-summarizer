package com.websummarizer.Web.Summarizer.controller;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import com.websummarizer.Web.Summarizer.controller.history.HistoryResAto;
import com.websummarizer.Web.Summarizer.services.history.HistoryService;
import com.websummarizer.Web.Summarizer.services.users.UserService;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

@ContextConfiguration(classes = {ShortLinkController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ShortLinkControllerDiffblueTest {
    @MockBean
    private HistoryService historyService;

    @Autowired
    private ShortLinkController shortLinkController;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link ShortLinkController#getShortLink(String, Model)}
     */
    @Test
    void testGetShortLink() throws Exception {
        // Arrange
        HistoryResAto.HistoryResAtoBuilder short_linkResult = HistoryResAto.builder()
                .HID(1L)
                .UID(1L)
                .history_content("Not all who wander are lost")
                .linkURL("https://example.org/example")
                .short_link("Short link");
        HistoryResAto buildResult = short_linkResult.upload_time(LocalDate.of(1970, 1, 1).atStartOfDay()).build();
        when(historyService.getShortLink(Mockito.<String>any())).thenReturn(buildResult);
        when(userService.getUserName(anyLong())).thenReturn("janedoe");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/{shortlink}", "Shortlink");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(shortLinkController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("content", "user"))
                .andExpect(MockMvcResultMatchers.view().name("shortContent/index"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("shortContent/index"));
    }
}
