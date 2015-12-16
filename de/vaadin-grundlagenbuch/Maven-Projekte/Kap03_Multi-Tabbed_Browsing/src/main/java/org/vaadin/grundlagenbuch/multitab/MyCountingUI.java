package org.vaadin.grundlagenbuch.multitab;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;

/**
 * Beispielanwendung, die die Fähigkeit von Vaadin für Multi-tabbed Browsing demonstriert. Die Anwendung hat eine
 * Zählvariable <code>counter</code> mit UI Scope, die in verschiedenen Browser-Tabs unabhängig voneinander hochgezählt
 * werden kann. Ein Link, der von der Anwendung bereitgestellt wird, öffnet die Anwendung in einem neuen Browser-Tab.
 * Für diesen neuen Tab gibt es eine neue UI-Instanz, die eine auf Null initialisierte  <code>counter</code> Variable
 * enthält.
 * <p>
 * Zusätzlich wird eine Übersicht über sämtliche in der aktuellen HTTP-Session vorhandenen UI-Instanzen ausgegeben.
 *
 * @author Roland Krüger
 */
@Theme("valo")
public class MyCountingUI extends UI {

  private int counter = 0;
  private Label counterLabel;

  // Annotationsbasierte Konfiguration des Vaadin Servlets mit Servlet 3.0
  @WebServlet(value = "/*")
  @VaadinServletConfiguration(productionMode = false, ui = MyCountingUI.class)
  public static class Servlet extends VaadinServlet {
  }

  @Override
  protected void init(VaadinRequest vaadinRequest) {
    // Hauptlayout der Anwendung ...
    VerticalLayout mainLayout = new VerticalLayout();
    // ... mit ein wenig Abstand zwischen den Komponenten
    mainLayout.setSpacing(true);
    // ... und mit einem Abstand der Komponenten zum Rand des Layouts
    mainLayout.setMargin(true);

    // Label, das als Überschrift dient und mit dem Style Name 'h1'
    // entsprechend größer dargestellt wird.
    Label caption = new Label("Beispiel für Vaadins Registernavigation");
    caption.setStyleName("h1");

    // Panel-Komponente, auf der der Zähler, der Button und der Link
    // angezeigt wird.
    Panel container = new Panel("Zähler");
    VerticalLayout panelLayout = new VerticalLayout();
    panelLayout.setSpacing(true);
    panelLayout.setMargin(true);

    container.setContent(panelLayout);

    counterLabel = new Label();
    counterLabel.setStyleName("h2");
    updateCounterLabel();

    Button countUpButton = new Button("Zähl hoch!");
    countUpButton.addClickListener(event -> countUp());

    // Link auf die Anwendung, der in einem neuen Tab geöffnet wird.
    Link link = new Link("In neuem Tab öffnen...", new ExternalResource("#"));
    link.setTargetName("_blank");

    mainLayout.addComponents(caption, container);
    panelLayout.addComponents(counterLabel, countUpButton, link);

    showUIsInSession(mainLayout);

    // Inhalt der UI-Instanz setzen
    setContent(mainLayout);
  }

  /**
   * Zeigt die IDs aller in der aktuellen Session vorhandenen UI-Instanzen auf einem Panel an.
   *
   * @param mainLayout das Hauptlayout, auf das das in dieser Methode erzeugte Panel gelegt werden soll.
   */
  private void showUIsInSession(VerticalLayout mainLayout) {
    Panel uiListPanel = new Panel("Offene UI-Instanzen in Ihrer Session");
    VerticalLayout layout = new VerticalLayout();
    layout.setMargin(true);

    uiListPanel.setContent(layout);
    layout.addComponent(new Label("Id von dieser UI-Instanz: " + getUIId()));

    final Label heading = new Label("Andere UI-Instanzen in der Session (insg. "
        + VaadinSession.getCurrent().getUIs().size() + " Stück):");
    heading.setStyleName("h3");
    layout.addComponent(heading);

    VaadinSession.getCurrent().getUIs().stream()
        .forEach(
            ui -> {
              layout.addComponent(new Label("UI mit id = " + ui.getUIId()));
            }
        );

    mainLayout.addComponent(uiListPanel);
  }

  /**
   * Den Zähler hochzählen und die Zähleranzeige entsprechend aktualisieren.
   */
  private void countUp() {
    counter++;
    updateCounterLabel();
  }

  /**
   * Aktualisiert das Label, das den Zählerstand anzeigt, mit dem aktuellen Wert des Zählers.
   */
  private void updateCounterLabel() {
    counterLabel.setValue("Aktueller Zählerstand: " + counter);
  }
}
