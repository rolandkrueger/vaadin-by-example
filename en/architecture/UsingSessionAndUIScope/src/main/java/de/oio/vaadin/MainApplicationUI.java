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

// If the user hits F5 (browser refresh) a new UI object shall not be created
@PreserveOnRefresh
public class MainApplicationUI extends UI {
	private final static String SESSION_SCOPED_VALUE_ID = "sessionScopedValue";

	private ObjectProperty<String> uiScopedVariable;
	private Property<String> overviewTableProperty;
	private TextField uiScopedValueTF;
	private TextField sessionScopedValueTF;

	@Override
	protected void init(VaadinRequest request) {

		initSessionScopedVariable();
		initUIScopedVariable();

		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setMargin(true);

		String headlineText = "<h1>Using Vaadin's Session and UI Scope</h1><hr/>";
		Label heading = new Label(headlineText, ContentMode.HTML);

		layout.addComponent(heading);

		layout.addComponent(new Label(
				"Open this application in a new browser window/tab <a href=\"#\" target=\"_blank\">with this link</a> and"
						+ " refresh the browser contents after having edited the scoped variables.",
				ContentMode.HTML));

		MainApplicationUI.getCurrentUIScopedVariable().addValueChangeListener(
				new ValueChangeListener() {
					@Override
					public void valueChange(ValueChangeEvent event) {
						refreshOverviewTable();
					}
				});

		layout.addComponent(new Label("Current UI's ID: "
				+ String.valueOf(UI.getCurrent().getUIId())));

		layout.addComponent(createFormLayout());
		layout.addComponent(new Label(overviewTableProperty, ContentMode.HTML));

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

	private void initUIScopedVariable() {
		uiScopedVariable = new ObjectProperty<String>("");
		overviewTableProperty = new ObjectProperty<String>("");
		refreshOverviewTable();
	}

	private void initSessionScopedVariable() {
		if (getSessionScopedVariable() == null) {
			try {
				VaadinSession.getCurrent().getLockInstance().lock();
				VaadinSession.getCurrent()
						.setAttribute(SESSION_SCOPED_VALUE_ID,
								new ObjectProperty<String>(""));
			} finally {
				VaadinSession.getCurrent().getLockInstance().unlock();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Property<String> getSessionScopedVariable() {
		Object value = VaadinSession.getCurrent().getAttribute(
				SESSION_SCOPED_VALUE_ID);
		return value == null ? null : (Property<String>) value;
	}

	public ObjectProperty<String> getUIScopedVariable() {
		return uiScopedVariable;
	}

	public static ObjectProperty<String> getCurrentUIScopedVariable() {
		return ((MainApplicationUI) UI.getCurrent()).getUIScopedVariable();
	}

	private FormLayout createFormLayout() {
		FormLayout formLayout = new FormLayout();
		formLayout.setCaption("Edit session/UI scoped variables:");
		uiScopedValueTF = new TextField("UI-scoped value:",
				MainApplicationUI.getCurrentUIScopedVariable());
		sessionScopedValueTF = new TextField("Session-scoped value:",
				getSessionScopedVariable());

		uiScopedValueTF.setImmediate(true);
		sessionScopedValueTF.setImmediate(true);

		formLayout.addComponent(uiScopedValueTF);
		formLayout.addComponent(sessionScopedValueTF);

		return formLayout;
	}

	private void refreshOverviewTable() {
		StringBuilder buf = new StringBuilder();
		buf.append("<h2>Overview of all UI scoped variables in the current session</h2>");
		buf.append("There are currently ")
				.append(VaadinSession.getCurrent().getUIs().size())
				.append(" UI objects active for this session (Session ID <em>");
		buf.append(VaadinSession.getCurrent().getSession().getId()).append(
				"</em>).");
		buf.append("<table>");

		for (UI ui : VaadinSession.getCurrent().getUIs()) {
			buf.append("<tr><td>");
			buf.append("UI ID ").append(ui.getUIId()).append(": ");
			buf.append("</td><td>");
			buf.append(((MainApplicationUI) ui).getUIScopedVariable()
					.getValue());
			buf.append("</td></tr>");
		}

		buf.append("</table>");
		overviewTableProperty.setValue(buf.toString());
	}
}
