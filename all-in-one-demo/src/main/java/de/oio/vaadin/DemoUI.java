package de.oio.vaadin;

import com.vaadin.annotations.*;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.UI;
import de.oio.vaadin.demo.AbstractDemo;
import de.oio.vaadin.demo.componenthighlighter.ComponentHighlighterDemo;
import de.oio.vaadin.demo.fieldgroupselectnestedjavabeans.FieldGroupSelectNestedJavaBeansDemo;
import de.oio.vaadin.demo.i18nforcustomlayoutsusingthymeleaf.I18nForCustomLayoutsUsingThymeleafDemo;
import de.oio.vaadin.demo.suggestingcombobox.SuggestingComboBoxDemo;
import de.oio.vaadin.demo.suggestingcombobox.component.WikipediaPageTitleAccessServiceImpl;
import de.oio.vaadin.demo.uiscope.UsingSessionAndUIScopeDemo;
import de.oio.vaadin.demo.urifragmentactions.UriFragmentActionsDemo;
import de.oio.vaadin.services.MessageProvider;
import de.oio.vaadin.services.VaadinUIServices;
import de.oio.vaadin.services.ViewManager;
import de.oio.vaadin.services.application.UriActionMapperTreeService;
import de.oio.vaadin.services.templating.TemplatingService;
import de.oio.vaadin.session.SessionContext;
import de.oio.vaadin.uriactions.RoutingContextData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.annotation.WebServlet;
import java.util.LinkedHashMap;
import java.util.Map;

@PreserveOnRefresh
@Theme("demo")
@Title("Vaadin By Example Demo")
@StyleSheet("http://fonts.googleapis.com/css?family=Roboto")
@SpringUI
@Widgetset("de.oio.vaadin.AllInOneDemoWidgetset")
public class DemoUI extends UI implements Page.UriFragmentChangedListener {

  private final static Logger LOG = LoggerFactory.getLogger(DemoUI.class);

  private final SessionContext context;
  private final TemplatingService templatingService;
  private final ViewManager viewManager;
  private final VaadinUIServices uiServices;
  private final MessageProvider messageProvider;
  private final WikipediaPageTitleAccessServiceImpl wikipediaPageTitleAccessService;
  private final UriActionMapperTreeService uriActionMapperTreeService;

  /**
   * The UI-scoped variable for demo {@link UsingSessionAndUIScopeDemo}.
   */
  private ObjectProperty<String> uiScopedVariable;
  private Map<String, AbstractDemo> demos;

  @Autowired
  public DemoUI(SessionContext context,
                TemplatingService templatingService,
                ViewManager viewManager,
                VaadinUIServices uiServices,
                MessageProvider messageProvider,
                WikipediaPageTitleAccessServiceImpl wikipediaPageTitleAccessService,
                UriActionMapperTreeService uriActionMapperTreeService) {
    this.context = context;
    this.templatingService = templatingService;
    this.viewManager = viewManager;
    this.uiServices = uiServices;
    this.messageProvider = messageProvider;
    this.wikipediaPageTitleAccessService = wikipediaPageTitleAccessService;
    this.uriActionMapperTreeService = uriActionMapperTreeService;
  }

  @Override
  public void init(VaadinRequest request) {

    LOG.info("Creating new UI with ID {} from session {}.", getUIId(), getSession().getSession().getId());
    uiServices.getUriActionManager().initialize(uriActionMapperTreeService.getUriActionMapperTree(),
        new RoutingContextData(uiServices.getEventbus(), uriActionMapperTreeService.getUriActionMapperTree()));

    if (context.getLocale() == null) {
      context.setLocale(getLocale());
    }

    viewManager.buildLayout(this);

    buildDemos();
    registerNavigationLogging();

    // initializations for demo UsingSessionAndUIScopeDemo
    initSessionScopedVariable();
    initUIScopedVariable();
  }

  private void registerNavigationLogging() {
    Page.getCurrent().addUriFragmentChangedListener(this);
  }

  private void buildDemos() {
    demos = new LinkedHashMap<>();
    addDemo(new UsingSessionAndUIScopeDemo(templatingService, context, messageProvider, uriActionMapperTreeService));
    addDemo(new I18nForCustomLayoutsUsingThymeleafDemo(templatingService, context));
    addDemo(new ComponentHighlighterDemo(templatingService, context));
    addDemo(new FieldGroupSelectNestedJavaBeansDemo(templatingService, context, messageProvider));
    addDemo(new SuggestingComboBoxDemo(templatingService, context, wikipediaPageTitleAccessService, messageProvider));
    addDemo(new UriFragmentActionsDemo(templatingService, context));
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
            new ObjectProperty<>(""));
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
    uiScopedVariable = new ObjectProperty<>("");
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

  public UriActionMapperTreeService getUriActionMapperTreeService() {
    return uriActionMapperTreeService;
  }

  public Map<String, AbstractDemo> getDemos() {
    return demos;
  }

  public static VaadinUIServices UIServices() {
    return getCurrent().uiServices;
  }

  @Override
  public void uriFragmentChanged(Page.UriFragmentChangedEvent event) {
    LOG.info("[{}::{}] visiting fragment '{}'", VaadinSession.getCurrent().getSession().getId(), getUIId(), event
        .getUriFragment());
  }
}
