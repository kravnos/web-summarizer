package com.websummarizer.Web.Summarizer.controller;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import com.websummarizer.Web.Summarizer.model.History;
import com.websummarizer.Web.Summarizer.model.Provider;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.services.UserServiceImpl;
import com.websummarizer.Web.Summarizer.services.history.HistoryService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

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
class ShortLinkControllerTest {
    @MockBean
    private HistoryService historyService;

    @Autowired
    private ShortLinkController shortLinkController;

    @MockBean
    private UserServiceImpl userServiceImpl;

    /**
     * Method under test: {@link ShortLinkController#getShortLink(String, Model)}
     */
    @Test
    void testGetShortLink() throws Exception {
        // Arrange
        when(historyService.getShortLink(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/{shortlink}", "Shortlink");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(shortLinkController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("histories", "user"))
                .andExpect(MockMvcResultMatchers.view().name("api/shortlink"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("api/shortlink"));
    }

    /**
     * Method under test: {@link ShortLinkController#getShortLink(String, Model)}
     */
    @Test
    void testGetShortLink2() throws Exception {
        // Arrange
        User user = new User();
        user.setAuthorities(new HashSet<>());
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(1L);
        user.setLast_name("Doe");
        user.setLlmSelection("User");
        user.setPassword("iloveyou");
        user.setPhone_number("6625550144");
        user.setProvider(Provider.LOCAL);
        user.setRequest_token("ABC123");

        History history = new History();
        history.setHistoryContent("Not all who wander are lost");
        history.setId(1L);
        history.setInputText("User");
        history.setShort_link("User");
        history.setUploadTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        history.setUser(user);

        ArrayList<History> historyList = new ArrayList<>();
        historyList.add(history);
        when(historyService.getShortLink(Mockito.<String>any())).thenReturn(historyList);
        when(userServiceImpl.getUserName(anyLong())).thenReturn("janedoe");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/{shortlink}", "Shortlink");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(shortLinkController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("histories", "user"))
                .andExpect(MockMvcResultMatchers.view().name("api/shortlink"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("api/shortlink"));
    }
}
