package de.altenerding.biber.pinkie.user.bounday;

import de.altenerding.biber.pinkie.user.entity.User;

public class UserService {

	public User getDefaultUser() {
		User user = new User();
		user.setFirstName("JonFromREST");
		user.setLastName("DoeFromREST");
		return user;
	}
}
