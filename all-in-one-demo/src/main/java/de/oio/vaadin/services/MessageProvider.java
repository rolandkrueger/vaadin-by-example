package de.oio.vaadin.services;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

import java.io.Serializable;

public interface MessageProvider extends Serializable {
  String getMessage(String code) throws NoSuchMessageException;
  String getMessage(String code, Object[] args, String defaultMessage);
  String getMessage(String code, Object[] args) throws NoSuchMessageException;
  String getMessage(MessageSourceResolvable messageSourceResolvable) throws NoSuchMessageException;
}
