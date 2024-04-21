package com.websummarizer.Web.Summarizer.controller;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websummarizer.Web.Summarizer.model.user.UserReqAto;
import com.websummarizer.Web.Summarizer.model.user.UserResAto;
import com.websummarizer.Web.Summarizer.model.user.UsersResAto;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserControllerDiffblueTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link UserController#createUser(UserReqAto)}
     */
    @Test
    void testCreateUser() throws Exception {
        // Arrange
        UserResAto buildResult = UserResAto.builder()
                .email("jane.doe@example.org")
                .first_name("Jane")
                .id(1L)
                .last_name("Doe")
                .password("iloveyou")
                .phone_number("6625550144")
                .build();
        when(userService.addUser(Mockito.<UserReqAto>any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        UserReqAto buildResult2 = UserReqAto.builder()
                .email("jane.doe@example.org")
                .first_name("Jane")
                .last_name("Doe")
                .password("iloveyou")
                .phone_number("6625550144")
                .build();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(buildResult2));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"first_name\":\"Jane\",\"last_name\":\"Doe\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\","
                                        + "\"phone_number\":\"6625550144\"}"));
    }

    /**
     * Method under test: {@link UserController#deleteUser(Long)}
     */
    @Test
    void testDeleteUser() throws Exception {
        // Arrange
        doNothing().when(userService).deleteUser(anyLong());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/v1/users/{id}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link UserController#deleteUser(Long)}
     */
    @Test
    void testDeleteUser2() throws Exception {
        // Arrange
        doNothing().when(userService).deleteUser(anyLong());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/v1/users/{id}", 1L);
        requestBuilder.contentType("https://example.org/example");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link UserController#getUser(Long)}
     */
    @Test
    void testGetUser() throws Exception {
        // Arrange
        UserResAto buildResult = UserResAto.builder()
                .email("jane.doe@example.org")
                .first_name("Jane")
                .id(1L)
                .last_name("Doe")
                .password("iloveyou")
                .phone_number("6625550144")
                .build();
        when(userService.getUser(anyLong())).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/users/{id}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"first_name\":\"Jane\",\"last_name\":\"Doe\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\","
                                        + "\"phone_number\":\"6625550144\"}"));
    }

    /**
     * Method under test: {@link UserController#updateUser(Long, UserReqAto)}
     */
    @Test
    void testUpdateUser() throws Exception {
        // Arrange
        UserResAto buildResult = UserResAto.builder()
                .email("jane.doe@example.org")
                .first_name("Jane")
                .id(1L)
                .last_name("Doe")
                .password("iloveyou")
                .phone_number("6625550144")
                .build();
        when(userService.updateUser(Mockito.<Long>any(), Mockito.<UserReqAto>any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/v1/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        UserReqAto buildResult2 = UserReqAto.builder()
                .email("jane.doe@example.org")
                .first_name("Jane")
                .last_name("Doe")
                .password("iloveyou")
                .phone_number("6625550144")
                .build();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(buildResult2));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"first_name\":\"Jane\",\"last_name\":\"Doe\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\","
                                        + "\"phone_number\":\"6625550144\"}"));
    }

    /**
     * Method under test: {@link UserController#findAllUsers()}
     */
    @Test
    void testFindAllUsers() throws Exception {
        // Arrange
        UsersResAto.UsersResAtoBuilder builderResult = UsersResAto.builder();
        UsersResAto buildResult = builderResult.users(new ArrayList<>()).build();
        when(userService.findAllUser()).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/users");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"users\":[],\"totalCount\":0}"));
    }
}
