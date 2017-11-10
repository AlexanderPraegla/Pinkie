package de.altenerding.biber.pinkie.business.login.boundary;

import de.altenerding.biber.pinkie.business.login.control.Authenticator;
import de.altenerding.biber.pinkie.business.login.control.LoginCreator;
import de.altenerding.biber.pinkie.business.login.control.LoginModifier;
import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Role;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;

@Stateless
public class AuthenticateService implements Serializable {

	private Authenticator authenticator;
	private LoginCreator loginCreator;
	private LoginModifier loginModifier;

	public boolean validate(String alias, String password) throws IOException {
		return authenticator.validate(alias, password);
	}

	public boolean authenticateRole(Role role) {
		return authenticator.authenticateRole(role);
	}

	//Will be used later to set and create password for members
	public void createLogin(String alias, String password) throws Exception {
		loginCreator.createLogin(alias, password);
	}

	/*
		updating an existing password without providing to old one should only be possible for admins
	 */
	@Access(role = Role.ADMIN)
	public void resetPassword(String alias, String passwordNew) {
		loginModifier.resetPassword(alias, passwordNew);
	}


	public void resetPassword(String alias, String passwordNew, String passwordOld) throws Exception {
		if (!validate(alias, passwordOld)) {
			throw new Exception("Invalid password");
		}

		loginModifier.resetPassword(alias, passwordNew);
	}

	@Inject
	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}

	@Inject
	public void setLoginCreator(LoginCreator loginCreator) {
		this.loginCreator = loginCreator;
	}

	@Inject
	public void setLoginModifier(LoginModifier loginModifier) {
		this.loginModifier = loginModifier;
	}
}
