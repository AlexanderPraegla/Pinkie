package de.altenerding.biber.pinkie.user.bounday;

import de.altenerding.biber.pinkie.user.entity.User;

import javax.ejb.Stateless;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Stateless
public class UserService {

    public List<User> getUsers() {
        User pinky = new User();
        pinky.setId(1);
        pinky.setFirstName("Pinky");
        pinky.setLastName("Narf");

        User brain = new User();
        brain.setId(2);
        brain.setFirstName("Brain");
        brain.setLastName("Domination");

        List<User> users = new LinkedList<>();
        users.add(pinky);
        users.add(brain);

        return users;
    }

    public Optional<User> getUser(long id) {
        return getUsers().stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }
}
