package de.oio.vaadin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Configurable(preConstruction = true)
public class DemoUI extends UI {

  @Autowired
  private SessionContext context;

  @Override
  public void init(VaadinRequest request) {

    context.setLocale(getLocale());
  }

  public static DemoUI getCurrent() {
    return DemoUI.getCurrent();
  }
}
