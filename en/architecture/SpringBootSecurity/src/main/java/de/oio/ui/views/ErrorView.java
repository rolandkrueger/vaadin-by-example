package de.oio.ui.views;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import de.oio.ui.components.GoToMainViewLink;

@SpringView(name = ErrorView.NAME)
public class ErrorView extends AbstractView {

	public final static String NAME = "error";

	private ObjectProperty<String> errorMessage;

	public ErrorView() {
		errorMessage = new ObjectProperty<String>("");
		Label errorMessageLabel = new Label(errorMessage, ContentMode.HTML);
		addComponent(errorMessageLabel);
		addComponent(new GoToMainViewLink());
	}

	@Override
	public void enter(ViewChangeEvent event) {
		errorMessage
				.setValue("<h1>Oops, page not found!</h1><hr/>"
						+ "Unfortunately, the page with name <em>"
						+ event.getViewName()
						+ "</em> is unknown to me :-( <br/>Please try something different.");
	}
}
