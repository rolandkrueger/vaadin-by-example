package de.oio.vaadin;

import com.vaadin.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"de.oio.vaadin", "org.vaadin.appbase"})
public class MainApplication extends SpringBootServletInitializer {
  public static void main(String[] args) {
    SpringApplication.run(new Class[]{MainApplication.class}, args);
  }
}
