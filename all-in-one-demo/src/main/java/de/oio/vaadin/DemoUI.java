package de.oio.vaadin;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.appbase.VaadinUIServices;
import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

import de.oio.vaadin.demo.AbstractDemo;
import de.oio.vaadin.demo.componenthighlighter.ComponentHighlighterDemo;
import de.oio.vaadin.demo.i18nForCustomLayoutsUsingVelocity.I18nForCustomLayoutsUsingVelocityDemo;
import de.oio.vaadin.demo.uiscopedemo.UsingSessionAndUIScopeDemo;
import de.oio.vaadin.manager.URIActionHandlerProvider;
import de.oio.vaadin.manager.ViewManager;

@PreserveOnRefresh
@Theme("demo")
@StyleSheet("http://fonts.googleapis.com/css?family=Droid+Sans")
@Configurable(preConstruction = true)
public class DemoUI extends UI {
	@Autowired
	private SessionContext context;
	@Autowired
	private ITemplatingService templatingService;
	@Getter
	private ViewManager viewManager;
	@Getter
	private URIActionHandlerProvider uriActionHandlerProvider;

	/**
	 * The UI-scoped variable for demo {@link UsingSessionAndUIScopeDemo}.
	 */
	private ObjectProperty<String> uiScopedVariable;

	@Getter
	private Map<String, AbstractDemo> demos;

	@Override
	public void init(VaadinRequest request) {

		Page.getCurrent().setTitle("Vaadin By Example Demo");

		VaadinUIServices uiServices = new VaadinUIServices();
		uiServices.startUp();

		if (context.getLocale() == null) {
			context.setLocale(getLocale());
		}

		uriActionHandlerProvider = new URIActionHandlerProvider(
				uiServices.getUriActionManager());
		uriActionHandlerProvider.buildURILayout();

		viewManager = new ViewManager();
		viewManager.buildLayout(this);

		buildDemos();

		// initializations for demo UsingSessionAndUIScopeDemo
		initSessionScopedVariable();
		initUIScopedVariable();
	}

	private void buildDemos() {
		demos = new HashMap<>();
		addDemo(new UsingSessionAndUIScopeDemo(templatingService, context));
		addDemo(new I18nForCustomLayoutsUsingVelocityDemo(templatingService,
				context));
		addDemo(new ComponentHighlighterDemo(templatingService, context));

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
		return !VaadinService.getCurrent().getDeploymentConfiguration()
				.isProductionMode();
	}

	//
	// Methods for demo UsingSessionAndUIScopeDemo
	//

	/**
	 * Initialize a session-scoped variable whose value can be changed with a
	 * textfield. This method demonstrates the typical process of how the shared
	 * session data has to be accessed. When changing session data the session
	 * object has to be locked prior to accessing the data. After the data has
	 * been changed, the lock must be released. To release the lock safely, it
	 * is advised to do that in a finally-block.
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
				VaadinSession.getCurrent().setAttribute(
						UsingSessionAndUIScopeDemo.SESSION_SCOPED_VALUE_ID,
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
		Object value = VaadinSession.getCurrent().getAttribute(
				UsingSessionAndUIScopeDemo.SESSION_SCOPED_VALUE_ID);
		return value == null ? null : (Property<String>) value;
	}

	/**
	 * Initializes the UI-scoped variable for this {@link UI}. In this method,
	 * we don't have to check whether this variable has already been initialized
	 * as it is local to the current {@link UI}. This differs to the
	 * initialization of the session-scoped variable where we first have to
	 * check whether the value has already been initialized by another
	 * UI-object.
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
	 * guaranteed that this method will always return the correct instance for
	 * the current UI.
	 */
	public static ObjectProperty<String> getCurrentUIScopedVariable() {
		return DemoUI.getCurrent().getUIScopedVariable();
	}
}
