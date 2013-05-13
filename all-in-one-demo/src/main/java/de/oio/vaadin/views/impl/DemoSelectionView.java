package de.oio.vaadin.views.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.appbase.service.IMessageProvider;
import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;
import org.vaadin.highlighter.ComponentHighlighterExtension;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;

import de.oio.vaadin.DemoUI;
import de.oio.vaadin.demo.AbstractDemo;
import de.oio.vaadin.views.CustomLayoutView;

@Configurable
public class DemoSelectionView extends CustomLayoutView {

	private Collection<AbstractDemo> demos;
	@Autowired
	private IMessageProvider messageProvider;

	public DemoSelectionView(ITemplatingService templatingService,
			SessionContext context, Collection<AbstractDemo> demos) {
		super(templatingService, context, "demoselection");
		this.demos = demos;
	}

	@Override
	public void buildLayout() {
		super.buildLayout();
		DemoSelector selector = new DemoSelector(demos);

		getLayout().addComponent(selector, "selectionList");
	}

	private class DemoSelector extends VerticalLayout {

		public DemoSelector(Collection<AbstractDemo> demos) {
			if (DemoUI.isDebugMode()) {
				new ComponentHighlighterExtension(this);
			}

			for (AbstractDemo demo : demos) {
				// FIXME: hard-coded #!
				Link link = new Link(messageProvider.getMessage(demo
						.getDemoInfo().getDemoHeadlineKey()),
						new ExternalResource("#!"
								+ demo.getUriHandler()
										.getParameterizedActionURI(true)
										.toString()));
				link.addStyleName("demoSelectorLink");
				addComponent(link);
			}

			addStyleName("demoSelector");
		}
	}
}
