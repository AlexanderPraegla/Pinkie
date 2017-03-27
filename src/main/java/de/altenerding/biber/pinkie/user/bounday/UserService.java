package de.altenerding.biber.pinkie.user.bounday;

import de.altenerding.biber.pinkie.user.control.UserProvider;
import de.altenerding.biber.pinkie.user.entity.PinkieUser;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class UserService {

    private UserProvider userProvider;

    public List<PinkieUser> getUsers() {
        return userProvider.getUsers();
    }

    public PinkieUser getUser(long id) {
        return userProvider.getUserById(id);
    }

    @Inject
    public void setUserProvider(UserProvider userProvider) {
        this.userProvider = userProvider;
    }
}
