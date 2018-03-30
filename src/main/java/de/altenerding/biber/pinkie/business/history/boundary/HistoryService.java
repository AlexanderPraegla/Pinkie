package de.altenerding.biber.pinkie.business.history.boundary;

import de.altenerding.biber.pinkie.business.history.control.HistoryProvider;
import de.altenerding.biber.pinkie.business.history.entity.HistoryEvent;
import org.primefaces.model.timeline.TimelineEvent;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class HistoryService {

	@Inject
	private HistoryProvider historyProvider;

	public List<TimelineEvent> getHistoryEvents() {
		return historyProvider.getHistoryEvents();
	}

	public void updateHistoryEvent(HistoryEvent event) {
		historyProvider.updateHistoryEvent(event);
	}

	public void createHistoryEvent(HistoryEvent event) {
		historyProvider.createHistoryEvent(event);
	}

	public void deleteEvent(HistoryEvent event) {
		historyProvider.deleteEvent(event);
	}
}
