/** Name: Dhruvi Anil Lakhani Student ID: 21512162 */
package org.uwl.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "ADDRESSES")
@Data
public class Address {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String addressLine1;

  @Column private String addressLine2;

  @Column(nullable = false)
  private String postCode;

  @Column(nullable = false)
  private String county;

  @Column(nullable = false)
  private String country;

  @PrePersist
  public void preSave() {
    setAddressLine1(StringUtils.capitalize(getAddressLine1()));
    setAddressLine2(StringUtils.capitalize(getAddressLine2()));
    setPostCode(StringUtils.upperCase(getPostCode()));
    setCounty(StringUtils.capitalize(getCounty()));
    setCountry(StringUtils.capitalize(getCountry()));
  }
}
