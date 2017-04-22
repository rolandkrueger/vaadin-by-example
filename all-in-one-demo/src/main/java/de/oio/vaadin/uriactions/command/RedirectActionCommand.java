package de.oio.vaadin.uriactions.command;


import com.google.common.base.MoreObjects;
import de.oio.vaadin.event.impl.navigation.NavigateToURIEvent;
import de.oio.vaadin.uriactions.RoutingContextData;
import org.roklib.urifragmentrouting.UriActionCommand;
import org.roklib.urifragmentrouting.annotation.RoutingContext;
import org.roklib.urifragmentrouting.mapper.UriPathSegmentActionMapper;

import static com.google.common.base.Preconditions.checkNotNull;

public class RedirectActionCommand extends AbstractUriActionCommand {
  private final UriPathSegmentActionMapper redirectToHandler;

  public RedirectActionCommand(final UriPathSegmentActionMapper redirectToHandler) {
    this.redirectToHandler = checkNotNull(redirectToHandler);
  }

  @Override
  public void run() {
    routingContext.getEventBus().post(new NavigateToURIEvent(this, routingContext.getUriActionMapperTree().assembleUriFragment(redirectToHandler)));
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("target", routingContext.getUriActionMapperTree().assembleUriFragment(redirectToHandler))
        .toString();
  }
}
