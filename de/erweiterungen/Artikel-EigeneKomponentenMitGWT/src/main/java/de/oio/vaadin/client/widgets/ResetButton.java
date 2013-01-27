package de.oio.vaadin.client.widgets;

import com.google.gwt.user.client.ui.Image;
import com.vaadin.client.ui.VButton;

public class ResetButton extends VButton {

	public ResetButton(String caption) {
		setText(caption);
		Image resetIcon = new Image(IconResources.INSTANCE.resetIcon());
		resetIcon.setStylePrimaryName("v-icon");
		super.wrapper.insertBefore(resetIcon.getElement(), super.captionElement);
	}
}
