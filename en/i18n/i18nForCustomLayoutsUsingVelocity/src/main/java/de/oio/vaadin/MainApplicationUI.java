package de.oio.vaadin;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.config.EasyFactoryConfiguration;
import org.apache.velocity.tools.generic.ResourceTool;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@PreserveOnRefresh
@Theme("mytheme")
public class MainApplicationUI extends UI {

  private Locale currentLocale;
  private VelocityEngine velocityEngine;
  private Map<Locale, Context> localizedVelocityContexts;

  @Override
  protected void init(VaadinRequest request) {
    currentLocale = Locale.ENGLISH;

    createVelocityEngine();
    createVelocityContexts();
    buildMainLayout();
  }

  private void buildMainLayout() {
    try {
      CustomLayout layout = new CustomLayout(getLayoutTemplate("main"));
      layout.addComponent(createLanguageSelector(), "languageSelector");
      setContent(layout);
    } catch (IOException e) {
      setErrorMessage();
      e.printStackTrace();
    }
  }

  private ComboBox createLanguageSelector() {
    ComboBox languageSelector = new ComboBox();
    languageSelector.setContainerDataSource(getLanguageItems());
    languageSelector.setItemCaptionPropertyId("caption");
    languageSelector.setItemIconPropertyId("icon");
    languageSelector.setImmediate(true);
    languageSelector.setNullSelectionAllowed(false);
    languageSelector.addValueChangeListener(new Property.ValueChangeListener() {
      @Override
      public void valueChange(ValueChangeEvent event) {
        Locale locale = (Locale) event.getProperty().getValue();
        if (locale.equals(currentLocale)) {
          return;
        }

        currentLocale = locale;
        buildMainLayout();
      }
    });
    languageSelector.select(currentLocale);
    return languageSelector;
  }

  private Container getLanguageItems() {
    Container languageItems = new IndexedContainer();
    languageItems.addContainerProperty("icon", ThemeResource.class, null);
    languageItems.addContainerProperty("caption", String.class, "");
    languageItems.addContainerProperty("locale", Locale.class, Locale.ENGLISH);

    fillItem(languageItems.addItem(Locale.GERMAN), "de", Locale.GERMANY);
    fillItem(languageItems.addItem(Locale.ENGLISH), "en", Locale.ENGLISH);
    fillItem(languageItems.addItem(new Locale("sv")), "sv", new Locale("sv"));

    return languageItems;
  }

  @SuppressWarnings("unchecked")
  private void fillItem(Item item, String langCode, Locale locale) {
    item.getItemProperty("icon").setValue(new ThemeResource("icons/" + langCode + ".png"));
    ResourceBundle bundle = ResourceBundle.getBundle("messages", currentLocale);
    item.getItemProperty("caption").setValue(bundle.getString(langCode));
    item.getItemProperty("locale").setValue(locale);
  }

  private void createVelocityContexts() {
    localizedVelocityContexts = new HashMap<Locale, Context>();
    localizedVelocityContexts.put(Locale.GERMAN, createContextForLocale(Locale.GERMAN));
    localizedVelocityContexts.put(Locale.ENGLISH, createContextForLocale(Locale.ENGLISH));
    localizedVelocityContexts.put(new Locale("sv"), createContextForLocale(new Locale("sv")));
  }

  private Context createContextForLocale(Locale locale) {
    EasyFactoryConfiguration config = new EasyFactoryConfiguration();
    config.toolbox(Scope.APPLICATION).tool("msg", ResourceTool.class).property("bundles", "messages")
        .property("locale", locale);

    ToolManager manager = new ToolManager(false, false);
    manager.configure(config);

    return manager.createContext();
  }

  private void setErrorMessage() {
    VerticalLayout layout = new VerticalLayout();
    layout.addComponent(new Label("Unable to load CustomLayout. Check console output for details."));
    setContent(layout);
  }

  private void createVelocityEngine() {
    velocityEngine = new VelocityEngine();

    Properties velocityProperties = new Properties();
    velocityProperties.put("resource.loader", "url");
    velocityProperties
        .put("url.resource.loader.class", "org.apache.velocity.runtime.resource.loader.URLResourceLoader");
    velocityProperties.put("url.resource.loader.root", "http://localhost:8080"
        + VaadinService.getCurrentRequest().getContextPath() + "/VAADIN/themes/" + getTheme() + "/layouts/");
    velocityEngine.init(velocityProperties);
  }

  private InputStream getLayoutTemplate(String templateName) {
    Template template = velocityEngine.getTemplate(templateName + ".html");
    StringWriter writer = new StringWriter();

    Context ctx = localizedVelocityContexts.get(currentLocale);
    if (ctx == null) {
      ctx = localizedVelocityContexts.get(Locale.ENGLISH);
    }
    template.merge(ctx, writer);
    return new ByteArrayInputStream(writer.toString().getBytes());
  }
}
