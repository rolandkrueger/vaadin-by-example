package de.oio.vaadin.springboot.app.mvp;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Notification.Type;

import de.oio.vaadin.springboot.app.data.Contact;
import de.oio.vaadin.springboot.app.services.MessageProvider;

public interface MainView {

	public void setPresenter(MainView.Presenter presenter);

	public void buildLayout();

	public void showNotification(String caption, Type type);

	public interface Presenter {
		public void editContactItem(Item contactItem);

		public ContactFormView.Presenter getContactFormPresenter();

		public MessageProvider getMessageProvider();

		public BeanItemContainer<Contact> getDataContainer();
	}
}
