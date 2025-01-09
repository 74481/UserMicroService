/** Name: Dhruvi Anil Lakhani Student ID: 21512162 */
package org.uwl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uwl.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByEmail(final String email);
}
