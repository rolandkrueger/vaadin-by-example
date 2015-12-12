package de.oio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VaadinSecuredWithSpringSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(new Object[]{VaadinSecuredWithSpringSecurityApplication.class}, args);
    }
}
