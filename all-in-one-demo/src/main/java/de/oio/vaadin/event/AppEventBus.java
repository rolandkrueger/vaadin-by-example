package de.oio.vaadin.event;

import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.vaadin.spring.UIScope;

@Component
@UIScope
@Slf4j
public class AppEventBus implements EventBus {

  private static final long serialVersionUID = 1225711706929234463L;

  private com.google.common.eventbus.EventBus eventbus;

  public AppEventBus() {
    eventbus = new com.google.common.eventbus.EventBus(new SubscriberExceptionHandler() {
      @Override
      public void handleException(Throwable exception, SubscriberExceptionContext context) {
        log.error("Could not dispatch event: " + context.getSubscriber() + " to " + context.getSubscriberMethod());
        exception.printStackTrace();
      }
    });
  }

  @Override
  public void post(Event event) {
    if (log.isTraceEnabled()) {
      log.trace(event.getSource() + " posted event: " + event);
    }
    eventbus.post(event);
  }

  @Override
  public void register(Object listener) {
    if (log.isTraceEnabled()) {
      log.trace("Registering event bus listener: " + listener);
    }
    eventbus.register(listener);
  }

  @Override
  public void unregister(Object listener) {
    if (log.isTraceEnabled()) {
      log.trace("Removing listener from event bus: " + listener);
    }
    eventbus.unregister(listener);
  }
}
