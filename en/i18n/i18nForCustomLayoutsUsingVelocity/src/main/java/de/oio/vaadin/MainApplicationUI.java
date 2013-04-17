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
    // the current locale can be changed by the user through a ComboBox
    currentLocale = Locale.ENGLISH;

    createVelocityEngine();
    createVelocityContexts();
    buildMainLayout();
  }

  /**
   * Sets up the main layout of the application. The layout consists of one
   * CustomLayout the contents of which can be found in main.html.
   */
  private void buildMainLayout() {
    try {
      // initialize a CustomLayout with the preprocessed template from Velocity
      CustomLayout layout = new CustomLayout(getLayoutTemplate("main"));
      // add components to the CustomLayout
      layout.addComponent(createLanguageSelector(), "languageSelector");
      setContent(layout);
    } catch (IOException e) {
      setErrorMessage();
    }
  }

  /**
   * Creates an instance of the Velocity templating engine.
   */
  private void createVelocityEngine() {
    velocityEngine = new VelocityEngine();

    // the engine is configured through a Properties object
    Properties velocityProperties = new Properties();

    // tell Velocity that it shall load its resources (i.e. its template files)
    // through a URL
    velocityProperties.put("resource.loader", "url");

    // define the implementation class for loading resources
    velocityProperties
        .put("url.resource.loader.class", "org.apache.velocity.runtime.resource.loader.URLResourceLoader");

    // configure the root URL below which all needed resources an be found. In
    // our case this is the layouts directory of our current theme 'mytheme'.
    velocityProperties.put("url.resource.loader.root", "http://localhost:8080"
        + VaadinService.getCurrentRequest().getContextPath() + "/VAADIN/themes/" + getTheme() + "/layouts/");

    // initialize the engine with the properties
    velocityEngine.init(velocityProperties);
  }

  /**
   * Creates an own Velocity context for each supported locale. The context
   * objects are stored in a map for later reference.
   */
  private void createVelocityContexts() {
    localizedVelocityContexts = new HashMap<Locale, Context>();
    // create an own context for each of our supported locales German, English,
    // and Swedish
    localizedVelocityContexts.put(Locale.GERMAN, createContextForLocale(Locale.GERMAN));
    localizedVelocityContexts.put(Locale.ENGLISH, createContextForLocale(Locale.ENGLISH));
    localizedVelocityContexts.put(new Locale("sv"), createContextForLocale(new Locale("sv")));
  }

  /**
   * Creates a Velocity context for the given locale.
   * 
   * @param locale
   *          the target locale for which the context is to be created
   * @return a Velocity context object that contains all translations found in
   *         the resource bundle for the specified locale
   */
  private Context createContextForLocale(Locale locale) {
    EasyFactoryConfiguration config = new EasyFactoryConfiguration();

    // @formatter:off
    config.toolbox(Scope.APPLICATION)
          // we use the ResourceTool class from the Velocity Tools project and put it into the 'msg' namespace
          .tool("msg", ResourceTool.class)
          // specify the resource bundle to use; our bundle is named 'messages' 
          .property("bundles", "messages")
          // define the locale
          .property("locale", locale);
    // @formatter:on

    // configure a tool manager which will be responsible for creating the
    // Velocity context
    ToolManager manager = new ToolManager(false, false);
    manager.configure(config);

    // let the manager create the context
    return manager.createContext();
  }

  /**
   * Loads a template file, sends it through the Velocity engine, and passes the
   * transformed (localized) data back to the caller. The template is loaded by
   * the URLResourceLoader as configured in {@link #createVelocityEngine()}.
   * 
   * @param templateName
   *          The template name for the CustomLayout. The naming rules are the
   *          same as with Vaadin's CustomLayout.
   * @return an InputStream containing the localized template data to be used in
   *         a CustomLayout.
   */
  private InputStream getLayoutTemplate(String templateName) {
    // let Velocity load the template from the current theme's layout directory
    // through the URLResourceLoader
    Template template = velocityEngine.getTemplate(templateName + ".html");
    StringWriter writer = new StringWriter();

    // get the Velocity context for the current locale
    Context ctx = localizedVelocityContexts.get(currentLocale);
    if (ctx == null) {
      // use English as the default language
      ctx = localizedVelocityContexts.get(Locale.ENGLISH);
    }
    // let Velocity do its template processing
    template.merge(ctx, writer);

    // return the result as an InputStream which can be used to initialize a
    // CustomLayout
    return new ByteArrayInputStream(writer.toString().getBytes());
  }

  /**
   * Creates the ComboBox for selecting the current language.
   */
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

  /**
   * Creates the data model for the language selector ComboBox.
   */
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

  private void setErrorMessage() {
    VerticalLayout layout = new VerticalLayout();
    layout.addComponent(new Label("Unable to load CustomLayout. Check console output for details."));
    setContent(layout);
  }
}
