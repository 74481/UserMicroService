/** Name: Dhruvi Anil Lakhani Student ID: 21512162 */
package org.uwl;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.uwl")
@OpenAPIDefinition
// Swagger => http://localhost:8081/UserMicroService/swagger-ui/index.html
public class UserMicroServiceApplication {

  public static void main(final String[] args) {
    SpringApplication.run(UserMicroServiceApplication.class, args);
  }
}
