package de.altenerding.biber.service.user.bounday;

import de.altenerding.biber.service.user.entity.User;

public class UserService {

	public User getDefaultUser() {
		User user = new User();
		user.setFirstName("JonFromREST");
		user.setLastName("DoeFromREST");
		return user;
	}
}
