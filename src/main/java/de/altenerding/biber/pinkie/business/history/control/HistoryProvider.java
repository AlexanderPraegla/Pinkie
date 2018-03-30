package de.altenerding.biber.pinkie.business.history.control;

import de.altenerding.biber.pinkie.business.history.entity.HistoryEvent;
import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.timeline.TimelineEvent;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class HistoryProvider {

	private Logger logger;
	@PersistenceContext
	private EntityManager em;

	public List<TimelineEvent> getHistoryEvents() {
		logger.info("Loading all historical events");
		List<HistoryEvent> eventList = em.createNamedQuery("HistoryEvent.getAll", HistoryEvent.class).getResultList();

		List<TimelineEvent> timelineEvents = new ArrayList<>();
		for (HistoryEvent historyEvent : eventList) {
			timelineEvents.add(new TimelineEvent(historyEvent, historyEvent.getEventDate()));
		}
		return timelineEvents;
	}

	@Access(role = Role.PRESS)
	public void updateHistoryEvent(HistoryEvent event) {
		logger.info("Updating event with id={}", event.getId());
		em.merge(event);
		em.flush();
	}

	@Access(role = Role.PRESS)
	public void createHistoryEvent(HistoryEvent event) {
		logger.info("Creating new history event");
		em.persist(event);
		em.flush();
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public void deleteEvent(HistoryEvent event) {
		logger.info("Deleting event with id={}", event.getId());
		event = em.merge(event);
		em.remove(event);
		em.flush();
	}
}
