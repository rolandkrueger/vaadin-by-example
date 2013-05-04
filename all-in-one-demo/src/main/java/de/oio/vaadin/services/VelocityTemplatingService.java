package de.oio.vaadin.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.config.EasyFactoryConfiguration;
import org.apache.velocity.tools.generic.ResourceTool;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@org.springframework.context.annotation.Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class VelocityTemplatingService {
  private VelocityEngine velocityEngine;
  private Map<Locale, Context> contexts;

  public InputStream getLayoutTemplate(Locale forLocale, String templatePath) {
    if (isLayoutTemplateAvailableInCache()) {
      return loadFromCache();
    }

    Context ctx = getContextFromCache(forLocale);
    Template template = velocityEngine.getTemplate(templatePath + ".html");

    StringWriter writer = new StringWriter();
    template.merge(ctx, writer);
    return new ByteArrayInputStream(writer.toString().getBytes(Charset.forName("UTF-8")));
  }

  private Context getContextFromCache(Locale forLocale) {
    Context ctx = contexts.get(forLocale);
    if (ctx == null) {
      ctx = createContextForLocale(forLocale);
      contexts.put(forLocale, ctx);
    }
    return ctx;
  }

  private InputStream loadFromCache() {
    return null;
  }

  private boolean isLayoutTemplateAvailableInCache() {
    return false;
  }

  private Context createContextForLocale(Locale locale) {
    EasyFactoryConfiguration config = new EasyFactoryConfiguration();
    config.toolbox(Scope.APPLICATION).tool("msg", ResourceTool.class).property("bundles", "messages")
        .property("locale", locale);

    ToolManager manager = new ToolManager(false, false);
    manager.configure(config);

    return manager.createContext();
  }

  @PostConstruct
  public void init() throws Exception {
    contexts = Collections.synchronizedMap(new HashMap<Locale, Context>());
    velocityEngine = new VelocityEngine();
    Properties velocityProperties = new Properties();

    velocityProperties.put("resource.loader", "url");

    velocityProperties
        .put("url.resource.loader.class", "org.apache.velocity.runtime.resource.loader.URLResourceLoader");
    velocityProperties.put("url.resource.loader.root",
        "http://localhost:8080/vaadin-by-example-demo/VAADIN/themes/demo/layouts/");
    velocityEngine.init(velocityProperties);
  }
}
