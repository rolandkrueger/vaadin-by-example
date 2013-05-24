package de.oio.vaadin.manager;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.appbase.VaadinUIServices;
import org.vaadin.appbase.service.IMessageProvider;
import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;
import org.vaadin.appbase.view.IView;

import com.vaadin.ui.UI;

import de.oio.vaadin.DemoUI;
import de.oio.vaadin.demo.AbstractDemo;
import de.oio.vaadin.mvp.IMainView;
import de.oio.vaadin.views.impl.AboutView;
import de.oio.vaadin.views.impl.DemoSelectionView;
import de.oio.vaadin.views.impl.DemoView;
import de.oio.vaadin.views.impl.HomeView;
import de.oio.vaadin.views.impl.MainView;

@Configurable
public class ViewManager implements IMainView.Presenter {

  @Autowired
  private ITemplatingService templatingService;
  @Autowired
  private SessionContext context;
  @Autowired
  private IMessageProvider messageProvider;

  private MainView mainView;

  private UI ui;

  public void buildLayout(UI ui) {
    this.ui = ui;
    resetViews();
    showHomeView();
  }

  public void showAboutView() {
    activateView(new AboutView());
  }

  public void showDemoSelectionView() {
    activateView(new DemoSelectionView(DemoUI.getCurrent().getDemos().values()));
  }

  public void showHomeView() {
    activateView(new HomeView());
  }

  private void activateView(IView view) {
    view.buildLayout();
    getMainView().setContent(view.getContent());
  }

  private MainView getMainView() {
    if (mainView == null) {
      mainView = new MainView(messageProvider);
      mainView.buildLayout();
      mainView.setPresenter(this);
    }
    return mainView;
  }

  public void resetViews() {
    mainView = null;
    ui.setContent(getMainView().getContent());
    VaadinUIServices.UIServices().getPlaceManager().reactivateCurrentPlace();
    getMainView().setCurrentLocale(context.getLocale());
  }

  public void showDemoView(AbstractDemo demo) {
    activateView(new DemoView(demo));
  }

  @Override
  public void changeLanguage(Locale newLocale) {
    if (newLocale.equals(context.getLocale())) {
      return;
    }
    context.setLocale(newLocale);
    resetViews();
  }
}
