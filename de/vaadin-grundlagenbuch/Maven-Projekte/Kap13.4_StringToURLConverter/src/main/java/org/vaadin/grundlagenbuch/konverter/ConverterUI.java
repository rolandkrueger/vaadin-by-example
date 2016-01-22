package org.vaadin.grundlagenbuch.konverter;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Beispiel für die Implementierung eines Konverters, der Strings in URL-Objekte umwandelt
 * und umgekehrt.
 *
 * @author Roland Krüger
 */
@Theme("valo")
public class ConverterUI extends UI {

  public static final String LINK_CAPTION = "Link zu Ihrem gewünschten Ziel: ";

  // Annotationsbasierte Konfiguration des Vaadin Servlets mit Servlet 3.0
  @WebServlet(value = "/*")
  @VaadinServletConfiguration(productionMode = false, ui = ConverterUI.class)
  public static class Servlet extends VaadinServlet {
  }

  @Override
  protected void init(VaadinRequest vaadinRequest) {
    VerticalLayout mainLayout = new VerticalLayout();
    mainLayout.setSpacing(true);
    mainLayout.setMargin(true);

    Label heading = new Label("Konvertierung eines Strings in eine URL");
    heading.setStyleName("h1");

    try {
      ObjectProperty<URL> urlProperty =
              new ObjectProperty<URL>(new URL("http://www.vaadin.com"));

      CssLayout group = new CssLayout();
      group.setCaption("Geben Sie eine Zieladresse ein:");
      group.addStyleName("v-component-group");
      group.setWidth("50%");

      TextField urlInput = new TextField();
      urlInput.addStyleName("inline-icon");
      urlInput.setIcon(FontAwesome.EXTERNAL_LINK);
      urlInput.setWidth("100%");
      urlInput.setImmediate(true);
      urlInput.setConverter(new StringToURLConverter());
      urlInput.setNullRepresentation("");
      urlInput.setConversionError("{1} ist keine gültige URL");
      urlInput.setPropertyDataSource(urlProperty);

      Button okButton = new Button("Ok");
      okButton.setIcon(FontAwesome.CHECK);
      group.addComponents(urlInput, okButton);

      Link link = new Link(LINK_CAPTION + urlProperty.getValue(), new ExternalResource(urlProperty.getValue()));

      urlProperty.addValueChangeListener(event -> {
        ExternalResource targetResource;
        if (urlProperty.getValue() != null) {
          targetResource = new ExternalResource(urlProperty.getValue());
        } else {
          final String contextPath = VaadinServlet.getCurrent().getServletContext().getContextPath();
          targetResource = new ExternalResource(contextPath);
        }
        link.setResource(targetResource);
        link.setCaption(LINK_CAPTION + targetResource.getURL());
      });

      mainLayout.addComponents(heading, group, link);
      setContent(mainLayout);
    } catch (MalformedURLException exc) {
      throw new RuntimeException("An unexpected error occurred", exc);
    }
  }
}
