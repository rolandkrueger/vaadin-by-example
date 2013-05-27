package de.oio.vaadin.views.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.appbase.components.TranslatedCustomLayout;
import org.vaadin.appbase.service.IMessageProvider;
import org.vaadin.highlighter.ComponentHighlighterExtension;

import com.google.common.base.Preconditions;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;

import de.oio.vaadin.DemoUI;
import de.oio.vaadin.demo.AbstractDemo;

@Configurable
public class DemoView extends TranslatedCustomLayout {

	private AbstractDemo demo;
	@Autowired
	private IMessageProvider messageProvider;

	public DemoView(AbstractDemo demo) {
		super("demo");
		Preconditions.checkNotNull(demo);
		this.demo = demo;
	}

	@Override
	public void buildLayout() {
		super.buildLayout();

		DemoInfoPanel demoInfoPanel = new DemoInfoPanel();
		demoInfoPanel.buildLayout();
		getLayout()
				.addComponent(demoInfoPanel.getContent(), "descriptionPanel");
		getLayout().addComponent(demo.getView(), "mainPanel");
	}

	private class DemoInfoPanel extends TranslatedCustomLayout {
		public DemoInfoPanel() {
			super("demoInfo");
		}

		@Override
		public void buildLayout() {
			super.buildLayout();
			if (DemoUI.isDebugMode()) {
				new ComponentHighlighterExtension(getLayout());
			}

			getLayout().addComponent(
					new Label(messageProvider.getMessage(demo.getDemoInfo()
							.getDemoHeadlineKey())), "demoHeadline");
			getLayout().addComponent(
					new Link(demo.getDemoInfo().getBlogPostTitle(),
							new ExternalResource(demo.getDemoInfo()
									.getBlogPostURI())), "linkToBlogPost");
			getLayout().addComponent(
					new Link(demo.getDemoInfo().getCodeHostingURI(),
							new ExternalResource(demo.getDemoInfo()
									.getCodeHostingURI())), "linkToDemoCode");
			getLayout().addComponent(
					new Label(messageProvider.getMessage(demo.getDemoInfo()
							.getShortDescriptionKey())), "shortDescription");
		}
	}
}
