package de.oio.vaadin.mocks;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

import de.oio.vaadin.services.IMessageProvider;

public class MessageProviderMock implements IMessageProvider {

  @Override
  public String getMessage(String code) throws NoSuchMessageException {
    return "";
  }

  @Override
  public String getMessage(String code, Object[] args, String defaultMessage) {
    return "";
  }

  @Override
  public String getMessage(String code, Object[] args)
      throws NoSuchMessageException {
    return "";
  }

  @Override
  public String getMessage(MessageSourceResolvable messageSourceResolvable)
      throws NoSuchMessageException {
    return "";
  }
}
