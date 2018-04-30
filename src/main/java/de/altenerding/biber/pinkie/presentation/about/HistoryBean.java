package de.altenerding.biber.pinkie.presentation.about;

import de.altenerding.biber.pinkie.business.history.boundary.HistoryService;
import de.altenerding.biber.pinkie.business.history.entity.HistoryEvent;
import org.primefaces.event.timeline.TimelineSelectEvent;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class HistoryBean implements Serializable {

	@Inject
	private HistoryService historyService;

	private TimelineModel timelineModel;
	private HistoryEvent selectedEvent;
	private HistoryEvent createdEvent = new HistoryEvent();

	public void updateOrCreateEvent(HistoryEvent event) {
		if (event.getId() <= 0) {
			historyService.createHistoryEvent(event);
			createdEvent = new HistoryEvent();
		} else {
			historyService.updateHistoryEvent(event);
		}
		loadHistoryEvents();
	}

	public void deleteEvent(HistoryEvent event) {
		historyService.deleteEvent(event);
		selectedEvent = null;
		loadHistoryEvents();
	}

	@PostConstruct
	public void loadHistoryEvents() {
		List<TimelineEvent> historyEvents = historyService.getHistoryEvents();
		timelineModel = new TimelineModel(historyEvents);
	}

	public void onSelect(TimelineSelectEvent e) {
		selectedEvent = (HistoryEvent) e.getTimelineEvent().getData();
	}

	public TimelineModel getTimelineModel() {
		return timelineModel;
	}

	public HistoryEvent getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(HistoryEvent selectedEvent) {
		this.selectedEvent = selectedEvent;
	}

	public HistoryEvent getCreatedEvent() {
		return createdEvent;
	}

	public void setCreatedEvent(HistoryEvent createdEvent) {
		this.createdEvent = createdEvent;
	}
}
