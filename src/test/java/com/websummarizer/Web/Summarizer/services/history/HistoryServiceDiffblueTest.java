//package com.websummarizer.Web.Summarizer.services.history;
//
//import com.websummarizer.Web.Summarizer.common.exceptions.CCNotFoundException;
//import com.websummarizer.Web.Summarizer.controller.history.HistoriesResAto;
//import com.websummarizer.Web.Summarizer.controller.history.HistoryReqAto;
//import com.websummarizer.Web.Summarizer.controller.history.HistoryResAto;
//import com.websummarizer.Web.Summarizer.model.History;
//import com.websummarizer.Web.Summarizer.model.User;
//import com.websummarizer.Web.Summarizer.repo.UserRepo;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.aot.DisabledInAotMode;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.ArgumentMatchers.isA;
//import static org.mockito.Mockito.*;
//
//@ContextConfiguration(classes = {HistoryService.class})
//@ExtendWith(SpringExtension.class)
//@DisabledInAotMode
//class HistoryServiceDiffblueTest {
//  @MockBean
//  private HistoryRepo historyRepository;
//
//  @Autowired
//  private HistoryService historyService;
//
//  @MockBean
//  private UserRepo userRepository;
//
//  /**
//   * Method under test: {@link HistoryService#addHistory(HistoryReqAto)}
//   */
//  @Test
//  void testAddHistory() {
//    // Arrange
//    User user = new User();
//    user.setEmail("jane.doe@example.org");
//    user.setFirst_name("Jane");
//    user.setId(1L);
//    user.setLast_name("Doe");
//    user.setPassword("iloveyou");
//    user.setPhone_number("6625550144");
//
//    History history = new History();
//    history.setHistoryContent("Not all who wander are lost");
//    history.setId(1L);
//    history.setShortLink("Short Link");
//    history.setUploadTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//    history.setUser(user);
//    when(historyRepository.save(Mockito.<History>any())).thenReturn(history);
//
//    User user2 = new User();
//    user2.setEmail("jane.doe@example.org");
//    user2.setFirst_name("Jane");
//    user2.setId(1L);
//    user2.setLast_name("Doe");
//    user2.setPassword("iloveyou");
//    user2.setPhone_number("6625550144");
//    Optional<User> ofResult = Optional.of(user2);
//    when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
//    HistoryReqAto.HistoryReqAtoBuilder short_linkResult = HistoryReqAto.builder()
//            .UID(1L)
//            .history_content("Not all who wander are lost")
//            .short_link("Short link");
//    HistoryReqAto reqSto = short_linkResult.upload_time(LocalDate.of(1970, 1, 1).atStartOfDay()).build();
//
//    // Act
//    HistoryResAto actualAddHistoryResult = historyService.addHistory(reqSto);
//
//    // Assert
//    verify(userRepository).findById(isA(Long.class));
//    verify(historyRepository).save(isA(History.class));
//    assertEquals("1970-01-01", actualAddHistoryResult.getUpload_time().toLocalDate().toString());
//    assertEquals("Not all who wander are lost", actualAddHistoryResult.getHistory_content());
//    assertEquals("Short Link", actualAddHistoryResult.getShort_link());
//    assertEquals(1L, actualAddHistoryResult.getHID().longValue());
//    assertEquals(1L, actualAddHistoryResult.getUID().longValue());
//  }
//
//  /**
//   * Method under test: {@link HistoryService#addHistory(HistoryReqAto)}
//   */
//  @Test
//  void testAddHistory2() {
//    // Arrange
//    when(historyRepository.save(Mockito.<History>any())).thenThrow(new CCNotFoundException());
//
//    User user = new User();
//    user.setEmail("jane.doe@example.org");
//    user.setFirst_name("Jane");
//    user.setId(1L);
//    user.setLast_name("Doe");
//    user.setPassword("iloveyou");
//    user.setPhone_number("6625550144");
//    Optional<User> ofResult = Optional.of(user);
//    when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
//    HistoryReqAto.HistoryReqAtoBuilder short_linkResult = HistoryReqAto.builder()
//            .UID(1L)
//            .history_content("Not all who wander are lost")
//            .short_link("Short link");
//    HistoryReqAto reqSto = short_linkResult.upload_time(LocalDate.of(1970, 1, 1).atStartOfDay()).build();
//
//    // Act and Assert
//    assertThrows(CCNotFoundException.class, () -> historyService.addHistory(reqSto));
//    verify(userRepository).findById(isA(Long.class));
//    verify(historyRepository).save(isA(History.class));
//  }
//
//  /**
//   * Method under test: {@link HistoryService#addHistory(HistoryReqAto)}
//   */
//  @Test
//  void testAddHistory3() {
//    // Arrange
//    User user = new User();
//    user.setEmail("jane.doe@example.org");
//    user.setFirst_name("Jane");
//    user.setId(1L);
//    user.setLast_name("Doe");
//    user.setPassword("iloveyou");
//    user.setPhone_number("6625550144");
//
//    User user2 = new User();
//    user2.setEmail("jane.doe@example.org");
//    user2.setFirst_name("Jane");
//    user2.setId(1L);
//    user2.setLast_name("Doe");
//    user2.setPassword("iloveyou");
//    user2.setPhone_number("6625550144");
//    History history = mock(History.class);
//    when(history.getUploadTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
//    when(history.getShortLink()).thenReturn("Short Link");
//    when(history.getHistoryContent()).thenReturn("Not all who wander are lost");
//    when(history.getUser()).thenReturn(user2);
//    when(history.getId()).thenReturn(1L);
//    doNothing().when(history).setHistoryContent(Mockito.<String>any());
//    doNothing().when(history).setId(Mockito.<Long>any());
//    doNothing().when(history).setShortLink(Mockito.<String>any());
//    doNothing().when(history).setUploadTime(Mockito.<LocalDateTime>any());
//    doNothing().when(history).setUser(Mockito.<User>any());
//    history.setHistoryContent("Not all who wander are lost");
//    history.setId(1L);
//    history.setShortLink("Short Link");
//    history.setUploadTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//    history.setUser(user);
//    when(historyRepository.save(Mockito.<History>any())).thenReturn(history);
//
//    User user3 = new User();
//    user3.setEmail("jane.doe@example.org");
//    user3.setFirst_name("Jane");
//    user3.setId(1L);
//    user3.setLast_name("Doe");
//    user3.setPassword("iloveyou");
//    user3.setPhone_number("6625550144");
//    Optional<User> ofResult = Optional.of(user3);
//    when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
//    HistoryReqAto.HistoryReqAtoBuilder short_linkResult = HistoryReqAto.builder()
//            .UID(1L)
//            .history_content("Not all who wander are lost")
//            .short_link("Short link");
//    HistoryReqAto reqSto = short_linkResult.upload_time(LocalDate.of(1970, 1, 1).atStartOfDay()).build();
//
//    // Act
//    HistoryResAto actualAddHistoryResult = historyService.addHistory(reqSto);
//
//    // Assert
//    verify(history).getHistoryContent();
//    verify(history).getId();
//    verify(history).getShortLink();
//    verify(history).getUploadTime();
//    verify(history).getUser();
//    verify(history).setHistoryContent(eq("Not all who wander are lost"));
//    verify(history).setId(isA(Long.class));
//    verify(history).setShortLink(eq("Short Link"));
//    verify(history).setUploadTime(isA(LocalDateTime.class));
//    verify(history).setUser(isA(User.class));
//    verify(userRepository).findById(isA(Long.class));
//    verify(historyRepository).save(isA(History.class));
//    assertEquals("1970-01-01", actualAddHistoryResult.getUpload_time().toLocalDate().toString());
//    assertEquals("Not all who wander are lost", actualAddHistoryResult.getHistory_content());
//    assertEquals("Short Link", actualAddHistoryResult.getShort_link());
//    assertEquals(1L, actualAddHistoryResult.getHID().longValue());
//    assertEquals(1L, actualAddHistoryResult.getUID().longValue());
//  }
//
//  /**
//   * Method under test: {@link HistoryService#addHistory(HistoryReqAto)}
//   */
//  @Test
//  void testAddHistory4() {
//    // Arrange
//    User user = new User();
//    user.setEmail("jane.doe@example.org");
//    user.setFirst_name("Jane");
//    user.setId(1L);
//    user.setLast_name("Doe");
//    user.setPassword("iloveyou");
//    user.setPhone_number("6625550144");
//
//    User user2 = new User();
//    user2.setEmail("jane.doe@example.org");
//    user2.setFirst_name("Jane");
//    user2.setId(1L);
//    user2.setLast_name("Doe");
//    user2.setPassword("iloveyou");
//    user2.setPhone_number("6625550144");
//    History history = mock(History.class);
//    when(history.getUploadTime()).thenThrow(new CCNotFoundException());
//    when(history.getShortLink()).thenReturn("Short Link");
//    when(history.getHistoryContent()).thenReturn("Not all who wander are lost");
//    when(history.getUser()).thenReturn(user2);
//    when(history.getId()).thenReturn(1L);
//    doNothing().when(history).setHistoryContent(Mockito.<String>any());
//    doNothing().when(history).setId(Mockito.<Long>any());
//    doNothing().when(history).setShortLink(Mockito.<String>any());
//    doNothing().when(history).setUploadTime(Mockito.<LocalDateTime>any());
//    doNothing().when(history).setUser(Mockito.<User>any());
//    history.setHistoryContent("Not all who wander are lost");
//    history.setId(1L);
//    history.setShortLink("Short Link");
//    history.setUploadTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//    history.setUser(user);
//    when(historyRepository.save(Mockito.<History>any())).thenReturn(history);
//
//    User user3 = new User();
//    user3.setEmail("jane.doe@example.org");
//    user3.setFirst_name("Jane");
//    user3.setId(1L);
//    user3.setLast_name("Doe");
//    user3.setPassword("iloveyou");
//    user3.setPhone_number("6625550144");
//    Optional<User> ofResult = Optional.of(user3);
//    when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
//    HistoryReqAto.HistoryReqAtoBuilder short_linkResult = HistoryReqAto.builder()
//            .UID(1L)
//            .history_content("Not all who wander are lost")
//            .short_link("Short link");
//    HistoryReqAto reqSto = short_linkResult.upload_time(LocalDate.of(1970, 1, 1).atStartOfDay()).build();
//
//    // Act and Assert
//    assertThrows(CCNotFoundException.class, () -> historyService.addHistory(reqSto));
//    verify(history).getHistoryContent();
//    verify(history).getId();
//    verify(history).getShortLink();
//    verify(history).getUploadTime();
//    verify(history).getUser();
//    verify(history).setHistoryContent(eq("Not all who wander are lost"));
//    verify(history).setId(isA(Long.class));
//    verify(history).setShortLink(eq("Short Link"));
//    verify(history).setUploadTime(isA(LocalDateTime.class));
//    verify(history).setUser(isA(User.class));
//    verify(userRepository).findById(isA(Long.class));
//    verify(historyRepository).save(isA(History.class));
//  }
//
//  /**
//   * Method under test: {@link HistoryService#addHistory(HistoryReqAto)}
//   */
//  @Test
//  void testAddHistory5() {
//    // Arrange
//    User user = new User();
//    user.setEmail("jane.doe@example.org");
//    user.setFirst_name("Jane");
//    user.setId(1L);
//    user.setLast_name("Doe");
//    user.setPassword("iloveyou");
//    user.setPhone_number("6625550144");
//    History history = mock(History.class);
//    doNothing().when(history).setHistoryContent(Mockito.<String>any());
//    doNothing().when(history).setId(Mockito.<Long>any());
//    doNothing().when(history).setShortLink(Mockito.<String>any());
//    doNothing().when(history).setUploadTime(Mockito.<LocalDateTime>any());
//    doNothing().when(history).setUser(Mockito.<User>any());
//    history.setHistoryContent("Not all who wander are lost");
//    history.setId(1L);
//    history.setShortLink("Short Link");
//    history.setUploadTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//    history.setUser(user);
//    Optional<User> emptyResult = Optional.empty();
//    when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
//    HistoryReqAto.HistoryReqAtoBuilder short_linkResult = HistoryReqAto.builder()
//            .UID(1L)
//            .history_content("Not all who wander are lost")
//            .short_link("Short link");
//    HistoryReqAto reqSto = short_linkResult.upload_time(LocalDate.of(1970, 1, 1).atStartOfDay()).build();
//
//    // Act and Assert
//    assertThrows(CCNotFoundException.class, () -> historyService.addHistory(reqSto));
//    verify(history).setHistoryContent(eq("Not all who wander are lost"));
//    verify(history).setId(isA(Long.class));
//    verify(history).setShortLink(eq("Short Link"));
//    verify(history).setUploadTime(isA(LocalDateTime.class));
//    verify(history).setUser(isA(User.class));
//    verify(userRepository).findById(isA(Long.class));
//  }
//
//  /**
//   * Method under test: {@link HistoryService#getHistory(long)}
//   */
//  @Test
//  void testGetHistory() {
//    // Arrange
//    User user = new User();
//    user.setEmail("jane.doe@example.org");
//    user.setFirst_name("Jane");
//    user.setId(1L);
//    user.setLast_name("Doe");
//    user.setPassword("iloveyou");
//    user.setPhone_number("6625550144");
//
//    History history = new History();
//    history.setHistoryContent("Not all who wander are lost");
//    history.setId(1L);
//    history.setShortLink("Short Link");
//    history.setUploadTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//    history.setUser(user);
//    Optional<History> ofResult = Optional.of(history);
//    when(historyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
//
//    // Act
//    HistoryResAto actualHistory = historyService.getHistory(1L);
//
//    // Assert
//    verify(historyRepository).findById(isA(Long.class));
//    assertEquals("1970-01-01", actualHistory.getUpload_time().toLocalDate().toString());
//    assertEquals("Not all who wander are lost", actualHistory.getHistory_content());
//    assertEquals("Short Link", actualHistory.getShort_link());
//    assertEquals(1L, actualHistory.getHID().longValue());
//    assertEquals(1L, actualHistory.getUID().longValue());
//  }
//
//  /**
//   * Method under test: {@link HistoryService#getHistory(long)}
//   */
//  @Test
//  void testGetHistory2() {
//    // Arrange
//    User user = new User();
//    user.setEmail("jane.doe@example.org");
//    user.setFirst_name("Jane");
//    user.setId(1L);
//    user.setLast_name("Doe");
//    user.setPassword("iloveyou");
//    user.setPhone_number("6625550144");
//
//    User user2 = new User();
//    user2.setEmail("jane.doe@example.org");
//    user2.setFirst_name("Jane");
//    user2.setId(1L);
//    user2.setLast_name("Doe");
//    user2.setPassword("iloveyou");
//    user2.setPhone_number("6625550144");
//    History history = mock(History.class);
//    when(history.getUploadTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
//    when(history.getShortLink()).thenReturn("Short Link");
//    when(history.getHistoryContent()).thenReturn("Not all who wander are lost");
//    when(history.getUser()).thenReturn(user2);
//    when(history.getId()).thenReturn(1L);
//    doNothing().when(history).setHistoryContent(Mockito.<String>any());
//    doNothing().when(history).setId(Mockito.<Long>any());
//    doNothing().when(history).setShortLink(Mockito.<String>any());
//    doNothing().when(history).setUploadTime(Mockito.<LocalDateTime>any());
//    doNothing().when(history).setUser(Mockito.<User>any());
//    history.setHistoryContent("Not all who wander are lost");
//    history.setId(1L);
//    history.setShortLink("Short Link");
//    history.setUploadTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//    history.setUser(user);
//    Optional<History> ofResult = Optional.of(history);
//    when(historyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
//
//    // Act
//    HistoryResAto actualHistory = historyService.getHistory(1L);
//
//    // Assert
//    verify(history).getHistoryContent();
//    verify(history).getId();
//    verify(history).getShortLink();
//    verify(history).getUploadTime();
//    verify(history).getUser();
//    verify(history).setHistoryContent(eq("Not all who wander are lost"));
//    verify(history).setId(isA(Long.class));
//    verify(history).setShortLink(eq("Short Link"));
//    verify(history).setUploadTime(isA(LocalDateTime.class));
//    verify(history).setUser(isA(User.class));
//    verify(historyRepository).findById(isA(Long.class));
//    assertEquals("1970-01-01", actualHistory.getUpload_time().toLocalDate().toString());
//    assertEquals("Not all who wander are lost", actualHistory.getHistory_content());
//    assertEquals("Short Link", actualHistory.getShort_link());
//    assertEquals(1L, actualHistory.getHID().longValue());
//    assertEquals(1L, actualHistory.getUID().longValue());
//  }
//
//  /**
//   * Method under test: {@link HistoryService#getHistory(long)}
//   */
//  @Test
//  void testGetHistory3() {
//    // Arrange
//    User user = new User();
//    user.setEmail("jane.doe@example.org");
//    user.setFirst_name("Jane");
//    user.setId(1L);
//    user.setLast_name("Doe");
//    user.setPassword("iloveyou");
//    user.setPhone_number("6625550144");
//
//    User user2 = new User();
//    user2.setEmail("jane.doe@example.org");
//    user2.setFirst_name("Jane");
//    user2.setId(1L);
//    user2.setLast_name("Doe");
//    user2.setPassword("iloveyou");
//    user2.setPhone_number("6625550144");
//    History history = mock(History.class);
//    when(history.getUploadTime()).thenThrow(new CCNotFoundException());
//    when(history.getShortLink()).thenReturn("Short Link");
//    when(history.getHistoryContent()).thenReturn("Not all who wander are lost");
//    when(history.getUser()).thenReturn(user2);
//    when(history.getId()).thenReturn(1L);
//    doNothing().when(history).setHistoryContent(Mockito.<String>any());
//    doNothing().when(history).setId(Mockito.<Long>any());
//    doNothing().when(history).setShortLink(Mockito.<String>any());
//    doNothing().when(history).setUploadTime(Mockito.<LocalDateTime>any());
//    doNothing().when(history).setUser(Mockito.<User>any());
//    history.setHistoryContent("Not all who wander are lost");
//    history.setId(1L);
//    history.setShortLink("Short Link");
//    history.setUploadTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//    history.setUser(user);
//    Optional<History> ofResult = Optional.of(history);
//    when(historyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
//
//    // Act and Assert
//    assertThrows(CCNotFoundException.class, () -> historyService.getHistory(1L));
//    verify(history).getHistoryContent();
//    verify(history).getId();
//    verify(history).getShortLink();
//    verify(history).getUploadTime();
//    verify(history).getUser();
//    verify(history).setHistoryContent(eq("Not all who wander are lost"));
//    verify(history).setId(isA(Long.class));
//    verify(history).setShortLink(eq("Short Link"));
//    verify(history).setUploadTime(isA(LocalDateTime.class));
//    verify(history).setUser(isA(User.class));
//    verify(historyRepository).findById(isA(Long.class));
//  }
//
//  /**
//   * Method under test: {@link HistoryService#updateHistory(Long, HistoryReqAto)}
//   */
//  @Test
//  void testUpdateHistory() {
//    // Arrange
//    when(historyRepository.findById(Mockito.<Long>any())).thenThrow(new CCNotFoundException());
//
//    // Act and Assert
//    assertThrows(CCNotFoundException.class, () -> historyService.updateHistory(1L, null));
//    verify(historyRepository).findById(isA(Long.class));
//  }
//
//  /**
//   * Method under test: {@link HistoryService#updateHistory(Long, HistoryReqAto)}
//   */
//  @Test
//  void testUpdateHistory2() {
//    // Arrange
//    User user = new User();
//    user.setEmail("jane.doe@example.org");
//    user.setFirst_name("Jane");
//    user.setId(1L);
//    user.setLast_name("Doe");
//    user.setPassword("iloveyou");
//    user.setPhone_number("6625550144");
//
//    History history = new History();
//    history.setHistoryContent("Not all who wander are lost");
//    history.setId(1L);
//    history.setShortLink("Short Link");
//    history.setUploadTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//    history.setUser(user);
//    Optional<History> ofResult = Optional.of(history);
//
//    User user2 = new User();
//    user2.setEmail("jane.doe@example.org");
//    user2.setFirst_name("Jane");
//    user2.setId(1L);
//    user2.setLast_name("Doe");
//    user2.setPassword("iloveyou");
//    user2.setPhone_number("6625550144");
//
//    History history2 = new History();
//    history2.setHistoryContent("Not all who wander are lost");
//    history2.setId(1L);
//    history2.setShortLink("Short Link");
//    history2.setUploadTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//    history2.setUser(user2);
//    when(historyRepository.save(Mockito.<History>any())).thenReturn(history2);
//    when(historyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
//
//    User user3 = new User();
//    user3.setEmail("jane.doe@example.org");
//    user3.setFirst_name("Jane");
//    user3.setId(1L);
//    user3.setLast_name("Doe");
//    user3.setPassword("iloveyou");
//    user3.setPhone_number("6625550144");
//    Optional<User> ofResult2 = Optional.of(user3);
//    when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
//    HistoryReqAto.HistoryReqAtoBuilder short_linkResult = HistoryReqAto.builder()
//            .UID(1L)
//            .history_content("Not all who wander are lost")
//            .short_link("Short link");
//    HistoryReqAto historyReqAto = short_linkResult.upload_time(LocalDate.of(1970, 1, 1).atStartOfDay()).build();
//
//    // Act
//    HistoryResAto actualUpdateHistoryResult = historyService.updateHistory(1L, historyReqAto);
//
//    // Assert
//    verify(historyRepository).findById(isA(Long.class));
//    verify(userRepository).findById(isA(Long.class));
//    verify(historyRepository).save(isA(History.class));
//    assertEquals("1970-01-01", actualUpdateHistoryResult.getUpload_time().toLocalDate().toString());
//    assertEquals("Not all who wander are lost", actualUpdateHistoryResult.getHistory_content());
//    assertEquals("Short link", actualUpdateHistoryResult.getShort_link());
//    assertEquals(1L, actualUpdateHistoryResult.getHID().longValue());
//    assertEquals(1L, actualUpdateHistoryResult.getUID().longValue());
//  }
//
//  /**
//   * Method under test: {@link HistoryService#updateHistory(Long, HistoryReqAto)}
//   */
//  @Test
//  void testUpdateHistory3() {
//    // Arrange
//    User user = new User();
//    user.setEmail("jane.doe@example.org");
//    user.setFirst_name("Jane");
//    user.setId(1L);
//    user.setLast_name("Doe");
//    user.setPassword("iloveyou");
//    user.setPhone_number("6625550144");
//
//    History history = new History();
//    history.setHistoryContent("Not all who wander are lost");
//    history.setId(1L);
//    history.setShortLink("Short Link");
//    history.setUploadTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//    history.setUser(user);
//    Optional<History> ofResult = Optional.of(history);
//    when(historyRepository.save(Mockito.<History>any())).thenThrow(new CCNotFoundException());
//    when(historyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
//
//    User user2 = new User();
//    user2.setEmail("jane.doe@example.org");
//    user2.setFirst_name("Jane");
//    user2.setId(1L);
//    user2.setLast_name("Doe");
//    user2.setPassword("iloveyou");
//    user2.setPhone_number("6625550144");
//    Optional<User> ofResult2 = Optional.of(user2);
//    when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
//    HistoryReqAto.HistoryReqAtoBuilder short_linkResult = HistoryReqAto.builder()
//            .UID(1L)
//            .history_content("Not all who wander are lost")
//            .short_link("Short link");
//    HistoryReqAto historyReqAto = short_linkResult.upload_time(LocalDate.of(1970, 1, 1).atStartOfDay()).build();
//
//    // Act and Assert
//    assertThrows(CCNotFoundException.class, () -> historyService.updateHistory(1L, historyReqAto));
//    verify(historyRepository).findById(isA(Long.class));
//    verify(userRepository).findById(isA(Long.class));
//    verify(historyRepository).save(isA(History.class));
//  }
//
//  /**
//   * Method under test: {@link HistoryService#updateHistory(Long, HistoryReqAto)}
//   */
//  @Test
//  void testUpdateHistory4() {
//    // Arrange
//    User user = new User();
//    user.setEmail("jane.doe@example.org");
//    user.setFirst_name("Jane");
//    user.setId(1L);
//    user.setLast_name("Doe");
//    user.setPassword("iloveyou");
//    user.setPhone_number("6625550144");
//
//    User user2 = new User();
//    user2.setEmail("jane.doe@example.org");
//    user2.setFirst_name("Jane");
//    user2.setId(1L);
//    user2.setLast_name("Doe");
//    user2.setPassword("iloveyou");
//    user2.setPhone_number("6625550144");
//    History history = mock(History.class);
//    when(history.getUploadTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
//    when(history.getShortLink()).thenReturn("Short Link");
//    when(history.getHistoryContent()).thenReturn("Not all who wander are lost");
//    when(history.getUser()).thenReturn(user2);
//    when(history.getId()).thenReturn(1L);
//    doNothing().when(history).setHistoryContent(Mockito.<String>any());
//    doNothing().when(history).setId(Mockito.<Long>any());
//    doNothing().when(history).setShortLink(Mockito.<String>any());
//    doNothing().when(history).setUploadTime(Mockito.<LocalDateTime>any());
//    doNothing().when(history).setUser(Mockito.<User>any());
//    history.setHistoryContent("Not all who wander are lost");
//    history.setId(1L);
//    history.setShortLink("Short Link");
//    history.setUploadTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//    history.setUser(user);
//    Optional<History> ofResult = Optional.of(history);
//
//    User user3 = new User();
//    user3.setEmail("jane.doe@example.org");
//    user3.setFirst_name("Jane");
//    user3.setId(1L);
//    user3.setLast_name("Doe");
//    user3.setPassword("iloveyou");
//    user3.setPhone_number("6625550144");
//
//    History history2 = new History();
//    history2.setHistoryContent("Not all who wander are lost");
//    history2.setId(1L);
//    history2.setShortLink("Short Link");
//    history2.setUploadTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//    history2.setUser(user3);
//    when(historyRepository.save(Mockito.<History>any())).thenReturn(history2);
//    when(historyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
//
//    User user4 = new User();
//    user4.setEmail("jane.doe@example.org");
//    user4.setFirst_name("Jane");
//    user4.setId(1L);
//    user4.setLast_name("Doe");
//    user4.setPassword("iloveyou");
//    user4.setPhone_number("6625550144");
//    Optional<User> ofResult2 = Optional.of(user4);
//    when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
//    HistoryReqAto.HistoryReqAtoBuilder short_linkResult = HistoryReqAto.builder()
//            .UID(1L)
//            .history_content("Not all who wander are lost")
//            .short_link("Short link");
//    HistoryReqAto historyReqAto = short_linkResult.upload_time(LocalDate.of(1970, 1, 1).atStartOfDay()).build();
//
//    // Act
//    HistoryResAto actualUpdateHistoryResult = historyService.updateHistory(1L, historyReqAto);
//
//    // Assert
//    verify(history).getHistoryContent();
//    verify(history).getId();
//    verify(history).getShortLink();
//    verify(history).getUploadTime();
//    verify(history).getUser();
//    verify(history, atLeast(1)).setHistoryContent(eq("Not all who wander are lost"));
//    verify(history).setId(isA(Long.class));
//    verify(history, atLeast(1)).setShortLink(Mockito.<String>any());
//    verify(history, atLeast(1)).setUploadTime(isA(LocalDateTime.class));
//    verify(history, atLeast(1)).setUser(Mockito.<User>any());
//    verify(historyRepository).findById(isA(Long.class));
//    verify(userRepository).findById(isA(Long.class));
//    verify(historyRepository).save(isA(History.class));
//    assertEquals("1970-01-01", actualUpdateHistoryResult.getUpload_time().toLocalDate().toString());
//    assertEquals("Not all who wander are lost", actualUpdateHistoryResult.getHistory_content());
//    assertEquals("Short Link", actualUpdateHistoryResult.getShort_link());
//    assertEquals(1L, actualUpdateHistoryResult.getHID().longValue());
//    assertEquals(1L, actualUpdateHistoryResult.getUID().longValue());
//  }
//
//  /**
//   * Method under test: {@link HistoryService#updateHistory(Long, HistoryReqAto)}
//   */
//  @Test
//  void testUpdateHistory5() {
//    // Arrange
//    User user = new User();
//    user.setEmail("jane.doe@example.org");
//    user.setFirst_name("Jane");
//    user.setId(1L);
//    user.setLast_name("Doe");
//    user.setPassword("iloveyou");
//    user.setPhone_number("6625550144");
//
//    User user2 = new User();
//    user2.setEmail("jane.doe@example.org");
//    user2.setFirst_name("Jane");
//    user2.setId(1L);
//    user2.setLast_name("Doe");
//    user2.setPassword("iloveyou");
//    user2.setPhone_number("6625550144");
//    History history = mock(History.class);
//    when(history.getUploadTime()).thenThrow(new CCNotFoundException());
//    when(history.getShortLink()).thenReturn("Short Link");
//    when(history.getHistoryContent()).thenReturn("Not all who wander are lost");
//    when(history.getUser()).thenReturn(user2);
//    when(history.getId()).thenReturn(1L);
//    doNothing().when(history).setHistoryContent(Mockito.<String>any());
//    doNothing().when(history).setId(Mockito.<Long>any());
//    doNothing().when(history).setShortLink(Mockito.<String>any());
//    doNothing().when(history).setUploadTime(Mockito.<LocalDateTime>any());
//    doNothing().when(history).setUser(Mockito.<User>any());
//    history.setHistoryContent("Not all who wander are lost");
//    history.setId(1L);
//    history.setShortLink("Short Link");
//    history.setUploadTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//    history.setUser(user);
//    Optional<History> ofResult = Optional.of(history);
//
//    User user3 = new User();
//    user3.setEmail("jane.doe@example.org");
//    user3.setFirst_name("Jane");
//    user3.setId(1L);
//    user3.setLast_name("Doe");
//    user3.setPassword("iloveyou");
//    user3.setPhone_number("6625550144");
//
//    History history2 = new History();
//    history2.setHistoryContent("Not all who wander are lost");
//    history2.setId(1L);
//    history2.setShortLink("Short Link");
//    history2.setUploadTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//    history2.setUser(user3);
//    when(historyRepository.save(Mockito.<History>any())).thenReturn(history2);
//    when(historyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
//
//    User user4 = new User();
//    user4.setEmail("jane.doe@example.org");
//    user4.setFirst_name("Jane");
//    user4.setId(1L);
//    user4.setLast_name("Doe");
//    user4.setPassword("iloveyou");
//    user4.setPhone_number("6625550144");
//    Optional<User> ofResult2 = Optional.of(user4);
//    when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
//    HistoryReqAto.HistoryReqAtoBuilder short_linkResult = HistoryReqAto.builder()
//            .UID(1L)
//            .history_content("Not all who wander are lost")
//            .short_link("Short link");
//    HistoryReqAto historyReqAto = short_linkResult.upload_time(LocalDate.of(1970, 1, 1).atStartOfDay()).build();
//
//    // Act and Assert
//    assertThrows(CCNotFoundException.class, () -> historyService.updateHistory(1L, historyReqAto));
//    verify(history).getHistoryContent();
//    verify(history).getId();
//    verify(history).getShortLink();
//    verify(history).getUploadTime();
//    verify(history).getUser();
//    verify(history, atLeast(1)).setHistoryContent(eq("Not all who wander are lost"));
//    verify(history).setId(isA(Long.class));
//    verify(history, atLeast(1)).setShortLink(Mockito.<String>any());
//    verify(history, atLeast(1)).setUploadTime(isA(LocalDateTime.class));
//    verify(history, atLeast(1)).setUser(Mockito.<User>any());
//    verify(historyRepository).findById(isA(Long.class));
//    verify(userRepository).findById(isA(Long.class));
//    verify(historyRepository).save(isA(History.class));
//  }
//
//  /**
//   * Method under test: {@link HistoryService#deleteHistory(long)}
//   */
//  @Test
//  void testDeleteHistory() {
//    // Arrange
//    doNothing().when(historyRepository).deleteById(Mockito.<Long>any());
//    when(historyRepository.existsById(Mockito.<Long>any())).thenReturn(true);
//
//    // Act
//    historyService.deleteHistory(1L);
//
//    // Assert that nothing has changed
//    verify(historyRepository).deleteById(isA(Long.class));
//    verify(historyRepository).existsById(isA(Long.class));
//  }
//
//  /**
//   * Method under test: {@link HistoryService#deleteHistory(long)}
//   */
//  @Test
//  void testDeleteHistory2() {
//    // Arrange
//    doThrow(new CCNotFoundException()).when(historyRepository).deleteById(Mockito.<Long>any());
//    when(historyRepository.existsById(Mockito.<Long>any())).thenReturn(true);
//
//    // Act and Assert
//    assertThrows(CCNotFoundException.class, () -> historyService.deleteHistory(1L));
//    verify(historyRepository).deleteById(isA(Long.class));
//    verify(historyRepository).existsById(isA(Long.class));
//  }
//
//  /**
//   * Method under test: {@link HistoryService#deleteHistory(long)}
//   */
//  @Test
//  void testDeleteHistory3() {
//    // Arrange
//    when(historyRepository.existsById(Mockito.<Long>any())).thenReturn(false);
//
//    // Act and Assert
//    assertThrows(CCNotFoundException.class, () -> historyService.deleteHistory(1L));
//    verify(historyRepository).existsById(isA(Long.class));
//  }
//
//  /**
//   * Method under test: {@link HistoryService#findAllHistory()}
//   */
//  @Test
//  void testFindAllHistory() {
//    // Arrange
//    ArrayList<History> historyList = new ArrayList<>();
//    when(historyRepository.findAll()).thenReturn(historyList);
//
//    // Act
//    HistoriesResAto actualFindAllHistoryResult = historyService.findAllHistory();
//
//    // Assert
//    verify(historyRepository).findAll();
//    assertEquals(historyList, actualFindAllHistoryResult.getHistories());
//  }
//
//  /**
//   * Method under test: {@link HistoryService#findAllHistory()}
//   */
//  @Test
//  void testFindAllHistory2() {
//    // Arrange
//    User user = new User();
//    user.setEmail("jane.doe@example.org");
//    user.setFirst_name("Jane");
//    user.setId(1L);
//    user.setLast_name("Doe");
//    user.setPassword("iloveyou");
//    user.setPhone_number("6625550144");
//
//    History history = new History();
//    history.setHistoryContent("Not all who wander are lost");
//    history.setId(1L);
//    history.setShortLink("Short Link");
//    history.setUploadTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//    history.setUser(user);
//
//    ArrayList<History> historyList = new ArrayList<>();
//    historyList.add(history);
//    when(historyRepository.findAll()).thenReturn(historyList);
//
//    // Act
//    HistoriesResAto actualFindAllHistoryResult = historyService.findAllHistory();
//
//    // Assert
//    verify(historyRepository).findAll();
//    HistoryResAto getResult = actualFindAllHistoryResult.getHistories().get(0);
//    assertEquals("1970-01-01", getResult.getUpload_time().toLocalDate().toString());
//    assertEquals("Not all who wander are lost", getResult.getHistory_content());
//    assertEquals("Short Link", getResult.getShort_link());
//    assertEquals(1, actualFindAllHistoryResult.getTotalCount());
//    assertEquals(1L, getResult.getHID().longValue());
//    assertEquals(1L, getResult.getUID().longValue());
//  }
//
//  /**
//   * Method under test: {@link HistoryService#findAllHistory()}
//   */
//  @Test
//  void testFindAllHistory3() {
//    // Arrange
//    User user = new User();
//    user.setEmail("jane.doe@example.org");
//    user.setFirst_name("Jane");
//    user.setId(1L);
//    user.setLast_name("Doe");
//    user.setPassword("iloveyou");
//    user.setPhone_number("6625550144");
//
//    History history = new History();
//    history.setHistoryContent("Not all who wander are lost");
//    history.setId(1L);
//    history.setShortLink("Short Link");
//    history.setUploadTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//    history.setUser(user);
//
//    User user2 = new User();
//    user2.setEmail("john.smith@example.org");
//    user2.setFirst_name("John");
//    user2.setId(2L);
//    user2.setLast_name("Smith");
//    user2.setPassword("Password");
//    user2.setPhone_number("8605550118");
//
//    History history2 = new History();
//    history2.setHistoryContent("History Content");
//    history2.setId(2L);
//    history2.setShortLink("42");
//    history2.setUploadTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//    history2.setUser(user2);
//
//    ArrayList<History> historyList = new ArrayList<>();
//    historyList.add(history2);
//    historyList.add(history);
//    when(historyRepository.findAll()).thenReturn(historyList);
//
//    // Act
//    HistoriesResAto actualFindAllHistoryResult = historyService.findAllHistory();
//
//    // Assert
//    verify(historyRepository).findAll();
//    List<HistoryResAto> histories = actualFindAllHistoryResult.getHistories();
//    HistoryResAto getResult = histories.get(0);
//    assertEquals("1970-01-01", getResult.getUpload_time().toLocalDate().toString());
//    HistoryResAto getResult2 = histories.get(1);
//    assertEquals("1970-01-01", getResult2.getUpload_time().toLocalDate().toString());
//    assertEquals("42", getResult.getShort_link());
//    assertEquals("History Content", getResult.getHistory_content());
//    assertEquals("Not all who wander are lost", getResult2.getHistory_content());
//    assertEquals("Short Link", getResult2.getShort_link());
//    assertEquals(1L, getResult2.getHID().longValue());
//    assertEquals(1L, getResult2.getUID().longValue());
//    assertEquals(2, actualFindAllHistoryResult.getTotalCount());
//    assertEquals(2L, getResult.getHID().longValue());
//    assertEquals(2L, getResult.getUID().longValue());
//  }
//
//  /**
//   * Method under test: {@link HistoryService#findAllHistory()}
//   */
//  @Test
//  void testFindAllHistory4() {
//    // Arrange
//    when(historyRepository.findAll()).thenThrow(new CCNotFoundException());
//
//    // Act and Assert
//    assertThrows(CCNotFoundException.class, () -> historyService.findAllHistory());
//    verify(historyRepository).findAll();
//  }
//
//  /**
//   * Method under test: {@link HistoryService#findAllHistory()}
//   */
//  @Test
//  void testFindAllHistory5() {
//    // Arrange
//    User user = new User();
//    user.setEmail("jane.doe@example.org");
//    user.setFirst_name("Jane");
//    user.setId(1L);
//    user.setLast_name("Doe");
//    user.setPassword("iloveyou");
//    user.setPhone_number("6625550144");
//
//    User user2 = new User();
//    user2.setEmail("jane.doe@example.org");
//    user2.setFirst_name("Jane");
//    user2.setId(1L);
//    user2.setLast_name("Doe");
//    user2.setPassword("iloveyou");
//    user2.setPhone_number("6625550144");
//    History history = mock(History.class);
//    when(history.getUploadTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
//    when(history.getShortLink()).thenReturn("Short Link");
//    when(history.getHistoryContent()).thenReturn("Not all who wander are lost");
//    when(history.getUser()).thenReturn(user2);
//    when(history.getId()).thenReturn(1L);
//    doNothing().when(history).setHistoryContent(Mockito.<String>any());
//    doNothing().when(history).setId(Mockito.<Long>any());
//    doNothing().when(history).setShortLink(Mockito.<String>any());
//    doNothing().when(history).setUploadTime(Mockito.<LocalDateTime>any());
//    doNothing().when(history).setUser(Mockito.<User>any());
//    history.setHistoryContent("Not all who wander are lost");
//    history.setId(1L);
//    history.setShortLink("Short Link");
//    history.setUploadTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//    history.setUser(user);
//
//    ArrayList<History> historyList = new ArrayList<>();
//    historyList.add(history);
//    when(historyRepository.findAll()).thenReturn(historyList);
//
//    // Act
//    HistoriesResAto actualFindAllHistoryResult = historyService.findAllHistory();
//
//    // Assert
//    verify(history).getHistoryContent();
//    verify(history).getId();
//    verify(history).getShortLink();
//    verify(history).getUploadTime();
//    verify(history).getUser();
//    verify(history).setHistoryContent(eq("Not all who wander are lost"));
//    verify(history).setId(isA(Long.class));
//    verify(history).setShortLink(eq("Short Link"));
//    verify(history).setUploadTime(isA(LocalDateTime.class));
//    verify(history).setUser(isA(User.class));
//    verify(historyRepository).findAll();
//    HistoryResAto getResult = actualFindAllHistoryResult.getHistories().get(0);
//    assertEquals("1970-01-01", getResult.getUpload_time().toLocalDate().toString());
//    assertEquals("Not all who wander are lost", getResult.getHistory_content());
//    assertEquals("Short Link", getResult.getShort_link());
//    assertEquals(1, actualFindAllHistoryResult.getTotalCount());
//    assertEquals(1L, getResult.getHID().longValue());
//    assertEquals(1L, getResult.getUID().longValue());
//  }
//
//  /**
//   * Method under test: {@link HistoryService#findAllHistory()}
//   */
//  @Test
//  void testFindAllHistory6() {
//    // Arrange
//    User user = new User();
//    user.setEmail("jane.doe@example.org");
//    user.setFirst_name("Jane");
//    user.setId(1L);
//    user.setLast_name("Doe");
//    user.setPassword("iloveyou");
//    user.setPhone_number("6625550144");
//
//    User user2 = new User();
//    user2.setEmail("jane.doe@example.org");
//    user2.setFirst_name("Jane");
//    user2.setId(1L);
//    user2.setLast_name("Doe");
//    user2.setPassword("iloveyou");
//    user2.setPhone_number("6625550144");
//    History history = mock(History.class);
//    when(history.getUploadTime()).thenThrow(new CCNotFoundException());
//    when(history.getShortLink()).thenReturn("Short Link");
//    when(history.getHistoryContent()).thenReturn("Not all who wander are lost");
//    when(history.getUser()).thenReturn(user2);
//    when(history.getId()).thenReturn(1L);
//    doNothing().when(history).setHistoryContent(Mockito.<String>any());
//    doNothing().when(history).setId(Mockito.<Long>any());
//    doNothing().when(history).setShortLink(Mockito.<String>any());
//    doNothing().when(history).setUploadTime(Mockito.<LocalDateTime>any());
//    doNothing().when(history).setUser(Mockito.<User>any());
//    history.setHistoryContent("Not all who wander are lost");
//    history.setId(1L);
//    history.setShortLink("Short Link");
//    history.setUploadTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//    history.setUser(user);
//
//    ArrayList<History> historyList = new ArrayList<>();
//    historyList.add(history);
//    when(historyRepository.findAll()).thenReturn(historyList);
//
//    // Act and Assert
//    assertThrows(CCNotFoundException.class, () -> historyService.findAllHistory());
//    verify(history).getHistoryContent();
//    verify(history).getId();
//    verify(history).getShortLink();
//    verify(history).getUploadTime();
//    verify(history).getUser();
//    verify(history).setHistoryContent(eq("Not all who wander are lost"));
//    verify(history).setId(isA(Long.class));
//    verify(history).setShortLink(eq("Short Link"));
//    verify(history).setUploadTime(isA(LocalDateTime.class));
//    verify(history).setUser(isA(User.class));
//    verify(historyRepository).findAll();
//  }
//}
