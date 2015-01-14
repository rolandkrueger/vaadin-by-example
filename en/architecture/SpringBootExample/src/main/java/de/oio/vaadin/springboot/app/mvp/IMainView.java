package de.oio.vaadin.springboot.app.mvp;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Notification.Type;

import de.oio.vaadin.springboot.app.data.Contact;
import de.oio.vaadin.springboot.app.services.IMessageProvider;

public interface IMainView {

	public void setPresenter(IMainView.Presenter presenter);

	public void buildLayout();

	public void showNotification(String caption, Type type);

	public interface Presenter {
		public void editContactItem(Item contactItem);

		public IContactFormView.Presenter getContactFormPresenter();

		public IMessageProvider getMessageProvider();

		public BeanItemContainer<Contact> getDataContainer();
	}
}
