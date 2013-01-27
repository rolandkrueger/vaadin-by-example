package de.oio.vaadin.client.rpc;

import com.vaadin.shared.annotations.Delayed;
import com.vaadin.shared.communication.ServerRpc;

public interface ResettableTextAreaServerRpc extends ServerRpc {

	@Delayed(lastOnly = true)
	public void setText(String text);
}
