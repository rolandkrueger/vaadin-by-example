package de.oio.vaadin.demo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import org.roklib.webapps.uridispatching.AbstractURIActionHandler;
import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;

import com.vaadin.ui.Component;

public abstract class AbstractDemo {

	@Getter(AccessLevel.PROTECTED)
	private ITemplatingService templatingService;
	@Getter(AccessLevel.PROTECTED)
	private SessionContext context;

	@Getter
	@Setter
	private AbstractURIActionHandler uriHandler;

	public AbstractDemo(ITemplatingService templatingService,
			SessionContext context) {
		super();
		this.templatingService = templatingService;
		this.context = context;
	}

	public abstract String getName();

	public abstract DemoInfo getDemoInfo();

	public abstract Component getView();
}
