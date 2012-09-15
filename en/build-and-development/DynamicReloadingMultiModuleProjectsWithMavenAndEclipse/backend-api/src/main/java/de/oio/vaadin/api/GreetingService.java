package de.oio.vaadin.api;

/**
 * Defines a simple service. When the application has been started with
 * <code>mvn -Pdevelopment jetty:run</code>, you can change the code of this
 * service, compile, and immediately see the results of your changes in the
 * browser.
 */
public class GreetingService {

  public String greet() {
    return "Hello " + System.getProperty("user.name") + "!";
  }
}
