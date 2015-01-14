package de.oio.vaadin.springboot.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.vaadin.spring.VaadinSessionScope;

import java.io.Serializable;
import java.util.Locale;

@Component
@VaadinSessionScope
public class VaadinSessionContext implements Serializable {
    private static final Logger LOG = LoggerFactory
            .getLogger(VaadinSessionContext.class);

    private Locale locale;

    public void setLocale(Locale locale) {
        LOG.debug("Setting locale: " + locale);
        this.locale = new Locale(locale.getLanguage());
    }

    public Locale getLocale() {
        return locale;
    }
}
