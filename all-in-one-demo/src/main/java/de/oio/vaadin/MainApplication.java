package de.oio.vaadin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"de.oio.vaadin", "org.vaadin.appbase"})
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(new Class[]{MainApplication.class}, args);
    }
}
