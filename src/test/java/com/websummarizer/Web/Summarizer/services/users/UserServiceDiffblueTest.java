package com.websummarizer.Web.Summarizer.services.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.websummarizer.Web.Summarizer.common.exceptions.CCNotFoundException;
import com.websummarizer.Web.Summarizer.controller.user.UserReqAto;
import com.websummarizer.Web.Summarizer.controller.user.UserResAto;
import com.websummarizer.Web.Summarizer.controller.user.UsersResAto;
import com.websummarizer.Web.Summarizer.model.User;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserServiceDiffblueTest {
  @MockBean
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  /**
   * Method under test: {@link UserService#addUser(UserReqAto)}
   */
  @Test
  void testAddUser() {
    // Arrange
    User user = new User();
    user.setEmail("jane.doe@example.org");
    user.setFirst_name("Jane");
    user.setId(1L);
    user.setLast_name("Doe");
    user.setPassword("iloveyou");
    user.setPhone_number("6625550144");
    when(userRepository.save(Mockito.<User>any())).thenReturn(user);
    UserReqAto reqSto = UserReqAto.builder()
            .email("jane.doe@example.org")
            .first_name("Jane")
            .last_name("Doe")
            .password("iloveyou")
            .phone_number("6625550144")
            .build();

    // Act
    UserResAto actualAddUserResult = userService.addUser(reqSto);

    // Assert
    verify(userRepository).save(isA(User.class));
    assertEquals("6625550144", actualAddUserResult.getPhone_number());
    assertEquals("Doe", actualAddUserResult.getLast_name());
    assertEquals("Jane", actualAddUserResult.getFirst_name());
    assertEquals("iloveyou", actualAddUserResult.getPassword());
    assertEquals("jane.doe@example.org", actualAddUserResult.getEmail());
    assertEquals(1L, actualAddUserResult.getId().longValue());
  }

  /**
   * Method under test: {@link UserService#addUser(UserReqAto)}
   */
  @Test
  void testAddUser2() {
    // Arrange
    User user = mock(User.class);
    when(user.getPassword()).thenReturn("iloveyou");
    when(user.getPhone_number()).thenReturn("6625550144");
    when(user.getEmail()).thenReturn("jane.doe@example.org");
    when(user.getLast_name()).thenReturn("Doe");
    when(user.getFirst_name()).thenReturn("Jane");
    when(user.getId()).thenReturn(1L);
    doNothing().when(user).setEmail(Mockito.<String>any());
    doNothing().when(user).setFirst_name(Mockito.<String>any());
    doNothing().when(user).setId(anyLong());
    doNothing().when(user).setLast_name(Mockito.<String>any());
    doNothing().when(user).setPassword(Mockito.<String>any());
    doNothing().when(user).setPhone_number(Mockito.<String>any());
    user.setEmail("jane.doe@example.org");
    user.setFirst_name("Jane");
    user.setId(1L);
    user.setLast_name("Doe");
    user.setPassword("iloveyou");
    user.setPhone_number("6625550144");
    when(userRepository.save(Mockito.<User>any())).thenReturn(user);
    UserReqAto reqSto = UserReqAto.builder()
            .email("jane.doe@example.org")
            .first_name("Jane")
            .last_name("Doe")
            .password("iloveyou")
            .phone_number("6625550144")
            .build();

    // Act
    UserResAto actualAddUserResult = userService.addUser(reqSto);

    // Assert
    verify(user).getEmail();
    verify(user).getFirst_name();
    verify(user).getId();
    verify(user).getLast_name();
    verify(user).getPassword();
    verify(user).getPhone_number();
    verify(user).setEmail(eq("jane.doe@example.org"));
    verify(user).setFirst_name(eq("Jane"));
    verify(user).setId(eq(1L));
    verify(user).setLast_name(eq("Doe"));
    verify(user).setPassword(eq("iloveyou"));
    verify(user).setPhone_number(eq("6625550144"));
    verify(userRepository).save(isA(User.class));
    assertEquals("6625550144", actualAddUserResult.getPhone_number());
    assertEquals("Doe", actualAddUserResult.getLast_name());
    assertEquals("Jane", actualAddUserResult.getFirst_name());
    assertEquals("iloveyou", actualAddUserResult.getPassword());
    assertEquals("jane.doe@example.org", actualAddUserResult.getEmail());
    assertEquals(1L, actualAddUserResult.getId().longValue());
  }

  /**
   * Method under test: {@link UserService#addUser(UserReqAto)}
   */
  @Test
  void testAddUser3() {
    // Arrange
    User user = mock(User.class);
    when(user.getPassword()).thenThrow(new CCNotFoundException());
    when(user.getPhone_number()).thenReturn("6625550144");
    when(user.getEmail()).thenReturn("jane.doe@example.org");
    when(user.getLast_name()).thenReturn("Doe");
    when(user.getFirst_name()).thenReturn("Jane");
    when(user.getId()).thenReturn(1L);
    doNothing().when(user).setEmail(Mockito.<String>any());
    doNothing().when(user).setFirst_name(Mockito.<String>any());
    doNothing().when(user).setId(anyLong());
    doNothing().when(user).setLast_name(Mockito.<String>any());
    doNothing().when(user).setPassword(Mockito.<String>any());
    doNothing().when(user).setPhone_number(Mockito.<String>any());
    user.setEmail("jane.doe@example.org");
    user.setFirst_name("Jane");
    user.setId(1L);
    user.setLast_name("Doe");
    user.setPassword("iloveyou");
    user.setPhone_number("6625550144");
    when(userRepository.save(Mockito.<User>any())).thenReturn(user);
    UserReqAto reqSto = UserReqAto.builder()
            .email("jane.doe@example.org")
            .first_name("Jane")
            .last_name("Doe")
            .password("iloveyou")
            .phone_number("6625550144")
            .build();

    // Act and Assert
    assertThrows(CCNotFoundException.class, () -> userService.addUser(reqSto));
    verify(user).getEmail();
    verify(user).getFirst_name();
    verify(user).getId();
    verify(user).getLast_name();
    verify(user).getPassword();
    verify(user).getPhone_number();
    verify(user).setEmail(eq("jane.doe@example.org"));
    verify(user).setFirst_name(eq("Jane"));
    verify(user).setId(eq(1L));
    verify(user).setLast_name(eq("Doe"));
    verify(user).setPassword(eq("iloveyou"));
    verify(user).setPhone_number(eq("6625550144"));
    verify(userRepository).save(isA(User.class));
  }

  /**
   * Method under test: {@link UserService#addUser(UserReqAto)}
   */
  @Test
  void testAddUser4() {
    // Arrange
    when(userRepository.save(Mockito.<User>any())).thenThrow(new CCNotFoundException());
    UserReqAto reqSto = mock(UserReqAto.class);
    when(reqSto.getEmail()).thenReturn("jane.doe@example.org");
    when(reqSto.getFirst_name()).thenReturn("Jane");
    when(reqSto.getPassword()).thenReturn("iloveyou");
    when(reqSto.getPhone_number()).thenReturn("6625550144");

    // Act and Assert
    assertThrows(CCNotFoundException.class, () -> userService.addUser(reqSto));
    verify(reqSto).getEmail();
    verify(reqSto, atLeast(1)).getFirst_name();
    verify(reqSto).getPassword();
    verify(reqSto).getPhone_number();
    verify(userRepository).save(isA(User.class));
  }

  /**
   * Method under test: {@link UserService#getUser(long)}
   */
  @Test
  void testGetUser() {
    // Arrange
    User user = new User();
    user.setEmail("jane.doe@example.org");
    user.setFirst_name("Jane");
    user.setId(1L);
    user.setLast_name("Doe");
    user.setPassword("iloveyou");
    user.setPhone_number("6625550144");
    Optional<User> ofResult = Optional.of(user);
    when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    // Act
    UserResAto actualUser = userService.getUser(1L);

    // Assert
    verify(userRepository).findById(isA(Long.class));
    assertEquals("6625550144", actualUser.getPhone_number());
    assertEquals("Doe", actualUser.getLast_name());
    assertEquals("Jane", actualUser.getFirst_name());
    assertEquals("iloveyou", actualUser.getPassword());
    assertEquals("jane.doe@example.org", actualUser.getEmail());
    assertEquals(1L, actualUser.getId().longValue());
  }

  /**
   * Method under test: {@link UserService#getUser(long)}
   */
  @Test
  void testGetUser2() {
    // Arrange
    User user = mock(User.class);
    when(user.getPassword()).thenReturn("iloveyou");
    when(user.getPhone_number()).thenReturn("6625550144");
    when(user.getEmail()).thenReturn("jane.doe@example.org");
    when(user.getLast_name()).thenReturn("Doe");
    when(user.getFirst_name()).thenReturn("Jane");
    when(user.getId()).thenReturn(1L);
    doNothing().when(user).setEmail(Mockito.<String>any());
    doNothing().when(user).setFirst_name(Mockito.<String>any());
    doNothing().when(user).setId(anyLong());
    doNothing().when(user).setLast_name(Mockito.<String>any());
    doNothing().when(user).setPassword(Mockito.<String>any());
    doNothing().when(user).setPhone_number(Mockito.<String>any());
    user.setEmail("jane.doe@example.org");
    user.setFirst_name("Jane");
    user.setId(1L);
    user.setLast_name("Doe");
    user.setPassword("iloveyou");
    user.setPhone_number("6625550144");
    Optional<User> ofResult = Optional.of(user);
    when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    // Act
    UserResAto actualUser = userService.getUser(1L);

    // Assert
    verify(user).getEmail();
    verify(user).getFirst_name();
    verify(user).getId();
    verify(user).getLast_name();
    verify(user).getPassword();
    verify(user).getPhone_number();
    verify(user).setEmail(eq("jane.doe@example.org"));
    verify(user).setFirst_name(eq("Jane"));
    verify(user).setId(eq(1L));
    verify(user).setLast_name(eq("Doe"));
    verify(user).setPassword(eq("iloveyou"));
    verify(user).setPhone_number(eq("6625550144"));
    verify(userRepository).findById(isA(Long.class));
    assertEquals("6625550144", actualUser.getPhone_number());
    assertEquals("Doe", actualUser.getLast_name());
    assertEquals("Jane", actualUser.getFirst_name());
    assertEquals("iloveyou", actualUser.getPassword());
    assertEquals("jane.doe@example.org", actualUser.getEmail());
    assertEquals(1L, actualUser.getId().longValue());
  }

  /**
   * Method under test: {@link UserService#getUser(long)}
   */
  @Test
  void testGetUser3() {
    // Arrange
    User user = mock(User.class);
    when(user.getPassword()).thenThrow(new CCNotFoundException());
    when(user.getPhone_number()).thenReturn("6625550144");
    when(user.getEmail()).thenReturn("jane.doe@example.org");
    when(user.getLast_name()).thenReturn("Doe");
    when(user.getFirst_name()).thenReturn("Jane");
    when(user.getId()).thenReturn(1L);
    doNothing().when(user).setEmail(Mockito.<String>any());
    doNothing().when(user).setFirst_name(Mockito.<String>any());
    doNothing().when(user).setId(anyLong());
    doNothing().when(user).setLast_name(Mockito.<String>any());
    doNothing().when(user).setPassword(Mockito.<String>any());
    doNothing().when(user).setPhone_number(Mockito.<String>any());
    user.setEmail("jane.doe@example.org");
    user.setFirst_name("Jane");
    user.setId(1L);
    user.setLast_name("Doe");
    user.setPassword("iloveyou");
    user.setPhone_number("6625550144");
    Optional<User> ofResult = Optional.of(user);
    when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    // Act and Assert
    assertThrows(CCNotFoundException.class, () -> userService.getUser(1L));
    verify(user).getEmail();
    verify(user).getFirst_name();
    verify(user).getId();
    verify(user).getLast_name();
    verify(user).getPassword();
    verify(user).getPhone_number();
    verify(user).setEmail(eq("jane.doe@example.org"));
    verify(user).setFirst_name(eq("Jane"));
    verify(user).setId(eq(1L));
    verify(user).setLast_name(eq("Doe"));
    verify(user).setPassword(eq("iloveyou"));
    verify(user).setPhone_number(eq("6625550144"));
    verify(userRepository).findById(isA(Long.class));
  }

  /**
   * Method under test: {@link UserService#updateUser(Long, UserReqAto)}
   */
  @Test
  void testUpdateUser() {
    // Arrange
    when(userRepository.findById(Mockito.<Long>any())).thenThrow(new CCNotFoundException());

    // Act and Assert
    assertThrows(CCNotFoundException.class, () -> userService.updateUser(1L, null));
    verify(userRepository).findById(isA(Long.class));
  }

  /**
   * Method under test: {@link UserService#updateUser(Long, UserReqAto)}
   */
  @Test
  void testUpdateUser2() {
    // Arrange
    User user = new User();
    user.setEmail("jane.doe@example.org");
    user.setFirst_name("Jane");
    user.setId(1L);
    user.setLast_name("Doe");
    user.setPassword("iloveyou");
    user.setPhone_number("6625550144");
    Optional<User> ofResult = Optional.of(user);

    User user2 = new User();
    user2.setEmail("jane.doe@example.org");
    user2.setFirst_name("Jane");
    user2.setId(1L);
    user2.setLast_name("Doe");
    user2.setPassword("iloveyou");
    user2.setPhone_number("6625550144");
    when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
    when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
    UserReqAto userReqAto = UserReqAto.builder()
            .email("jane.doe@example.org")
            .first_name("Jane")
            .last_name("Doe")
            .password("iloveyou")
            .phone_number("6625550144")
            .build();

    // Act
    UserResAto actualUpdateUserResult = userService.updateUser(1L, userReqAto);

    // Assert
    verify(userRepository).findById(isA(Long.class));
    verify(userRepository).save(isA(User.class));
    assertEquals("6625550144", actualUpdateUserResult.getPhone_number());
    assertEquals("Doe", actualUpdateUserResult.getLast_name());
    assertEquals("Jane", actualUpdateUserResult.getFirst_name());
    assertEquals("iloveyou", actualUpdateUserResult.getPassword());
    assertEquals("jane.doe@example.org", actualUpdateUserResult.getEmail());
    assertEquals(1L, actualUpdateUserResult.getId().longValue());
  }

  /**
   * Method under test: {@link UserService#updateUser(Long, UserReqAto)}
   */
  @Test
  void testUpdateUser3() {
    // Arrange
    User user = new User();
    user.setEmail("jane.doe@example.org");
    user.setFirst_name("Jane");
    user.setId(1L);
    user.setLast_name("Doe");
    user.setPassword("iloveyou");
    user.setPhone_number("6625550144");
    Optional<User> ofResult = Optional.of(user);
    when(userRepository.save(Mockito.<User>any())).thenThrow(new CCNotFoundException());
    when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
    UserReqAto userReqAto = UserReqAto.builder()
            .email("jane.doe@example.org")
            .first_name("Jane")
            .last_name("Doe")
            .password("iloveyou")
            .phone_number("6625550144")
            .build();

    // Act and Assert
    assertThrows(CCNotFoundException.class, () -> userService.updateUser(1L, userReqAto));
    verify(userRepository).findById(isA(Long.class));
    verify(userRepository).save(isA(User.class));
  }

  /**
   * Method under test: {@link UserService#updateUser(Long, UserReqAto)}
   */
  @Test
  void testUpdateUser4() {
    // Arrange
    User user = mock(User.class);
    when(user.getPassword()).thenReturn("iloveyou");
    when(user.getPhone_number()).thenReturn("6625550144");
    when(user.getEmail()).thenReturn("jane.doe@example.org");
    when(user.getLast_name()).thenReturn("Doe");
    when(user.getFirst_name()).thenReturn("Jane");
    when(user.getId()).thenReturn(1L);
    doNothing().when(user).setEmail(Mockito.<String>any());
    doNothing().when(user).setFirst_name(Mockito.<String>any());
    doNothing().when(user).setId(anyLong());
    doNothing().when(user).setLast_name(Mockito.<String>any());
    doNothing().when(user).setPassword(Mockito.<String>any());
    doNothing().when(user).setPhone_number(Mockito.<String>any());
    user.setEmail("jane.doe@example.org");
    user.setFirst_name("Jane");
    user.setId(1L);
    user.setLast_name("Doe");
    user.setPassword("iloveyou");
    user.setPhone_number("6625550144");
    Optional<User> ofResult = Optional.of(user);

    User user2 = new User();
    user2.setEmail("jane.doe@example.org");
    user2.setFirst_name("Jane");
    user2.setId(1L);
    user2.setLast_name("Doe");
    user2.setPassword("iloveyou");
    user2.setPhone_number("6625550144");
    when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
    when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
    UserReqAto userReqAto = UserReqAto.builder()
            .email("jane.doe@example.org")
            .first_name("Jane")
            .last_name("Doe")
            .password("iloveyou")
            .phone_number("6625550144")
            .build();

    // Act
    UserResAto actualUpdateUserResult = userService.updateUser(1L, userReqAto);

    // Assert
    verify(user).getEmail();
    verify(user).getFirst_name();
    verify(user).getId();
    verify(user).getLast_name();
    verify(user).getPassword();
    verify(user).getPhone_number();
    verify(user, atLeast(1)).setEmail(eq("jane.doe@example.org"));
    verify(user, atLeast(1)).setFirst_name(eq("Jane"));
    verify(user).setId(eq(1L));
    verify(user, atLeast(1)).setLast_name(eq("Doe"));
    verify(user, atLeast(1)).setPassword(eq("iloveyou"));
    verify(user, atLeast(1)).setPhone_number(eq("6625550144"));
    verify(userRepository).findById(isA(Long.class));
    verify(userRepository).save(isA(User.class));
    assertEquals("6625550144", actualUpdateUserResult.getPhone_number());
    assertEquals("Doe", actualUpdateUserResult.getLast_name());
    assertEquals("Jane", actualUpdateUserResult.getFirst_name());
    assertEquals("iloveyou", actualUpdateUserResult.getPassword());
    assertEquals("jane.doe@example.org", actualUpdateUserResult.getEmail());
    assertEquals(1L, actualUpdateUserResult.getId().longValue());
  }

  /**
   * Method under test: {@link UserService#updateUser(Long, UserReqAto)}
   */
  @Test
  void testUpdateUser5() {
    // Arrange
    User user = mock(User.class);
    when(user.getPassword()).thenThrow(new CCNotFoundException());
    when(user.getPhone_number()).thenReturn("6625550144");
    when(user.getEmail()).thenReturn("jane.doe@example.org");
    when(user.getLast_name()).thenReturn("Doe");
    when(user.getFirst_name()).thenReturn("Jane");
    when(user.getId()).thenReturn(1L);
    doNothing().when(user).setEmail(Mockito.<String>any());
    doNothing().when(user).setFirst_name(Mockito.<String>any());
    doNothing().when(user).setId(anyLong());
    doNothing().when(user).setLast_name(Mockito.<String>any());
    doNothing().when(user).setPassword(Mockito.<String>any());
    doNothing().when(user).setPhone_number(Mockito.<String>any());
    user.setEmail("jane.doe@example.org");
    user.setFirst_name("Jane");
    user.setId(1L);
    user.setLast_name("Doe");
    user.setPassword("iloveyou");
    user.setPhone_number("6625550144");
    Optional<User> ofResult = Optional.of(user);

    User user2 = new User();
    user2.setEmail("jane.doe@example.org");
    user2.setFirst_name("Jane");
    user2.setId(1L);
    user2.setLast_name("Doe");
    user2.setPassword("iloveyou");
    user2.setPhone_number("6625550144");
    when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
    when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
    UserReqAto userReqAto = UserReqAto.builder()
            .email("jane.doe@example.org")
            .first_name("Jane")
            .last_name("Doe")
            .password("iloveyou")
            .phone_number("6625550144")
            .build();

    // Act and Assert
    assertThrows(CCNotFoundException.class, () -> userService.updateUser(1L, userReqAto));
    verify(user).getEmail();
    verify(user).getFirst_name();
    verify(user).getId();
    verify(user).getLast_name();
    verify(user).getPassword();
    verify(user).getPhone_number();
    verify(user, atLeast(1)).setEmail(eq("jane.doe@example.org"));
    verify(user, atLeast(1)).setFirst_name(eq("Jane"));
    verify(user).setId(eq(1L));
    verify(user, atLeast(1)).setLast_name(eq("Doe"));
    verify(user, atLeast(1)).setPassword(eq("iloveyou"));
    verify(user, atLeast(1)).setPhone_number(eq("6625550144"));
    verify(userRepository).findById(isA(Long.class));
    verify(userRepository).save(isA(User.class));
  }

  /**
   * Method under test: {@link UserService#deleteUser(long)}
   */
  @Test
  void testDeleteUser() {
    // Arrange
    doNothing().when(userRepository).deleteById(Mockito.<Long>any());
    when(userRepository.existsById(Mockito.<Long>any())).thenReturn(true);

    // Act
    userService.deleteUser(1L);

    // Assert that nothing has changed
    verify(userRepository).deleteById(isA(Long.class));
    verify(userRepository).existsById(isA(Long.class));
  }

  /**
   * Method under test: {@link UserService#deleteUser(long)}
   */
  @Test
  void testDeleteUser2() {
    // Arrange
    doThrow(new CCNotFoundException()).when(userRepository).deleteById(Mockito.<Long>any());
    when(userRepository.existsById(Mockito.<Long>any())).thenReturn(true);

    // Act and Assert
    assertThrows(CCNotFoundException.class, () -> userService.deleteUser(1L));
    verify(userRepository).deleteById(isA(Long.class));
    verify(userRepository).existsById(isA(Long.class));
  }

  /**
   * Method under test: {@link UserService#deleteUser(long)}
   */
  @Test
  void testDeleteUser3() {
    // Arrange
    when(userRepository.existsById(Mockito.<Long>any())).thenReturn(false);

    // Act and Assert
    assertThrows(CCNotFoundException.class, () -> userService.deleteUser(1L));
    verify(userRepository).existsById(isA(Long.class));
  }

  /**
   * Method under test: {@link UserService#findAllUser()}
   */
  @Test
  void testFindAllUser() {
    // Arrange
    when(userRepository.findAll()).thenReturn(new ArrayList<>());

    // Act
    UsersResAto actualFindAllUserResult = userService.findAllUser();

    // Assert
    verify(userRepository).findAll();
    assertEquals(0, actualFindAllUserResult.getTotalCount());
  }

  /**
   * Method under test: {@link UserService#findAllUser()}
   */
  @Test
  void testFindAllUser2() {
    // Arrange
    User user = new User();
    user.setEmail("jane.doe@example.org");
    user.setFirst_name("Jane");
    user.setId(1L);
    user.setLast_name("Doe");
    user.setPassword("iloveyou");
    user.setPhone_number("6625550144");

    ArrayList<User> userList = new ArrayList<>();
    userList.add(user);
    when(userRepository.findAll()).thenReturn(userList);

    // Act
    UsersResAto actualFindAllUserResult = userService.findAllUser();

    // Assert
    verify(userRepository).findAll();
    assertEquals(1, actualFindAllUserResult.getTotalCount());
  }

  /**
   * Method under test: {@link UserService#findAllUser()}
   */
  @Test
  void testFindAllUser3() {
    // Arrange
    User user = new User();
    user.setEmail("jane.doe@example.org");
    user.setFirst_name("Jane");
    user.setId(1L);
    user.setLast_name("Doe");
    user.setPassword("iloveyou");
    user.setPhone_number("6625550144");

    User user2 = new User();
    user2.setEmail("john.smith@example.org");
    user2.setFirst_name("John");
    user2.setId(2L);
    user2.setLast_name("Smith");
    user2.setPassword("Password");
    user2.setPhone_number("8605550118");

    ArrayList<User> userList = new ArrayList<>();
    userList.add(user2);
    userList.add(user);
    when(userRepository.findAll()).thenReturn(userList);

    // Act
    UsersResAto actualFindAllUserResult = userService.findAllUser();

    // Assert
    verify(userRepository).findAll();
    assertEquals(2, actualFindAllUserResult.getTotalCount());
  }

  /**
   * Method under test: {@link UserService#findAllUser()}
   */
  @Test
  void testFindAllUser4() {
    // Arrange
    when(userRepository.findAll()).thenThrow(new CCNotFoundException());

    // Act and Assert
    assertThrows(CCNotFoundException.class, () -> userService.findAllUser());
    verify(userRepository).findAll();
  }

  /**
   * Method under test: {@link UserService#findAllUser()}
   */
  @Test
  void testFindAllUser5() {
    // Arrange
    User user = mock(User.class);
    when(user.getPassword()).thenReturn("iloveyou");
    when(user.getPhone_number()).thenReturn("6625550144");
    when(user.getEmail()).thenReturn("jane.doe@example.org");
    when(user.getLast_name()).thenReturn("Doe");
    when(user.getFirst_name()).thenReturn("Jane");
    when(user.getId()).thenReturn(1L);
    doNothing().when(user).setEmail(Mockito.<String>any());
    doNothing().when(user).setFirst_name(Mockito.<String>any());
    doNothing().when(user).setId(anyLong());
    doNothing().when(user).setLast_name(Mockito.<String>any());
    doNothing().when(user).setPassword(Mockito.<String>any());
    doNothing().when(user).setPhone_number(Mockito.<String>any());
    user.setEmail("jane.doe@example.org");
    user.setFirst_name("Jane");
    user.setId(1L);
    user.setLast_name("Doe");
    user.setPassword("iloveyou");
    user.setPhone_number("6625550144");

    ArrayList<User> userList = new ArrayList<>();
    userList.add(user);
    when(userRepository.findAll()).thenReturn(userList);

    // Act
    UsersResAto actualFindAllUserResult = userService.findAllUser();

    // Assert
    verify(user).getEmail();
    verify(user).getFirst_name();
    verify(user).getId();
    verify(user).getLast_name();
    verify(user).getPassword();
    verify(user).getPhone_number();
    verify(user).setEmail(eq("jane.doe@example.org"));
    verify(user).setFirst_name(eq("Jane"));
    verify(user).setId(eq(1L));
    verify(user).setLast_name(eq("Doe"));
    verify(user).setPassword(eq("iloveyou"));
    verify(user).setPhone_number(eq("6625550144"));
    verify(userRepository).findAll();
    assertEquals(1, actualFindAllUserResult.getTotalCount());
  }

  /**
   * Method under test: {@link UserService#findAllUser()}
   */
  @Test
  void testFindAllUser6() {
    // Arrange
    User user = mock(User.class);
    when(user.getPassword()).thenThrow(new CCNotFoundException());
    when(user.getPhone_number()).thenReturn("6625550144");
    when(user.getEmail()).thenReturn("jane.doe@example.org");
    when(user.getLast_name()).thenReturn("Doe");
    when(user.getFirst_name()).thenReturn("Jane");
    when(user.getId()).thenReturn(1L);
    doNothing().when(user).setEmail(Mockito.<String>any());
    doNothing().when(user).setFirst_name(Mockito.<String>any());
    doNothing().when(user).setId(anyLong());
    doNothing().when(user).setLast_name(Mockito.<String>any());
    doNothing().when(user).setPassword(Mockito.<String>any());
    doNothing().when(user).setPhone_number(Mockito.<String>any());
    user.setEmail("jane.doe@example.org");
    user.setFirst_name("Jane");
    user.setId(1L);
    user.setLast_name("Doe");
    user.setPassword("iloveyou");
    user.setPhone_number("6625550144");

    ArrayList<User> userList = new ArrayList<>();
    userList.add(user);
    when(userRepository.findAll()).thenReturn(userList);

    // Act and Assert
    assertThrows(CCNotFoundException.class, () -> userService.findAllUser());
    verify(user).getEmail();
    verify(user).getFirst_name();
    verify(user).getId();
    verify(user).getLast_name();
    verify(user).getPassword();
    verify(user).getPhone_number();
    verify(user).setEmail(eq("jane.doe@example.org"));
    verify(user).setFirst_name(eq("Jane"));
    verify(user).setId(eq(1L));
    verify(user).setLast_name(eq("Doe"));
    verify(user).setPassword(eq("iloveyou"));
    verify(user).setPhone_number(eq("6625550144"));
    verify(userRepository).findAll();
  }

}
