package de.oio.vaadin.springboot.app.ui;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

import de.oio.vaadin.springboot.app.data.Contact;
import de.oio.vaadin.springboot.app.mvp.IContactFormView;

/**
 * Eingabemaske für Kontaktdaten. Die Eingabefelder für die Kontaktinformationen
 * werden von der FieldGroup erzeugt. In {@link #buildLayout()} werden diese
 * Eingabefelder nur noch konfiguriert und auf das Layout gelegt.
 * 
 * ContactForm ist von Klasse {@link FormLayout} abgeleitet. Damit werden die
 * Labels der Eingabefelder jeweils links vom Eingabefeld und rechtsbündig
 * angezeigt.
 * 
 * Die ContactForm kümmert sich ausschließlich um das UI. Die Datenverwaltung
 * wird vollständig vom Presenter übernommen.
 */

public class ContactForm extends FormLayout implements IContactFormView {

	private IContactFormView.Presenter presenter;

	/**
	 * Die FieldGroup und der Datencontainer werden von außen vorgegeben.
	 */
	public ContactForm() {
	}

	@Override
	public void buildLayout() {
		if (presenter == null) {
			throw new IllegalStateException("No presenter has been set yet");
		}

		setMargin(true);

		FieldGroup contactFieldGroup = presenter.getFieldGroup();
		// Eingabefeld für den Vornamen von der FieldGroup erzeugen lassen und
		// auf das Layout legen. Es wird von FieldGroup standardmäßig ein
		// TextFeld erzeugt. Eine weitere Konfiguration für dieses Feld ist hier
		// nicht notwendig.
		addComponent(contactFieldGroup.buildAndBind("firstName"));

		// Eingabefeld für den Vornamen
		TextField lastNameField = (TextField) contactFieldGroup
				.buildAndBind("lastName");
		// an dieses Feld muss ein BeanValidator gehängt werden, der sich um die
		// Validierung kümmert
		lastNameField
				.addValidator(new BeanValidator(Contact.class, "lastName"));
		addComponent(lastNameField);

		// Das Eingabefeld für die Adresse soll eine TextArea sein, um Platz für
		// längere Eingaben zu schaffen. Die gewünschte Field-Klasse wird daher
		// in buildAndBind als Klassenparameter (TextArea.class) mitgegeben.
		TextArea addressTA = (TextArea) contactFieldGroup.buildAndBind(
				"Address", "address", TextArea.class);
		// Größe der TextArea definieren
		addressTA.setRows(3);
		addressTA.setColumns(11);
		addComponent(addressTA);

		// Textfeld und Validator für die Emailadresse
		TextField emailField = (TextField) contactFieldGroup
				.buildAndBind("email");
		emailField.addValidator(new BeanValidator(Contact.class, "email"));
		addComponent(emailField);

		// TextArea für das Info-Feld
		TextArea infoTextArea = (TextArea) contactFieldGroup.buildAndBind(
				"Additional Information", "info", TextArea.class);
		infoTextArea.setRows(4);
		infoTextArea.setColumns(20);
		addComponent(infoTextArea);

		addComponent(contactFieldGroup.buildAndBind("telephone"));

		// Das Feld für das Geburtsdatum soll ein DateField sein
		addComponent(contactFieldGroup.buildAndBind("Date Of Birth",
				"dateOfBirth", DateField.class));

		// HorizontalLayout, auf das die Buttons zum Speichern und Zurücksetzen
		// gelegt werden
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setSpacing(true); // zwischen den Buttons soll etwas Platz
										// sein
		addComponent(new Label("<HR/>", ContentMode.HTML)); // horizontale Linie
															// einfügen

		// Speichern Button
		Button saveButton = new Button(presenter.getMessageProvider()
				.getMessage("button.save"));
		saveButton.setDescription("Save contact data"); // Tooltip text
		// die Listener Methode per Reflection definieren: es soll Methode
		// save() von dieser Klasse aufgerufen werden, wenn der Button gedrückt
		// wird
		saveButton.addListener(Button.ClickEvent.class, this, "save");

		Button discardButton = new Button(presenter.getMessageProvider()
				.getMessage("button.discard"));
		discardButton.setDescription("Discard your changes");
		discardButton.addListener(Button.ClickEvent.class, this, "discard");

		Button resetButton = new Button(presenter.getMessageProvider()
				.getMessage("button.reset"));
		resetButton.setDescription("Discard and reset text fields");
		// Click Listener als anonyme innere Klasse definieren
		resetButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				reset();
			}
		});

		buttonLayout.addComponent(saveButton);
		buttonLayout.addComponent(discardButton);
		buttonLayout.addComponent(resetButton);
		addComponent(buttonLayout);
	}

	public void save() {
		// Der Klick auf den Speichern-Button wird an den Presenter
		// weitergereicht und dort verarbeitet.
		presenter.onSaveClicked();
	}

	public void discard() {
		presenter.onDiscardClicked();
	}

	public void reset() {
		presenter.onResetClicked();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
}
