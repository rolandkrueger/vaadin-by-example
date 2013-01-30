package de.oio.vaadin.client.rpc;

import com.vaadin.shared.annotations.Delayed;
import com.vaadin.shared.communication.ServerRpc;

/**
 * Server-RPC Interface für das Aufrufen von entfernten Methoden auf dem Server
 * durch den Client Connector.
 * 
 */
public interface ResettableTextAreaServerRpc extends ServerRpc {

	/**
	 * Sendet den aktuellen Inhalt des Textfeldes an die Serverkomponente. Die
	 * RPC-Methode ist mit der Annotation {@link Delayed} markiert, um dem
	 * Framework mitzuteilen, dass ein Aufruf dieser Methode nicht sofort zum
	 * Server geschickt werden soll. Der Aufruf soll vielmehr huckepack mit dem
	 * nächsten Server Request mitgeschickt werden, der in jedem Fall
	 * durchgeführt werden wird. Das passiert z. B. immer dann, wenn der
	 * Benutzer auf einen Button klickt. In diesem Fall muss auf jeden Fall ein
	 * Server Request durchgeführt werden. Da immer jeweils der letzte Aufruf
	 * der RPC-Methode den aktuellen Inhalt des Textfeldes enthält, können alle
	 * vorhergehenden Aufrufe, die noch nicht an den Server geschickt wurden,
	 * über das Attribut {@link Delayed#lastOnly()} verworfen werden.
	 * 
	 * @param text
	 *            der aktuelle Inhalt des Textfeldes
	 */
	@Delayed(lastOnly = true)
	public void setText(String text);
}
