package de.altenerding.biber.pinkie.business.login.boundary;

import de.altenerding.biber.pinkie.business.login.control.LoginControl;
import de.altenerding.biber.pinkie.business.members.entity.Member;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;

@Stateless
public class LoginService {

	private LoginControl loginControl;

	public Member login(String alias, String password) throws IOException {
		return loginControl.login(alias, password);
	}

	//Will be used later to set password for members
	public void createLogin(String alias, String password) throws Exception {
		loginControl.createLogin(alias, password);
	}

	@Inject
	public void setLoginControl(LoginControl loginControl) {
		this.loginControl = loginControl;
	}
}
