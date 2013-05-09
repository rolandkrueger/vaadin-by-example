package de.oio.vaadin.views;

import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;

import com.google.common.base.Preconditions;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomLayout;

public class CustomLayoutView extends AbstractView {

	private String templateName;
	private CustomLayout layout;

	public CustomLayoutView(ITemplatingService templatingService,
			SessionContext context, String templateName) {
		super(templatingService, context);
		Preconditions.checkNotNull(templateName);
		this.templateName = templateName;
	}

	@Override
	public void buildLayout() {
		layout = createTranslatedCustomLayout(templateName);
	}

	@Override
	public Component getContent() {
		return layout;
	}

	protected CustomLayout getLayout() {
		return layout;
	}
}
