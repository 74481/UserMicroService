/** Name: Dhruvi Anil Lakhani Student ID: 21512162 */
package org.uwl.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.uwl.entity.User;

@Component
public class UserEmail {

  @Autowired private JavaMailSender mailSender;

  public void sendRegistrationEmail(final User user, final String password) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom("info@restosys.co.uk");
      message.setTo(user.getEmail());
      message.setSubject("Thanks for registration");
      String body =
          String.format(
              "Hello %s, \n\n Thanks for registration. Please find your login details below: \n\n Username: %s \n Password: %s",
              user.getFirstName(), user.getEmail(), password);
      message.setText(body);
      mailSender.send(message);
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  public void sendForgetPasswordEmail(final User user) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom("info@restosys.co.uk");
      message.setTo(user.getEmail());
      message.setSubject("Your new password");
      String body =
          String.format(
              "Hello %s, \n\n Your new password: %s", user.getFirstName(), user.getPassword());
      message.setText(body);
      mailSender.send(message);
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
