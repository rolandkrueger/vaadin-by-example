package de.oio.vaadin.demo.uiscope;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import de.oio.vaadin.DemoUI;
import de.oio.vaadin.components.TranslatedCustomLayout;
import de.oio.vaadin.services.MessageProvider;
import de.oio.vaadin.services.application.UriActionMapperTreeService;
import de.oio.vaadin.services.templating.TemplateData;
import de.oio.vaadin.views.View;
import org.roklib.urifragmentrouting.mapper.UriPathSegmentActionMapper;

public class UsingSessionAndUIScopeView extends TranslatedCustomLayout {
  private final MessageProvider messageProvider;
  private final UriActionMapperTreeService uriActionMapperTreeService;

  /**
   * Textfield for changing the UI-scoped variable.
   */
  private TextField uiScopedValueTF;

  /**
   * Textfield for changing the session-scoped variable.
   */
  private TextField sessionScopedValueTF;
  /**
   * Property that contains the HTML code for the table which gives an overview
   * of all UI-scoped variables that are currently active for the current
   * session. For the sake of simplicity, we're putting this data in a simple
   * HTML table.
   */
  private Property<String> overviewTableProperty;

  public UsingSessionAndUIScopeView(MessageProvider messageProvider, UriActionMapperTreeService uriActionMapperTreeService, TemplateData layoutData) {
    super(layoutData);
    this.messageProvider = messageProvider;
    this.uriActionMapperTreeService = uriActionMapperTreeService;
    // create the data property for the overview table
    overviewTableProperty = new ObjectProperty<>("");
    // update the contents of the overview table
    refreshOverviewTable();
  }

  @Override
  public View buildLayout() {
    super.buildLayout();
    VerticalLayout layout = new VerticalLayout();
    layout.setSpacing(true);
    layout.setMargin(true);

    UriPathSegmentActionMapper demoActionMapper = DemoUI.getCurrent().getUriActionMapperTreeService()
        .getActionMapperForName(UsingSessionAndUIScopeDemo.DEMO_NAME);

    // we add a label that contains a link to the demo application itself.
    // When this link is opened in a new browser window or tab, a new UI
    // object for this window will be created by the framework. All field
    // variables of this class live in this new UI object and are thus
    // multiplied in memory for every new UI object.
    layout.addComponent(new Label(messageProvider.getMessage("UsingSessionAndUIScope.openThisApplication",
        new Object[]{uriActionMapperTreeService.getUriActionMapperTree().assembleUriFragment(demoActionMapper)}), ContentMode.HTML));

    DemoUI.getCurrentUIScopedVariable().addValueChangeListener(new ValueChangeListener() {
      @Override
      public void valueChange(ValueChangeEvent event) {
        refreshOverviewTable();
      }
    });

    // Print the ID of the current UI. This ID is automatically assigned by
    // Vaadin to new UI objects.
    layout.addComponent(new Label(messageProvider.getMessage("UsingSessionAndUIScope.currentUIsID")
        + String.valueOf(UI.getCurrent().getUIId())));

    layout.addComponent(createFormLayout());
    layout.addComponent(new Label(overviewTableProperty, ContentMode.HTML));

    // Add a refresh button that will update the overview table with the
    // most current values from each currently active UI object.
    Button refreshBtn = new Button(messageProvider.getMessage("refresh"));
    refreshBtn.addClickListener(new Button.ClickListener() {
      @Override
      public void buttonClick(ClickEvent event) {
        refreshOverviewTable();
      }
    });
    layout.addComponent(refreshBtn);
    getLayout().addComponent(layout, "mainPanel");
    return this;
  }

  /**
   * Updates the contents of the overview table. This table displays the
   * contents of each UI-scoped variable that is currently active in the
   * session. Using {@link VaadinSession#getUIs()} we're getting a handle on
   * each {@link UI} object in the current session. We iterate over all UI
   * objects and print the UI-scoped variable for each individual UI object in
   * the table. Each time a new UI is opened, a new row will be added to the
   * table.
   */
  private void refreshOverviewTable() {
    StringBuilder buf = new StringBuilder();
    buf.append(messageProvider.getMessage("UsingSessionAndUIScope.overviewHeadline"));

    // add the number of currently active UI objects
    buf.append(messageProvider.getMessage("UsingSessionAndUIScope.activeUIobjects", new Object[]{
        VaadinSession.getCurrent().getUIs().size(), VaadinSession.getCurrent().getSession().getId()}));

    // add a table showing the UI-scoped variable values for each currently
    // active UI object
    buf.append("<table>");

    for (UI ui : VaadinSession.getCurrent().getUIs()) {
      buf.append("<tr><td>");
      buf.append("UI ID ").append(ui.getUIId()).append(": ");
      buf.append("</td><td>");
      // cast the UI object to our UI subclass and get the UI-scoped
      // variable with the non-static method getUIScopedVariable(). We
      // can't use the static version here as this will always return the
      // variable for the current UI.
      buf.append(((DemoUI) ui).getUIScopedVariable().getValue());
      buf.append("</td></tr>");
    }

    buf.append("</table>");

    // update the label with the new table contents
    overviewTableProperty.setValue(buf.toString());
  }

  /**
   * Creates the form layout that contains the two textfields to edit the
   * session and UI-scoped variables.
   */
  private FormLayout createFormLayout() {
    FormLayout formLayout = new FormLayout();
    formLayout.setCaption(messageProvider.getMessage("UsingSessionAndUIScope.editVariables"));
    uiScopedValueTF = new TextField(messageProvider.getMessage("UsingSessionAndUIScope.uiScopedValue"),
        DemoUI.getCurrentUIScopedVariable());
    sessionScopedValueTF = new TextField(messageProvider.getMessage("UsingSessionAndUIScope.sessionScopedValue"),
        DemoUI.getSessionScopedVariable());

    uiScopedValueTF.setImmediate(true);
    sessionScopedValueTF.setImmediate(true);

    formLayout.addComponent(uiScopedValueTF);
    formLayout.addComponent(sessionScopedValueTF);

    return formLayout;
  }
}
