package de.oio.vaadin.uriactions;

import com.google.common.eventbus.Subscribe;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.UI;
import de.oio.vaadin.event.EventBus;
import de.oio.vaadin.event.impl.navigation.NavigateToURIEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.roklib.urifragmentrouting.UriActionMapperTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.uriactions.UriFragmentActionNavigatorWrapper;

import java.util.List;

@Slf4j
@UIScope
@Component
public class URIActionManager {
  private UriFragmentActionNavigatorWrapper uriActionNavigator;
  @Getter
  private UriActionMapperTree uriActionMapperTree;

  @Autowired
  public URIActionManager(EventBus eventBus) {
    eventBus.register(this);
  }

  public void initialize(UriActionMapperTree uriActionMapperTree, RoutingContextData routingContextData) {
    this.uriActionMapperTree = uriActionMapperTree;
    uriActionNavigator = new UriFragmentActionNavigatorWrapper(UI.getCurrent());
    uriActionNavigator.setUriActionMapperTree(uriActionMapperTree);
    uriActionNavigator.setRoutingContext(routingContextData);

    logActionOverview();
  }

  @Subscribe
  public void receiveNavigationEvent(final NavigateToURIEvent event) {
    if (log.isTraceEnabled()) {
      log.trace("Handling " + event);
    }
    uriActionNavigator.getNavigator().navigateTo(event.getNavigationTarget());
  }

  /**
   * Prints an overview of all registered URI action handlers along with their respective parameters
   * as debug log statements. This looks like the following example:
   * <p>
   * <pre>
   * <blockquote>
   *   /admin
   *   /admin/users
   *   /articles
   *   /articles/show ? {SingleIntegerURIParameter : articleId}
   *   /login
   * </blockquote>
   * </pre>
   */
  public void logActionOverview() {
    if (!log.isDebugEnabled()) {
      return;
    }
    final List<String> uriOverview = uriActionMapperTree.getMapperOverview();
    log.debug("Logging registered URI action handlers:");
    final StringBuilder buf = new StringBuilder();
    buf.append('\n');
    for (final String url : uriOverview) {
      buf.append('\t').append(url).append('\n');
    }
    log.debug(buf.toString());
  }
}
