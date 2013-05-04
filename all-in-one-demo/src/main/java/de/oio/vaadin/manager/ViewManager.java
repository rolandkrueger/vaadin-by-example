package de.oio.vaadin.manager;

import org.vaadin.appbase.service.AbstractUsesServiceProvider;

public class ViewManager extends AbstractUsesServiceProvider
{

  @Override
  protected void onServiceProviderSet ()
  {
    eventbus ().register (this);
  }
}
