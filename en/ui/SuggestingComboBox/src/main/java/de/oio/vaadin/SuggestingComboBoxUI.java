package de.oio.vaadin;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.servlet.annotation.WebServlet;

@PreserveOnRefresh
public class SuggestingComboBoxUI extends UI {
    private SuggestingComboBox comboBox;
    private SuggestingComboBox comboBoxDefaultValue;
    private CountryBean defaultValue;

    @Override
    protected void init(VaadinRequest request) {
        final DatabaseAccessService databaseAccessService = new DatabaseAccessServiceImpl();

        final SuggestingContainer container = new SuggestingContainer(databaseAccessService);
        comboBox = new SuggestingComboBox("Suggesting ComboBox without default value");
        comboBox.setImmediate(true);
        comboBox.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                Notification.show("Selected item: " + event.getProperty().getValue(), Type.HUMANIZED_MESSAGE);
                // tell the custom container that a value has been selected. This is necessary to ensure that the
                // selected value is displayed by the ComboBox
                container.setSelected_this_is_a_custom_method((CountryBean) event.getProperty().getValue());
            }
        });
        comboBox.setContainerDataSource(container);

        // build a ComboBox that uses a default value
        defaultValue = new CountryBean(0L, "Sweden");
        comboBoxDefaultValue = new SuggestingComboBox("Suggesting ComboBox using a default value");
        comboBoxDefaultValue.setImmediate(true);
        final SuggestingContainer suggestingContainerWithDefaultValue = new SuggestingContainer(databaseAccessService, defaultValue);
        comboBoxDefaultValue.setContainerDataSource(suggestingContainerWithDefaultValue);
        comboBoxDefaultValue.setValue(defaultValue); // preselect default value
        comboBoxDefaultValue.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                if (event.getProperty().getValue() == null) {
                    // we have to take care that the default value is properly set when null has been selected
                    comboBoxDefaultValue.setValue(defaultValue);
                    return;
                }
                Notification.show("Selected item: " + event.getProperty().getValue(), Type.HUMANIZED_MESSAGE);
                // tell the custom container that a value has been selected. This is necessary to ensure that the
                // selected value is displayed by the ComboBox
                suggestingContainerWithDefaultValue.setSelected_this_is_a_custom_method((CountryBean) event.getProperty().getValue());
            }
        });

        buildContent();
    }

    private void buildContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.addComponent(comboBox);
        layout.addComponent(comboBoxDefaultValue);
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "SuggestingComboBoxServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = SuggestingComboBoxUI.class, productionMode = false)
    public static class SuggestingComboBoxServlet extends VaadinServlet {
    }
}
