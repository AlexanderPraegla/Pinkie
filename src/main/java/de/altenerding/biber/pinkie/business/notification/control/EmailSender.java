package de.altenerding.biber.pinkie.business.notification.control;

import de.altenerding.biber.pinkie.business.notification.entity.Email;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public class EmailSender {

	@Resource(lookup = "mail/default")
	private Session session;
	@Inject
	private Logger logger;

	public void send(@Observes Email email) {
		MimeMessage message = new MimeMessage(session);
		try {
            message.setFrom(new InternetAddress(session.getProperty("mail.from"), "Biber Info"));
			InternetAddress[] address = {new InternetAddress(email.getRecipient())};
			message.setRecipients(Message.RecipientType.TO, address);
			message.setSubject(email.getSubject());
			message.setSentDate(new Date());
			message.setContent(email.getBody(), "text/html; charset=utf-8");
			Transport.send(message, session.getProperty("mail.user"), session.getProperty("mail.password"));
        } catch (MessagingException ex) {
            logger.error("Error sending email", ex);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
	}
}
