package de.oio.vaadin.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import de.oio.vaadin.SessionContext;
import de.oio.vaadin.services.IMessageProvider;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MessageProviderImpl implements IMessageProvider {
  @Autowired
  private SessionContext context;

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
