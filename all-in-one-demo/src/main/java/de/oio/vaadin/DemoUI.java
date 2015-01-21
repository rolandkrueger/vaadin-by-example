package de.oio.vaadin;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import de.oio.vaadin.demo.AbstractDemo;
import de.oio.vaadin.demo.componenthighlighter.ComponentHighlighterDemo;
import de.oio.vaadin.demo.fieldgroupselectnestedjavabeans.FieldGroupSelectNestedJavaBeansDemo;
import de.oio.vaadin.demo.i18nforcustomlayoutsusingvelocity.I18nForCustomLayoutsUsingVelocityDemo;
import de.oio.vaadin.demo.suggestingcombobox.SuggestingComboBoxDemo;
import de.oio.vaadin.demo.suggestingcombobox.component.WikipediaPageTitleAccessServiceImpl;
import de.oio.vaadin.demo.uiscope.UsingSessionAndUIScopeDemo;
import de.oio.vaadin.manager.URIActionHandlerProvider;
import de.oio.vaadin.manager.ViewManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.appbase.VaadinUIServices;
import org.vaadin.appbase.service.IMessageProvider;
import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;
import org.vaadin.spring.VaadinUI;

import java.util.LinkedHashMap;
import java.util.Map;

@PreserveOnRefresh
@Theme("demo")
@Title("Vaadin By Example Demo")
@StyleSheet("http://fonts.googleapis.com/css?family=Roboto")
@VaadinUI
@Widgetset("de.oio.vaadin.AllInOneDemoWidgetset")
public class DemoUI extends UI {

  @Autowired
  private SessionContext context;

  @Autowired
  private ITemplatingService templatingService;

  @Autowired
  private ViewManager viewManager;

  @Autowired
  private VaadinUIServices uiServices;

  @Autowired
  private IMessageProvider messageProvider;

  @Autowired
  private WikipediaPageTitleAccessServiceImpl wikipediaPageTitleAccessService;

  private URIActionHandlerProvider uriActionHandlerProvider;

  /**
   * The UI-scoped variable for demo {@link UsingSessionAndUIScopeDemo}.
   */
  private ObjectProperty<String> uiScopedVariable;

  private Map<String, AbstractDemo> demos;

  @Override
  public void init(VaadinRequest request) {

    if (context.getLocale() == null) {
      context.setLocale(getLocale());
    }

    uiServices.init();

    uriActionHandlerProvider = new URIActionHandlerProvider(UIServices().getUriActionManager(), uiServices.getEventbus());
    uriActionHandlerProvider.buildURILayout();

    viewManager.buildLayout(this);

    buildDemos();

    // initializations for demo UsingSessionAndUIScopeDemo
    initSessionScopedVariable();
    initUIScopedVariable();
  }

  private void buildDemos() {
    demos = new LinkedHashMap<>();
    addDemo(new UsingSessionAndUIScopeDemo(templatingService, context, messageProvider));
    addDemo(new I18nForCustomLayoutsUsingVelocityDemo(templatingService, context));
    addDemo(new ComponentHighlighterDemo(templatingService, context));
    addDemo(new FieldGroupSelectNestedJavaBeansDemo(templatingService, context, messageProvider));
    addDemo(new SuggestingComboBoxDemo(templatingService, context, wikipediaPageTitleAccessService, messageProvider));

    uriActionHandlerProvider.registerDemos(demos.values());
    uriActionHandlerProvider.getUriActionManager().logActionOverview();
  }

  private void addDemo(AbstractDemo demo) {
    demos.put(demo.getName(), demo);
  }

  public static DemoUI getCurrent() {
    return (DemoUI) UI.getCurrent();
  }

  public static boolean isDebugMode() {
    return !VaadinService.getCurrent().getDeploymentConfiguration().isProductionMode();
  }

  //
  // Methods for demo UsingSessionAndUIScopeDemo
  //

  /**
   * Initialize a session-scoped variable whose value can be changed with a
   * textfield. This method demonstrates the typical process of how the shared
   * session data has to be accessed. When changing session data the session
   * object has to be locked prior to accessing the data. After the data has
   * been changed, the lock must be released. To release the lock safely, it is
   * advised to do that in a finally-block.
   */
  private void initSessionScopedVariable() {
    // only initialize the variable if that has not already been done by
    // another UI
    if (DemoUI.getSessionScopedVariable() == null) {
      try {
        // lock the current HTTP session in a try-finally-block
        VaadinSession.getCurrent().getLockInstance().lock();
        // Initialize a session-scoped variable with the name given by
        // the constant SESSION_SCOPED_VALUE_ID. We're using a Vaadin
        // property as the data of the session variable so that the data
        // can be changed with a textfield and displayed in a label.
        VaadinSession.getCurrent().setAttribute(UsingSessionAndUIScopeDemo.SESSION_SCOPED_VALUE_ID,
            new ObjectProperty<String>(""));
      } finally {
        // safely unlock the session in a finally block
        VaadinSession.getCurrent().getLockInstance().unlock();
      }
    }
  }

  /**
   * Provides our session-scoped variable. This is implemented as a static
   * method so that the variable can be accessed from anywhere in the
   * application similar to a ThreadLocal variable. As the variable is fetched
   * from the current session (using {@link VaadinSession#getCurrent()}) it is
   * guaranteed that this method always returns the correct instance for the
   * current session.
   */
  @SuppressWarnings("unchecked")
  public static Property<String> getSessionScopedVariable() {
    Object value = VaadinSession.getCurrent().getAttribute(UsingSessionAndUIScopeDemo.SESSION_SCOPED_VALUE_ID);
    return value == null ? null : (Property<String>) value;
  }

  /**
   * Initializes the UI-scoped variable for this {@link UI}. In this method, we
   * don't have to check whether this variable has already been initialized as
   * it is local to the current {@link UI}. This differs to the initialization
   * of the session-scoped variable where we first have to check whether the
   * value has already been initialized by another UI-object.
   */
  private void initUIScopedVariable() {
    // create the instance for the UI-scoped variable
    uiScopedVariable = new ObjectProperty<String>("");
  }

  /**
   * Returns the UI-scoped variable for this {@link UI} instance.
   */
  public ObjectProperty<String> getUIScopedVariable() {
    return uiScopedVariable;
  }

  /**
   * Returns the UI-scoped variable for this {@link UI} instance in a static
   * way. This method can be used like a common ThreadLocal variable from
   * anywhere in the the application:
   * <code>MainApplicationUI.getCurrentUIScopedVariable();</code> It is
   * guaranteed that this method will always return the correct instance for the
   * current UI.
   */
  public static ObjectProperty<String> getCurrentUIScopedVariable() {
    return DemoUI.getCurrent().getUIScopedVariable();
  }

  public ViewManager getViewManager() {
    return viewManager;
  }

  public URIActionHandlerProvider getUriActionHandlerProvider() {
    return uriActionHandlerProvider;
  }

  public Map<String, AbstractDemo> getDemos() {
    return demos;
  }

  public static VaadinUIServices UIServices() {
    return getCurrent().uiServices;
  }
}
