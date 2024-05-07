package org.ict.testjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class TestJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestJwtApplication.class, args);
    }

}
