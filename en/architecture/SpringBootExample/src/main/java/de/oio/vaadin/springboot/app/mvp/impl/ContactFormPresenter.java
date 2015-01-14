package de.oio.vaadin.springboot.app.mvp.impl;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.Notification;
import de.oio.vaadin.springboot.app.data.Contact;
import de.oio.vaadin.springboot.app.jpa.ContactRepository;
import de.oio.vaadin.springboot.app.mvp.IContactFormView;
import de.oio.vaadin.springboot.app.mvp.IContactFormView.Presenter;
import de.oio.vaadin.springboot.app.mvp.IMainView;
import de.oio.vaadin.springboot.app.services.IMessageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.spring.UIScope;

import java.io.Serializable;
import java.util.Date;

@UIScope
@Component
public class ContactFormPresenter implements IContactFormView.Presenter,
		IMainView.Presenter, Serializable {

	private boolean isNewContact = true;
	private FieldGroup contactFieldGroup;
	private BeanItemContainer<Contact> contactContainer;
	private IMainView mainView;
	private ContactRepository contactRepository;

	private IMessageProvider messageProvider;

	public ContactFormPresenter() {
	}

	public void setMainView(IMainView mainView) {
		this.mainView = mainView;
		createFieldGroup();

		// es wird ein BeanItemContainer verwendet, der seine Properties anhand
		// der JavaBeans-Properties der Contact-JavaBean ableitet.
		contactContainer = new BeanItemContainer<Contact>(Contact.class);
		// Alle Kontakte aus der Datenbank laden und in den Container stecken
		for (Contact contactFromDB : contactRepository.findAll()) {
			contactContainer.addBean(contactFromDB);
		}
	}

	/**
	 * Create FieldGroup for the input form.
	 */
	private void createFieldGroup() {
		contactFieldGroup = new FieldGroup(new BeanItem<Contact>(new Contact()));
		contactFieldGroup.setBuffered(true);
		contactFieldGroup.setFieldFactory(new ContactFieldGroupFieldFactory());
	}

	@Override
	public void saveContact(Contact contact) {
		contactRepository.save(contact);
		if (isNewContact) {
			contactContainer.addBean(contact);
		}
		editNewContact();
	}

	@Override
	public IMessageProvider getMessageProvider() {
		return messageProvider;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void onSaveClicked() {
		try {
			contactFieldGroup.commit();
		} catch (CommitException e) {
			mainView.showNotification("Unable to save contact!",
					Notification.Type.ERROR_MESSAGE);
			return;
		}
		saveContact(((BeanItem<Contact>) contactFieldGroup.getItemDataSource())
				.getBean());
	}

	@Override
	public void onResetClicked() {
		// Wenn es lokale Änderungen am bearbeiteten Item gibt, kann das
		// Formular nicht zurückgesetzt werden. Es muss erst auf 'Discard'
		// gedrückt werden.
		if (mustAbortWithUnsavedChanges()) {
			return;
		}
		editNewContact();
	}

	@Override
	public void onDiscardClicked() {
		// alle bisher gemachten Änderungen verwerfen. Das Item wird auf den
		// zuletzt gespeicherten Zustand zurückgesetzt.
		contactFieldGroup.discard();
	}

	private void editNewContact() {
		isNewContact = true;
		contactFieldGroup
				.setItemDataSource(new BeanItem<Contact>(new Contact()));
	}

	private boolean mustAbortWithUnsavedChanges() {
		// Überprüfen, ob an dem bearbeiteten Item schon irgendwelche Änderungen
		// gemacht wurden.
		if (contactFieldGroup.isModified()) {
			// Das Item wurde modifiziert, deswegen wird eine Fehlermeldung
			// angezeigt.
			mainView.showNotification("You have unsaved changes!",
					Notification.Type.ERROR_MESSAGE);
			return true;
		}
		return false;
	}

	public void editContactItem(Item contactItem) {
		// Wenn es lokale Änderungen gibt, kann kein neues Item bearbeitet
		// werden
		if (mustAbortWithUnsavedChanges()) {
			return;
		}
		contactFieldGroup.setItemDataSource(contactItem);
		isNewContact = false;
	}

	@Override
	public BeanItemContainer<Contact> getDataContainer() {
		return contactContainer;
	}

	@Override
	public FieldGroup getFieldGroup() {
		return contactFieldGroup;
	}

	@Override
	public Presenter getContactFormPresenter() {
		return this;
	}

	@Autowired
	public void setContactRepository(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}

	@Autowired
	public void setMessageProvider(IMessageProvider messageProvider) {
		this.messageProvider = messageProvider;
	}

	private class ContactFieldGroupFieldFactory extends
			DefaultFieldGroupFieldFactory {
		@SuppressWarnings("rawtypes")
		@Override
		protected <T extends Field> T createDefaultField(Class<?> type,
				Class<T> fieldType) {
			if (type.isAssignableFrom(Date.class)
					&& fieldType.isAssignableFrom(DateField.class)) {

				DateField dateField = new DateField();
				dateField.setImmediate(true);
				return fieldType.cast(dateField);
			}
			return super.createDefaultField(type, fieldType);
		}
	}
}
