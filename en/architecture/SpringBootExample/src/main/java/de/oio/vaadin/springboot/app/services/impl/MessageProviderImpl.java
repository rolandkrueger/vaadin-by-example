package de.oio.vaadin.springboot.app.services.impl;

import de.oio.vaadin.springboot.app.VaadinSessionContext;
import de.oio.vaadin.springboot.app.services.IMessageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.vaadin.spring.VaadinSessionScope;

import java.io.Serializable;

@Service
@VaadinSessionScope
public class MessageProviderImpl implements IMessageProvider, Serializable {
  @Autowired
  private VaadinSessionContext context;

  @Autowired
  private MessageSource messageSource;

  @Override
  public String getMessage(MessageSourceResolvable messageSourceResolvable)
      throws NoSuchMessageException {
    return messageSource.getMessage(messageSourceResolvable,
        context.getLocale());
  }

  @Override
  public String getMessage(String code, Object[] args)
      throws NoSuchMessageException {
    return messageSource.getMessage(code, args, context.getLocale());
  }

  @Override
  public String getMessage(String code, Object[] args, String defaultMessage) {
    return messageSource.getMessage(code, args, defaultMessage,
        context.getLocale());
  }

  public String getMessage(String code) throws NoSuchMessageException {
    return getMessage(code, null);
  }

}
