/** Name: Dhruvi Anil Lakhani Student ID: 21512162 */
package org.uwl.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.uwl.bean.LoggedInUser;
import org.uwl.entity.User;
import org.uwl.repository.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock private UserRepository userRepository;
  @Mock private LoggedInUser loggedInUser;
  @Mock private PasswordEncoder passwordEncoder;

  @InjectMocks private UserService userService;

  @Test
  void validFindByIdTest() {
    // Given
    final User user = new User();
    user.setId(1L);
    user.setFirstName("Test");
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));

    // When
    final User result = userService.findById(1L);

    // Then
    assertEquals(user, result);
  }

  @Test
  void invalidFindByIdTest() {
    // Given
    final User user = new User();
    user.setId(1L);
    user.setFirstName("Test");
    when(userRepository.findById(anyLong()))
        .thenAnswer(
            invocation -> {
              Long id = invocation.getArgument(0);
              if (id.equals(1L)) {
                return Optional.of(user);
              } else {
                return Optional.empty();
              }
            });

    // When
    final User result = userService.findById(2L);

    // Then
    assertNull(result);
  }

  @Test
  void validLoginTest() {
    // Given
    final User user = new User();
    user.setId(1L);
    user.setEmail("test@gmail.com");
    user.setPassword("$2a$10$VJVPR8Ef.vyAEEl0OdEWlOPcxUvZY3Lj1R5uBZYM5hHm00hmdF6xm");
    when(userRepository.findByEmail("test@gmail.com")).thenReturn(user);
    when(passwordEncoder.matches("admin", user.getPassword())).thenReturn(true);

    // When
    final boolean result = userService.login("test@gmail.com", "admin");

    // Then
    assertTrue(result);
  }

  @Test
  void invalidLoginTest() {
    // Given
    final User user = new User();
    user.setId(1L);
    user.setEmail("test@gmail.com");
    user.setPassword("test123");
    when(userRepository.findByEmail("test@gmail.com")).thenReturn(user);

    // When
    final boolean result = userService.login("test@gmail.com", "ABCDEFG");

    // Then
    assertFalse(result);
  }

  @Test
  void validForgotPasswordTest() {
    // Given
    final User user = new User();
    user.setId(1L);
    user.setEmail("test@gmail.com");
    user.setPassword("test123");
    when(userRepository.findByEmail("test@gmail.com")).thenReturn(user);
    final String newPassword = "ABCDEXYZ12345";

    // When
    final User result = userService.forgotPassword("test@gmail.com", newPassword);

    // Then
    assertNotNull(result);
  }

  @Test
  void invalidForgotPasswordTest() {
    // Given
    final User user = new User();
    user.setId(1L);
    user.setEmail("test@gmail.com");
    user.setPassword("test123");
    when(userRepository.findByEmail(anyString()))
        .thenAnswer(
            invocation -> {
              String email = invocation.getArgument(0);
              if (email.equals("test@gmail.com")) {
                return Optional.of(user);
              } else {
                return null;
              }
            });
    final String newPassword = "ABCDEXYZ12345";

    // When
    final User result = userService.forgotPassword("abcd@gmail.com", newPassword);

    // Then
    assertNull(result);
  }

  @Test
  void validGetLoggedInUserTest() {
    // Given
    final User user = new User();
    user.setId(1L);
    user.setEmail("test@gmail.com");
    user.setPassword("test123");
    when(loggedInUser.getUser()).thenReturn(user);

    // When
    final User result = userService.getLoggedInUser();

    // Then
    assertNotNull(result);
  }

  @Test
  void invalidGetLoggedInUserTest() {
    // Given
    when(loggedInUser.getUser()).thenReturn(null);

    // When
    final User result = userService.getLoggedInUser();

    // Then
    assertNull(result);
  }
}
