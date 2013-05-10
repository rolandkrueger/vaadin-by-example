package de.oio.vaadin.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.appbase.service.AbstractUsesServiceProvider;
import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;

import com.vaadin.ui.UI;

import de.oio.vaadin.DemoUI;
import de.oio.vaadin.demo.AbstractDemo;
import de.oio.vaadin.views.IView;
import de.oio.vaadin.views.impl.AboutView;
import de.oio.vaadin.views.impl.DemoSelectionView;
import de.oio.vaadin.views.impl.DemoView;
import de.oio.vaadin.views.impl.HomeView;
import de.oio.vaadin.views.impl.MainView;

@Configurable
public class ViewManager extends AbstractUsesServiceProvider {

	@Autowired
	private ITemplatingService templatingService;
	@Autowired
	private SessionContext context;

	private MainView mainView;
	private UI ui;

	public void buildLayout(UI ui) {
		this.ui = ui;
		resetViews();
		showHomeView();
	}

	@Override
	protected void onServiceProviderSet() {
		eventbus().register(this);
	}

	public void showAboutView() {
		activateView(new AboutView(templatingService, context));
	}

	public void showDemoSelectionView() {
		activateView(new DemoSelectionView(templatingService, context, DemoUI
				.getCurrent().getDemos().values()));
	}

	public void showHomeView() {
		activateView(new HomeView(templatingService, context));
	}

	private void activateView(IView view) {
		view.buildLayout();
		getMainView().setContent(view.getContent());
	}

	private MainView getMainView() {
		if (mainView == null) {
			mainView = new MainView(templatingService, context);
			mainView.buildLayout();
		}
		return mainView;
	}

	public void resetViews() {
		mainView = null;
		ui.setContent(getMainView().getContent());
	}

	public void showDemoView(AbstractDemo demo) {
		activateView(new DemoView(templatingService, context, demo));
	}
}
