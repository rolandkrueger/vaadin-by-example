package de.oio.vaadin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.appbase.SessionServices;
import org.vaadin.appbase.session.SessionContext;

import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Theme ("demo")
@Configurable (preConstruction = true)
public class DemoUI extends UI
{
  @Autowired private SessionContext context;

  @Override
  public void init (VaadinRequest request)
  {
    Page.getCurrent ().setTitle ("Vaadin By Example Demo");

    new SessionServices ().startUp ();
    context.setLocale (getLocale ());
  }

  public static DemoUI getCurrent ()
  {
    return DemoUI.getCurrent ();
  }
}
