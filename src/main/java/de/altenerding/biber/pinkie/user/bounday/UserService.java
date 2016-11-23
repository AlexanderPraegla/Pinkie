package de.altenerding.biber.pinkie.user.bounday;

import de.altenerding.biber.pinkie.user.entity.User;

public class UserService {

	public User getDefaultUser() {
		User user = new User();
        user.setFirstName("Pinkie");
        user.setLastName("The Brain");
        return user;
	}
}
