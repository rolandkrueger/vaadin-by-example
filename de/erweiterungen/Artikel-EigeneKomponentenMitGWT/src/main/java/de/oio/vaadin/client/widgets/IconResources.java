package de.oio.vaadin.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * GWT Client Bundle für das Icon des {@link ResetButton}s. Das Interface muss
 * von uns nirgends implementiert werden. Der GWT-Compiler kümmert sich darum,
 * eine fertig konfigurierte Instanz des Interfaces bereitzustellen.
 */
public interface IconResources extends ClientBundle {
	/**
	 * Instanz des Client Bundles, die uns durch den GWT Compiler zur Verfügung
	 * gestellt wird. Wir lassen das Client Bundle mit {@link GWT#create(Class)}
	 * erzeugen, damit der GWT Compiler das zurückgelieferte Objekt für uns
	 * entsprechend konfigurieren kann.
	 */
	public static final IconResources INSTANCE = GWT.create(IconResources.class);

	/**
	 * Liefert uns das Icon für den {@link ResetButton} als
	 * {@link ImageResource}. Die Datei reset.png wird im selben Verzeichnis
	 * erwartet wie das Interface.
	 * 
	 * @return das Icon als {@link ImageResource}
	 */
	@Source("reset.png")
	ImageResource resetIcon();
}
