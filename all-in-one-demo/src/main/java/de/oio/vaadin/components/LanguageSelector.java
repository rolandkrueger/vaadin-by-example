package de.oio.vaadin.components;

import java.util.Locale;

import org.vaadin.appbase.service.IMessageProvider;
import org.vaadin.highlighter.ComponentHighlighterExtension;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.ComboBox;

import de.oio.vaadin.DemoUI;

public class LanguageSelector extends ComboBox {

	public LanguageSelector(IMessageProvider messageProvider) {
		super(messageProvider.getMessage("captions.selectLanguage"));

		if (DemoUI.isDebugMode()) {
			new ComponentHighlighterExtension(this);
		}

		setContainerDataSource(createDataSource(messageProvider));
		setItemCaptionPropertyId("caption");
		setItemIconPropertyId("icon");
		setImmediate(true);
		setNullSelectionAllowed(false);
	}

	private Container createDataSource(IMessageProvider messageProvider) {
		Container languageItems = new IndexedContainer();
		languageItems.addContainerProperty("icon", ThemeResource.class, null);
		languageItems.addContainerProperty("caption", String.class, "");
		languageItems.addContainerProperty("locale", Locale.class,
				Locale.ENGLISH);

		fillItem(languageItems.addItem(Locale.GERMAN), "de", Locale.GERMAN,
				messageProvider);
		fillItem(languageItems.addItem(Locale.ENGLISH), "en", Locale.ENGLISH,
				messageProvider);
		fillItem(languageItems.addItem(new Locale("sv")), "sv",
				new Locale("sv"), messageProvider);

		return languageItems;
	}

	@SuppressWarnings("unchecked")
	private void fillItem(Item item, String langCode, Locale locale,
			IMessageProvider messageProvider) {
		item.getItemProperty("icon").setValue(
				new ThemeResource("icons/" + langCode + ".png"));
		item.getItemProperty("caption").setValue(
				messageProvider.getMessage("language." + langCode));
		item.getItemProperty("locale").setValue(locale);
	}
}
