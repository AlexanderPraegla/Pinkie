package de.altenerding.biber.pinkie.business.notification;

import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class NotificationService {

	private Logger logger;

	public void sendResettedPassword(String eMail, String passwordNew) {
		logger.info("Dummy method to send email to user if password was resetted");
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
