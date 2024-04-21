package com.websummarizer.Web.Summarizer.controller;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websummarizer.Web.Summarizer.model.history.HistoriesResAto;
import com.websummarizer.Web.Summarizer.model.history.HistoryReqAto;
import com.websummarizer.Web.Summarizer.model.history.HistoryResAto;
import com.websummarizer.Web.Summarizer.services.history.HistoryService;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

@ContextConfiguration(classes = {HistoryController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class HistoryControllerDiffblueTest {
    @Autowired
    private HistoryController historyController;

    @MockBean
    private HistoryService historyService;

    /**
     * Method under test: {@link HistoryController#addHistory(HistoryReqAto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddHistory() throws Exception {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDateTime` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.websummarizer.Web.Summarizer.controller.history.HistoryReqAto["upload_time"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1308)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:732)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:772)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:479)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:318)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4719)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3964)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/v1/history")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        HistoryReqAto.HistoryReqAtoBuilder short_linkResult = HistoryReqAto.builder()
                .UID(1L)
                .history_content("Not all who wander are lost")
                .short_link("Short link");
        HistoryReqAto buildResult = short_linkResult.upload_time(LocalDate.of(1970, 1, 1).atStartOfDay()).build();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(buildResult));

        // Act
        MockMvcBuilders.standaloneSetup(historyController).build().perform(requestBuilder);
    }

    /**
     * Method under test: {@link HistoryController#deleteHistory(Long)}
     */
    @Test
    void testDeleteHistory() throws Exception {
        // Arrange
        doNothing().when(historyService).deleteHistory(anyLong());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/v1/history/{id}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(historyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link HistoryController#deleteHistory(Long)}
     */
    @Test
    void testDeleteHistory2() throws Exception {
        // Arrange
        doNothing().when(historyService).deleteHistory(anyLong());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/v1/history/{id}", 1L);
        requestBuilder.contentType("https://example.org/example");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(historyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link HistoryController#getHistory(Long)}
     */
    @Test
    void testGetHistory() throws Exception {
        // Arrange
        HistoryResAto.HistoryResAtoBuilder short_linkResult = HistoryResAto.builder()
                .HID(1L)
                .UID(1L)
                .history_content("Not all who wander are lost")
                .short_link("Short link");
        HistoryResAto buildResult = short_linkResult.upload_time(LocalDate.of(1970, 1, 1).atStartOfDay()).build();
        when(historyService.getHistory(anyLong())).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/history/{id}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(historyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"history_content\":\"Not all who wander are lost\",\"short_link\":\"Short link\",\"upload_time\":[1970,1,1,0"
                                        + ",0],\"hid\":1,\"uid\":1}"));
    }

    /**
     * Method under test: {@link HistoryController#findAllHistory()}
     */
    @Test
    void testFindAllHistory() throws Exception {
        // Arrange
        HistoriesResAto.HistoriesResAtoBuilder builderResult = HistoriesResAto.builder();
        HistoriesResAto buildResult = builderResult.histories(new ArrayList<>()).build();
        when(historyService.findAllHistory()).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/history");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(historyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"histories\":[],\"totalCount\":0}"));
    }

    /**
     * Method under test:
     * {@link HistoryController#updateHistory(Long, HistoryReqAto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateHistory() throws Exception {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDateTime` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.websummarizer.Web.Summarizer.controller.history.HistoryReqAto["upload_time"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1308)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:732)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:772)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:479)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:318)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4719)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3964)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/v1/history/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        HistoryReqAto.HistoryReqAtoBuilder short_linkResult = HistoryReqAto.builder()
                .UID(1L)
                .history_content("Not all who wander are lost")
                .short_link("Short link");
        HistoryReqAto buildResult = short_linkResult.upload_time(LocalDate.of(1970, 1, 1).atStartOfDay()).build();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(buildResult));

        // Act
        MockMvcBuilders.standaloneSetup(historyController).build().perform(requestBuilder);
    }
}
