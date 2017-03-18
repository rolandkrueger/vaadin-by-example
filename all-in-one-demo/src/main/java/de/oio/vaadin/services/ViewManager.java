package de.oio.vaadin.services;

import com.vaadin.ui.UI;
import de.oio.vaadin.DemoUI;
import de.oio.vaadin.demo.AbstractDemo;
import de.oio.vaadin.mvp.MainView;
import de.oio.vaadin.services.application.UriActionMapperTreeService;
import de.oio.vaadin.services.templating.TemplatingService;
import de.oio.vaadin.session.SessionContext;
import de.oio.vaadin.views.View;
import de.oio.vaadin.views.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.spring.UIScope;

import java.io.Serializable;
import java.util.Locale;

@UIScope
@Component
public class ViewManager implements MainView.Presenter, Serializable {

  private final TemplatingService templatingService;
  private final SessionContext context;
  private final MessageProvider messageProvider;
  private final UriActionMapperTreeService uriActionMapperTreeService;
  private MainViewImpl mainView;
  private UI ui;

  @Autowired
  public ViewManager(TemplatingService templatingService,
                     SessionContext context,
                     MessageProvider messageProvider,
                     UriActionMapperTreeService uriActionMapperTreeService) {
    this.templatingService = templatingService;
    this.context = context;
    this.messageProvider = messageProvider;
    this.uriActionMapperTreeService = uriActionMapperTreeService;
  }

  public void buildLayout(UI ui) {
    this.ui = ui;
    resetViews();
    showHomeView();
  }

  public void showAboutView() {
    activateView(new AboutView(templatingService.getLayoutTemplate("about")));
  }

  public void showDemoSelectionView() {
    activateView(new DemoSelectionView(DemoUI.getCurrent().getDemos().values(), messageProvider, templatingService
        .getLayoutTemplate("demoselection"), uriActionMapperTreeService));
  }

  public void showHomeView() {
    activateView(new HomeView(templatingService.getLayoutTemplate("home")));
  }

  private void activateView(View view) {
    view.buildLayout();
    getMainView().setContent(view.getContent());
  }

  private MainViewImpl getMainView() {
    if (mainView == null) {
      mainView = new MainViewImpl(messageProvider, templatingService.getLayoutTemplate("main"));
      mainView.buildLayout();
      mainView.setPresenter(this);
    }
    return mainView;
  }

  public void resetViews() {
    mainView = null;
    ui.setContent(getMainView().getContent());
    getMainView().setCurrentLocale(context.getLocale());
    UI.getCurrent().getNavigator().navigateTo(UI.getCurrent().getNavigator().getState());
  }

  public void showDemoView(String demoName) {
    AbstractDemo demo = DemoUI.getCurrent().getDemos().get(demoName);
    activateView(new DemoView(demo, messageProvider, templatingService));
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
