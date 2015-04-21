package de.oio.ui.events;

import com.google.common.base.Preconditions;
import de.oio.model.User;

import java.util.EventObject;

public class UserLoggedInEvent extends EventObject {

	private User user;

	public UserLoggedInEvent(Object source, User user) {
		super(source);
		Preconditions.checkNotNull(user);
		this.user = user;
	}

	public User getUser() {
		return user;
	}
}
