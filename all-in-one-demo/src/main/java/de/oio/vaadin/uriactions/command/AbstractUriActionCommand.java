package de.oio.vaadin.uriactions.command;

import de.oio.vaadin.DemoUI;
import de.oio.vaadin.uriactions.RoutingContextData;
import org.roklib.urifragmentrouting.UriActionCommand;
import org.roklib.urifragmentrouting.annotation.CapturedParameter;
import org.roklib.urifragmentrouting.annotation.CurrentActionMapper;
import org.roklib.urifragmentrouting.annotation.RoutingContext;
import org.roklib.urifragmentrouting.mapper.UriPathSegmentActionMapper;
import org.roklib.urifragmentrouting.parameter.value.ParameterValue;

import java.util.Locale;

import static de.oio.vaadin.services.application.UriActionMapperTreeService.LANG_PARAMETER;
import static org.roklib.urifragmentrouting.UriActionMapperTree.ROOT_MAPPER;

public abstract class AbstractUriActionCommand implements UriActionCommand {
  RoutingContextData routingContext;

  @CurrentActionMapper
  public final void setCurrentActionMapper(UriPathSegmentActionMapper currentActionMapper) {
    DemoUI.getCurrent().setCurrentActionMapper(currentActionMapper);
  }

  @CapturedParameter(mapperName = ROOT_MAPPER, parameterName = LANG_PARAMETER)
  public final void setSelectedLanguage(ParameterValue<String> language) {
    Locale newLocale = new Locale(language.getValue());
    if (newLocale.equals(routingContext.getContext().getLocale())) {
      return;
    }
    routingContext.getContext().setLocale(newLocale);
    DemoUI.getCurrent().getViewManager().resetViews();
  }

  @RoutingContext
  public final void setRoutingContext(RoutingContextData routingContext) {
    this.routingContext = routingContext;
  }
}
