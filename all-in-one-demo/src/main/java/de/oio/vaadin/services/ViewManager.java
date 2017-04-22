package de.oio.vaadin.services;

import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.UI;
import de.oio.vaadin.DemoUI;
import de.oio.vaadin.demo.AbstractDemo;
import de.oio.vaadin.mvp.MainView;
import de.oio.vaadin.services.application.UriActionMapperTreeService;
import de.oio.vaadin.services.templating.TemplatingService;
import de.oio.vaadin.session.SessionContext;
import de.oio.vaadin.views.View;
import de.oio.vaadin.views.impl.*;
import org.roklib.urifragmentrouting.mapper.UriPathSegmentActionMapper;
import org.roklib.urifragmentrouting.parameter.value.CapturedParameterValues;
import org.roklib.urifragmentrouting.parameter.value.ParameterValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Locale;

import static de.oio.vaadin.services.application.UriActionMapperTreeService.LANG_PARAMETER;
import static org.roklib.urifragmentrouting.UriActionMapperTree.ROOT_MAPPER;

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

  public void showErrorView(String currentUriFragment) {
    activateView(new ErrorView(currentUriFragment, templatingService.getLayoutTemplate("error")));
  }

  private void activateView(View view) {
    view.buildLayout();
    getMainView().setContent(view.getContent());
  }

  private MainViewImpl getMainView() {
    if (mainView == null) {
      mainView = new MainViewImpl(messageProvider, templatingService.getLayoutTemplate("main"));
      mainView.setPresenter(this);
      mainView.buildLayout();
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
    UriPathSegmentActionMapper currentActionMapper = DemoUI.getCurrent().getCurrentActionMapper();
    CapturedParameterValues parameterValues = new CapturedParameterValues();
    parameterValues.setValueFor(ROOT_MAPPER, LANG_PARAMETER, ParameterValue.forValue(newLocale.getLanguage()));
    String newUriFragment = uriActionMapperTreeService.getUriActionMapperTree().assembleUriFragment(parameterValues, currentActionMapper);
    DemoUI.getCurrent().getNavigator().navigateTo(newUriFragment);
    context.setLocale(newLocale);
    resetViews();
  }
}
