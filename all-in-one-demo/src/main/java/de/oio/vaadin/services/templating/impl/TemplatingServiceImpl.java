package de.oio.vaadin.services.templating.impl;

import com.vaadin.server.VaadinServletService;
import com.vaadin.spring.annotation.VaadinSessionScope;
import de.oio.vaadin.DemoUI;
import de.oio.vaadin.services.application.UriActionMapperTreeService;
import de.oio.vaadin.services.templating.TemplateData;
import de.oio.vaadin.services.templating.TemplatingService;
import de.oio.vaadin.session.SessionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.messageresolver.AbstractMessageResolver;
import org.thymeleaf.messageresolver.StandardMessageResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

import static de.oio.vaadin.services.application.UriActionMapperTreeService.*;

@Service
@VaadinSessionScope
@Slf4j
public class TemplatingServiceImpl implements TemplatingService {

  private TemplateEngine templateEngine;
  private final MessageSource messageSource;
  private final SessionContext sessionContext;
  private ServletContext servletContext;
  private final UriActionMapperTreeService uriActionMapperTreeService;

  @Autowired
  public TemplatingServiceImpl(MessageSource messageSource, SessionContext sessionContext, ServletContext servletContext, UriActionMapperTreeService uriActionMapperTreeService) {
    this.servletContext = servletContext;
    this.uriActionMapperTreeService = uriActionMapperTreeService;
    ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
    templateResolver.setCacheable(true);
    templateResolver.setPrefix("layouts/");
    templateResolver.setSuffix(".html");

    StandardMessageResolver messageResolver = new StandardMessageResolver();
    templateEngine = new TemplateEngine();
    templateEngine.setMessageResolver(new MyMessageResolver());
    templateEngine.setTemplateResolver(templateResolver);
    this.messageSource = messageSource;
    this.sessionContext = sessionContext;
  }

  @Override
  public TemplateData getLayoutTemplate(String templatePath) {
    WebContext context = new WebContext(
        VaadinServletService.getCurrentServletRequest(),
        VaadinServletService.getCurrentResponse().getHttpServletResponse(),
        servletContext,
        sessionContext.getLocale());
    context.setVariable("homeHref", getLinkTargetFor(HOME));
    context.setVariable("demosHref", getLinkTargetFor(DEMOS));
    context.setVariable("aboutHref", getLinkTargetFor(ABOUT));

    String mergedTemplate = templateEngine.process(templatePath, context);
    return TemplateData.of(templatePath, new ByteArrayInputStream(mergedTemplate.getBytes(Charset.forName("UTF-8"))));
  }

  public String getLinkTargetFor(String actionMapperName) {
    return "#!" + uriActionMapperTreeService.getUriActionMapperTree().assembleUriFragment(DemoUI.getCurrent().createCapturedParameterValues(),
        uriActionMapperTreeService.getActionMapperForName(actionMapperName));
  }

  private class MyMessageResolver extends AbstractMessageResolver {
    @Override
    public String resolveMessage(ITemplateContext context, Class<?> origin, String key, Object[] messageParameters) {
      return messageSource.getMessage(key, null, context.getLocale());
    }

    @Override
    public String createAbsentMessageRepresentation(ITemplateContext context, Class<?> origin, String key, Object[] messageParameters) {
      return "??" + key + "_" + context.getLocale() + "??";
    }
  }
}
