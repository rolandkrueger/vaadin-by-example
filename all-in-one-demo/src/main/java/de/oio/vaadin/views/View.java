package de.oio.vaadin.views;

import com.vaadin.ui.Component;

public interface View {
  View buildLayout();
  Component getContent();
}
