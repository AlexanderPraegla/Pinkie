package de.altenerding.biber.pinkie.business.notification.control;

import de.altenerding.biber.pinkie.business.notification.entity.Message;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Event;
import javax.inject.Inject;

public class NotificationProcessor {

	@Inject
	private Event<Message> messageEvent;
	@Inject
	private Logger logger;
    @Inject
    private NotificationSettingsProvider notificationSettingsProvider;




}
