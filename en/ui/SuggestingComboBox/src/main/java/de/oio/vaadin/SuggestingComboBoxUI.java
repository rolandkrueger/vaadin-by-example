package de.oio.vaadin;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

@PreserveOnRefresh
public class SuggestingComboBoxUI extends UI implements Property.ValueChangeListener {
  private SuggestingComboBox comboBox;

  @Override
  protected void init(VaadinRequest request) {
    comboBox = new SuggestingComboBox();
    comboBox.setImmediate(true);
    comboBox.addValueChangeListener(this);
    SuggestingContainer container = new SuggestingContainer(new DatabaseAccessServiceImpl());
    comboBox.setContainerDataSource(container);

    setContent(comboBox);
  }

  @Override
  public void valueChange(ValueChangeEvent event) {
    Notification.show("Selected item: " + event.getProperty().getValue(), Type.HUMANIZED_MESSAGE);
  }
}
