package de.oio.vaadin.views;

import java.io.IOException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;

import com.google.common.base.Preconditions;
import com.vaadin.ui.CustomLayout;

public abstract class AbstractView implements IView {
	@Setter
	@Getter(AccessLevel.PROTECTED)
	private ITemplatingService templatingService;

	@Setter
	@Getter(AccessLevel.PROTECTED)
	private SessionContext context;

	public AbstractView(ITemplatingService templatingService,
			SessionContext context) {
		Preconditions.checkNotNull(templatingService);
		Preconditions.checkNotNull(context);
		this.templatingService = templatingService;
		this.context = context;
	}

	protected CustomLayout createTranslatedCustomLayout(String templateName) {
		CustomLayout layout = null;
		try {
			layout = new CustomLayout(getTemplatingService().getLayoutTemplate(
					getContext().getLocale(), templateName));
		} catch (IOException e) {
			// TODO error handling
			e.printStackTrace();
		}
		return layout;
	}
}
