package de.oio.vaadin;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.vaadin.server.widgetsetutils.ConnectorBundleLoaderFactory;
import com.vaadin.shared.ui.Connect.LoadStyle;

public class OptimizedConnectorBundleLoaderFactory extends ConnectorBundleLoaderFactory {

  private Set<String> eagerConnectors = new HashSet<String>();
  {
    eagerConnectors.add(com.vaadin.client.ui.ui.UIConnector.class.getName());
    eagerConnectors.add(com.vaadin.client.ui.customlayout.CustomLayoutConnector.class.getName());
    eagerConnectors.add(com.vaadin.client.ui.combobox.ComboBoxConnector.class.getName());
    eagerConnectors.add(org.vaadin.highlighter.client.extension.ComponentHighlighterConnector.class.getName());
    eagerConnectors.add(com.vaadin.client.ui.link.LinkConnector.class.getName());
  }

  @Override
  protected LoadStyle getLoadStyle(JClassType connectorType) {
    if (eagerConnectors.contains(connectorType.getQualifiedBinaryName())) {
      return LoadStyle.EAGER;
    } else {
      // Loads all other connectors immediately after the initial view has
      // been rendered
      return LoadStyle.DEFERRED;
    }
  }
}