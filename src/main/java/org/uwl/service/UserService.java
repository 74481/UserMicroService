/** Name: Dhruvi Anil Lakhani Student ID: 21512162 */
package org.uwl.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uwl.bean.LoggedInUser;
import org.uwl.entity.User;
import org.uwl.repository.UserRepository;

@Slf4j
@Service
public class UserService {

  @Autowired private UserRepository userRepository;
  @Autowired private LoggedInUser loggedInUser;
  @Autowired private PasswordEncoder passwordEncoder;

  @Transactional(readOnly = true)
  public User findById(final Long id) {
    return userRepository.findById(id).orElse(null);
  }

  @Transactional
  public User save(final User user) {
    if (user.getId() == null) {
      // Encode password using one way algorithem.
      user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
    return userRepository.save(user);
  }

  @Transactional(readOnly = true)
  public boolean login(final String email, final String password) {
    final User user = userRepository.findByEmail(email);
    boolean flag = user != null && passwordEncoder.matches(password, user.getPassword());
    if (flag == true) {
      loggedInUser.setUser(user);
    } else {
      loggedInUser.setUser(null);
    }
    return flag;
  }

  public void logout() {
    loggedInUser.setUser(null);
  }

  @Transactional
  public User forgotPassword(final String email, final String newPassword) {
    final User user = userRepository.findByEmail(email);
    if (user != null) {
      user.setPassword(passwordEncoder.encode(newPassword));
      userRepository.save(user);
    }
    return user;
  }

  public User getLoggedInUser() {
    return loggedInUser.getUser();
  }
}
