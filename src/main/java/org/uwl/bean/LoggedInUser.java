/** Name: Dhruvi Anil Lakhani Student ID: 21512162 */
package org.uwl.bean;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import org.uwl.entity.User;

@Component
@SessionScope
@Data
public class LoggedInUser {

  private User user;
}
