package de.oio.vaadin.springboot.app.services;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

public interface MessageProvider {
  public abstract String getMessage(String code) throws NoSuchMessageException;

  public abstract String getMessage(String code, Object[] args,
      String defaultMessage);

  public abstract String getMessage(String code, Object[] args)
      throws NoSuchMessageException;

  public abstract String getMessage(
      MessageSourceResolvable messageSourceResolvable)
      throws NoSuchMessageException;

}
