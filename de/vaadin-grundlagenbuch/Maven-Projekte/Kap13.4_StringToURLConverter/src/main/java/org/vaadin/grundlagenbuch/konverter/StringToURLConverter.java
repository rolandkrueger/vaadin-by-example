package org.vaadin.grundlagenbuch.konverter;

import com.vaadin.data.util.converter.Converter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

/**
 * @author Roland Krüger
 */
public class StringToURLConverter implements Converter<String, URL> {
  @Override
  public URL convertToModel(String value,
                            Class<? extends URL> targetType,
                            Locale locale)
          throws ConversionException {
    if (targetType != getModelType()) {
      throw new ConversionException("Converter only supports "
              + getModelType().getName() + " (targetType was "
              + targetType.getName() + ")");
    }

    if (value == null || value.trim().isEmpty()) {
      return null;
    }

    try {
      return new URL(value);
    } catch (MalformedURLException e) {
      throw new ConversionException(value);
    }
  }

  @Override
  public String convertToPresentation(URL value,
                                      Class<? extends String> targetType, Locale locale)
          throws ConversionException {
    if (targetType != getPresentationType()) {
      throw new ConversionException("Converter only supports "
              + getPresentationType().getName() + " (targetType was "
              + targetType.getName() + ")");
    }

    if (value == null) {
      return null;
    }
    return value.toString();
  }

  @Override
  public Class<URL> getModelType() {
    return URL.class;
  }

  @Override
  public Class<String> getPresentationType() {
    return String.class;
  }
}