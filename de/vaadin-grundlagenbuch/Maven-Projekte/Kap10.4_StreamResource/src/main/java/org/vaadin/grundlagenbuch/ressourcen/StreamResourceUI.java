package org.vaadin.grundlagenbuch.ressourcen;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Beispiel für die Implementierung der StreamSource-Schnittstelle.
 *
 * @author Roland Krüger
 */
@Theme("valo")
public class StreamResourceUI extends UI {

  // Annotationsbasierte Konfiguration des Vaadin Servlets mit Servlet 3.0
  @WebServlet(value = "/*")
  @VaadinServletConfiguration(productionMode = false, ui = StreamResourceUI.class)
  public static class Servlet extends VaadinServlet {
  }

  @Override
  protected void init(VaadinRequest vaadinRequest) {
    VerticalLayout mainLayout = new VerticalLayout();
    mainLayout.setSpacing(true);
    mainLayout.setMargin(true);

    Label heading = new Label("Eine dynamisch generierte Ressource in Aktion");
    heading.setStyleName("h1");

    Image email = new Image("Erreichen Sie uns unter folgender Email-Adresse:",
            new EmailImageResource("info@example.com", "email.png"));

    mainLayout.addComponents(heading, email);
    setContent(mainLayout);
  }

  /**
   * Eine eigene StreamResource, die einen übergebenen String in ein Bildobjekt umwandelt. Die
   * Klasse ist von {@link StreamResource} abgeleitet und verwendet EmailImageSource als
   * Implementierung der {@link com.vaadin.server.StreamResource.StreamSource}-Schnittstelle.
   */
  private static class EmailImageResource extends StreamResource {
    public EmailImageResource(String emailAddress, String filename) {
      super(new EmailImageSource(emailAddress), filename);
    }
  }

  /**
   * Implementierung der {@link com.vaadin.server.StreamResource.StreamSource}-Schnittstelle,
   * die ein Bild erzeugt, das den übergebenen Text darstellt. Das Bild wird als InputStream
   * zurückgeliefert.
   */
  private static class EmailImageSource implements StreamResource.StreamSource {
    private String emailAddress;

    public EmailImageSource(String emailAddress) {
      this.emailAddress = emailAddress;
    }

    @Override
    public InputStream getStream() {
      BufferedImage image = new BufferedImage(125, 30, BufferedImage.TYPE_3BYTE_BGR);
      Graphics graphics = image.getGraphics();
      graphics.setColor(Color.white);
      graphics.fillRect(0, 0, 125, 30);
      graphics.setColor(Color.black);
      graphics.drawString(emailAddress, 10, 20);
      try {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ImageIO.write(image, "png", buffer);
        return new ByteArrayInputStream(buffer.toByteArray());
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }
    }
  }
}
