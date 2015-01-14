package de.oio.vaadin.springboot;

import de.oio.vaadin.springboot.app.SpringApplicationInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Class[]{MainApplication.class, SpringApplicationInitializer.class}, args);
    }
}
