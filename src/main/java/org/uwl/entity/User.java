/** Name: Dhruvi Anil Lakhani Student ID: 21512162 */
package org.uwl.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Entity
@Table(
    name = "USERS",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@Data
public class User {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 5, nullable = false)
  private String title;

  @Column(length = 30, nullable = false)
  private String firstName;

  @Column(length = 30, nullable = false)
  private String lastName;

  @Column
  @Temporal(TemporalType.DATE)
  private Date dob;

  @Column(length = 50, nullable = false)
  private String email;

  @Column(length = 255, nullable = false)
  private String password;

  @Transient private String confirmPassword;

  @Column(length = 15)
  private String mobileNo;

  @OneToOne(cascade = CascadeType.ALL)
  private Address address;

  @PrePersist
  public void preSave() {
    setFirstName(StringUtils.capitalize(getFirstName()));
    setLastName(StringUtils.capitalize(getLastName()));
    setEmail(StringUtils.lowerCase(getEmail()));
  }
}
