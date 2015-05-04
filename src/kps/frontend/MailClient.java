package kps.frontend;

import kps.backend.users.User;

public class MailClient {
	
	private User currentUser;

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

}
