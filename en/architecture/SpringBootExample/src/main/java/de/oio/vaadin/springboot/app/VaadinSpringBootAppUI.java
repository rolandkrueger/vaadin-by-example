package de.oio.vaadin.springboot.app;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import de.oio.vaadin.springboot.app.mvp.impl.ContactFormPresenter;
import de.oio.vaadin.springboot.app.ui.MainView;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.VaadinUI;

@VaadinUI
@Theme("valo")
@PreserveOnRefresh
public class VaadinSpringBootAppUI extends UI {

	@Autowired
	private VaadinSessionContext context;

	@Autowired
	private ContactFormPresenter contactFormPresenter;

	@Override
	public void init(VaadinRequest request) {
		MainView mainView = new MainView();
		contactFormPresenter.setMainView(mainView);
		mainView.setPresenter(contactFormPresenter);
		mainView.buildLayout();

		setContent(mainView);
		setSizeFull();

		context.setLocale(getLocale());
	}
}
