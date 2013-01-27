package de.oio.vaadin;

import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.oio.vaadin.components.ResettableTextArea;

public class MainApplicationUI extends UI {

	private ResettableTextArea resettableTextArea;

	@Override
	protected void init(VaadinRequest request) {

		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setMargin(true);

		String headingText = "<h1>ResettableTextArea Demo-Anwendung</h1><hr/>"
				+ "<p>Beobachten Sie den Netzwerkverkehr, z. B. mit Firebug oder der Chrome Entwicklerkonsole, "
				+ "wenn Sie eine der Schaltflächen klicken. Bei einem Klick auf 'Abschicken' wird ein Request an den Server "
				+ "geschickt, bei einem Klick auf den Reset-Button des Textfeldes nicht.</p>"
				+ "<p>Wenn Sie einen Text in das Textfeld eingeben und nach ein paar Sekunden Pause das Browserfenster schließen,"
				+ "wird der Text wieder da sein, wenn Sie die Anwendung das nächste Mal mit dem gleichen Browser öffnen.</p>";

		Label heading = new Label(headingText, ContentMode.HTML);
		layout.addComponent(heading);

		resettableTextArea = new ResettableTextArea();
		resettableTextArea.setTextAreaUID("MY_RESETTABLE_TEXTAREA");
		resettableTextArea.setWidth("200px");
		resettableTextArea.setHeight("100px");
		layout.addComponent(resettableTextArea);

		Button submitBtn = new Button("Abschicken");
		submitBtn.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Notification.show("Text wurde verschickt",
						"Vom Server empfangener Text der ResettableTextArea: \""
								+ resettableTextArea.getValue() + "\"",
						Notification.Type.HUMANIZED_MESSAGE);
			}
		});
		layout.addComponent(submitBtn);

		setContent(layout);
	}

}
