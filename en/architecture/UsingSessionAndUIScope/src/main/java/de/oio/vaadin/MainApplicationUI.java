package de.oio.vaadin;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * <p>
 * This demo application demonstrates the two different scopes of a Vaadin 7
 * session. These are the common session scope and the new UI scope. The UI
 * scope represents the data needed for the contents of one browser window or
 * tab opened from the same session.
 * </p>
 * <p>
 * To demonstrate Vaadin's UI scope, a new UI object can be opened for the
 * current session by clicking a link that links to the application itself. This
 * link can be opened in a new browser window or tab. By opening a new
 * tab/window, a new UI object is created that lives in its own section of the
 * current session. You can change the UI-scoped variable for this object
 * without affecting the UI-scoped variables of the other UI objects. When you
 * edit the session-scoped variable, you'll see that the new value is shared
 * across all active UI objects. You have to reload a browser window or click
 * the refresh button to see the effect of editing the session-scoped variable
 * in a different tab.
 * </p>
 */
// We're using @PreserveOnRefresh so that no new UI object will be created if
// the we only reload the page.
@PreserveOnRefresh
public class MainApplicationUI extends UI {
	private final static String SESSION_SCOPED_VALUE_ID = "sessionScopedValue";

	/**
	 * The UI-scoped variable.
	 */
	private ObjectProperty<String> uiScopedVariable;

	/**
	 * Textfield for changing the UI-scoped variable.
	 */
	private TextField uiScopedValueTF;

	/**
	 * Textfield for changing the session-scoped variable.
	 */
	private TextField sessionScopedValueTF;

	/**
	 * Property that contains the HTML code for the table which gives an
	 * overview of all UI-scoped variables that are currently active for the
	 * current session. For the sake of simplicity, we're putting this data in a
	 * simple HTML table.
	 */
	private Property<String> overviewTableProperty;

	@Override
	protected void init(VaadinRequest request) {

		initSessionScopedVariable();
		initUIScopedVariable();

		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setMargin(true);

		String headlineText = "<h1>Using Vaadin's Session and UI Scope</h1><hr/>";
		layout.addComponent(new Label(headlineText, ContentMode.HTML));

		// we add a label that contains a link to the demo application itself.
		// When this link is opened in a new browser window or tab, a new UI
		// object for this window will be created by the framework. All field
		// variables of this class live in this new UI object and are thus
		// multiplied in memory for every new UI object.
		layout.addComponent(new Label(
				"Open this application in a new browser window/tab <a href=\"#\" target=\"_blank\" style=\"color: red;\">with this link</a> and"
						+ " refresh the browser contents after having edited the scoped variables.", ContentMode.HTML));

		MainApplicationUI.getCurrentUIScopedVariable().addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				refreshOverviewTable();
			}
		});

		// Print the ID of the current UI. This ID is automatically assigned by
		// Vaadin to new UI objects.
		layout.addComponent(new Label("Current UI's ID: " + String.valueOf(UI.getCurrent().getUIId())));

		layout.addComponent(createFormLayout());
		layout.addComponent(new Label(overviewTableProperty, ContentMode.HTML));

		// Add a refresh button that will update the overview table with the
		// most current values from each currently active UI object.
		Button refreshBtn = new Button("Refresh");
		refreshBtn.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				refreshOverviewTable();
			}
		});
		layout.addComponent(refreshBtn);

		setContent(layout);
	}

	/**
	 * Our own version of {@link UI#getCurrent()} with its return type narrowed
	 * down to our own subclass of UI. Using this method from our application
	 * allows us to access all data from this UI class in a static way. This is
	 * similar to the ThreadLocal pattern which we need not use any more in
	 * Vaadin 7.
	 */
	public static MainApplicationUI getCurrent() {
		return (MainApplicationUI) UI.getCurrent();
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

		// create the data property for the overview table
		overviewTableProperty = new ObjectProperty<String>("");

		// update the contents of the overview table
		refreshOverviewTable();
	}

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
		if (MainApplicationUI.getSessionScopedVariable() == null) {
			try {
				// lock the current HTTP session in a try-finally-block
				VaadinSession.getCurrent().getLockInstance().lock();
				// Initialize a session-scoped variable with the name given by
				// the constant SESSION_SCOPED_VALUE_ID. We're using a Vaadin
				// property as the data of the session variable so that the data
				// can be changed with a textfield and displayed in a label.
				VaadinSession.getCurrent().setAttribute(SESSION_SCOPED_VALUE_ID, new ObjectProperty<String>(""));
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
		Object value = VaadinSession.getCurrent().getAttribute(SESSION_SCOPED_VALUE_ID);
		return value == null ? null : (Property<String>) value;
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
		return MainApplicationUI.getCurrent().getUIScopedVariable();
	}

	/**
	 * Creates the form layout that contains the two textfields to edit the
	 * session and UI-scoped variables.
	 */
	private FormLayout createFormLayout() {
		FormLayout formLayout = new FormLayout();
		formLayout.setCaption("Edit session/UI scoped variables:");
		uiScopedValueTF = new TextField("UI-scoped value:", MainApplicationUI.getCurrentUIScopedVariable());
		sessionScopedValueTF = new TextField("Session-scoped value:", getSessionScopedVariable());

		uiScopedValueTF.setImmediate(true);
		sessionScopedValueTF.setImmediate(true);

		formLayout.addComponent(uiScopedValueTF);
		formLayout.addComponent(sessionScopedValueTF);

		return formLayout;
	}

	/**
	 * Updates the contents of the overview table. This table displays the
	 * contents of each UI-scoped variable that is currently active in the
	 * session. Using {@link VaadinSession#getUIs()} we're getting a handle on
	 * each {@link UI} object in the current session. We iterate over all UI
	 * objects and print the UI-scoped variable for each individual UI object in
	 * the table. Each time a new UI is opened, a new row will be added to the
	 * table.
	 */
	private void refreshOverviewTable() {
		StringBuilder buf = new StringBuilder();
		buf.append("<h2>Overview of all UI scoped variables in the current session</h2>");

		// add the number of currently active UI objects
		buf.append("There are currently ").append(VaadinSession.getCurrent().getUIs().size())
				.append(" UI objects active for this session (Session ID <em>");
		buf.append(VaadinSession.getCurrent().getSession().getId()).append("</em>).");

		// add a table showing the UI-scoped variable values for each currently
		// active UI object
		buf.append("<table>");

		for (UI ui : VaadinSession.getCurrent().getUIs()) {
			buf.append("<tr><td>");
			buf.append("UI ID ").append(ui.getUIId()).append(": ");
			buf.append("</td><td>");
			// cast the UI object to our UI subclass and get the UI-scoped
			// variable with the non-static method getUIScopedVariable(). We
			// can't use the static version here as this will always return the
			// variable for the current UI.
			buf.append(((MainApplicationUI) ui).getUIScopedVariable().getValue());
			buf.append("</td></tr>");
		}

		buf.append("</table>");

		// update the lable with the new table contents
		overviewTableProperty.setValue(buf.toString());
	}
}
