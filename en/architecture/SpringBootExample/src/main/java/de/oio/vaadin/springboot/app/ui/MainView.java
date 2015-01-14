package de.oio.vaadin.springboot.app.ui;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnHeaderMode;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.VerticalLayout;
import de.oio.vaadin.springboot.app.mvp.IMainView;

public class MainView extends VerticalLayout implements IMainView {

	private IMainView.Presenter presenter;
	private ContactForm contactForm;

	public MainView() {
	}

	private Table createTable() {
		Table contactTable = new Table("Contacts", presenter.getDataContainer());
		contactTable.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					presenter.editContactItem(event.getItem());
				}
			}
		});
		contactTable.setColumnReorderingAllowed(true);
		contactTable.setSelectable(true);
		contactTable.setVisibleColumns(new Object[] { "firstName", "lastName",
				"address", "email", "info", "telephone", "dateOfBirth" });
		contactTable.setColumnHeaderMode(ColumnHeaderMode.EXPLICIT_DEFAULTS_ID);
		contactTable.setRowHeaderMode(RowHeaderMode.INDEX);
		contactTable.setColumnHeader("info", "Additional information");
		contactTable.setColumnHeader("firstName", "First Name");
		contactTable.setColumnHeader("lastName", "Surname");
		contactTable.setColumnHeader("dateOfBirth", "Date Of Birth");
		contactTable.setSizeFull();

		return contactTable;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void buildLayout() {
		if (presenter == null) {
			throw new IllegalStateException("No presenter has been set yet");
		}

		// Rand um das Layout einschalten
		setMargin(true);
		// Spacing zwischen den Komponenten auf dem Layout einschalten
		setSpacing(true);

		contactForm = new ContactForm();
		contactForm.setPresenter(presenter.getContactFormPresenter());
		contactForm.buildLayout();

		Panel contactPanel = new Panel(presenter.getMessageProvider()
				.getMessage("contactform.caption"), contactForm);

		// Eingabemaske für Kontakte oben auf der Seite
		addComponent(contactPanel);
		// Kontakttabelle unten auf der Seite
		addComponent(createTable());
	}

	@Override
	public void showNotification(String caption, Type type) {
		Notification.show(caption, type);
	}
}
