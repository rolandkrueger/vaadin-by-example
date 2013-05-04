package de.oio.vaadin;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionContext {

  private static final Logger LOG = LoggerFactory.getLogger(SessionContext.class);

  private Locale locale;

  public void setLocale(Locale locale) {
    LOG.debug("Setting locale: " + locale);
    this.locale = new Locale(locale.getLanguage());
  }

  public Locale getLocale() {
    return locale;
  }

  public Locale getDefaultLocale() {
    return Locale.ENGLISH;
  }

}
