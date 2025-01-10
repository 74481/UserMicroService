/** Name: Dhruvi Anil Lakhani Student ID: 21512162 */
package org.uwl.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.uwl.email.UserEmail;
import org.uwl.entity.User;
import org.uwl.service.UserService;

@Controller
@RequestMapping(path = "/user")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class UserController {

  @Autowired private UserService userService;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private UserEmail userEmail;

  @Operation
  @GetMapping(path = "/findById/{id}")
  public ResponseEntity<User> findById(@PathVariable Long id) {
    final User user = userService.findById(id);
    if (user != null) {
      user.setPassword(null); // Do NOT expose password to Ui
    }
    return ResponseEntity.ok(user);
  }

  @Operation
  @PostMapping(path = "/register")
  public ResponseEntity<User> register(@RequestBody User user) {
    final String plainPassword = user.getPassword();
    final User userDB = userService.save(user);
    CompletableFuture.runAsync(() -> userEmail.sendRegistrationEmail(userDB, plainPassword));
    userDB.setPassword(null);
    return ResponseEntity.ok(userDB);
  }

  @Operation
  @PostMapping(path = "/login")
  public ResponseEntity<Boolean> login(@RequestBody User user) {
    boolean isAuthenticated = userService.login(user.getEmail(), user.getPassword());
    return ResponseEntity.ok(isAuthenticated);
  }

  @Operation
  @GetMapping(path = "/logout")
  public ResponseEntity<Boolean> logout() {
    userService.logout();
    return ResponseEntity.ok(true);
  }

  @Operation
  @PostMapping(path = "/forgotPassword")
  public ResponseEntity<Boolean> forgotPassword(@RequestBody User user) {
    final String newPassword = UUID.randomUUID().toString().substring(0, 8);
    final User userDB = userService.forgotPassword(user.getEmail(), newPassword);
    if (userDB != null) {
      CompletableFuture.runAsync(() -> userEmail.sendForgetPasswordEmail(userDB, newPassword));
    }
    return ResponseEntity.ok(userDB != null);
  }

  @Operation
  @GetMapping(path = "/loggedInUser")
  public ResponseEntity<User> loggedInUser() {
    final User user = userService.getLoggedInUser();
    if (user != null) {
      user.setPassword(null);
    }
    return ResponseEntity.ok(user);
  }
}
