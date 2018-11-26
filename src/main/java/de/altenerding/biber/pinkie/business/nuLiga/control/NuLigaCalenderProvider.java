package de.altenerding.biber.pinkie.business.nuLiga.control;

import de.altenerding.biber.pinkie.business.nuLiga.entity.ClubMeeting;
import de.altenerding.biber.pinkie.business.team.entity.Team;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class NuLigaCalenderProvider {

    public static final int CONST_MEETING_DURATION = 90;
    @Inject
    private Logger logger;
    @PersistenceContext
    private EntityManager em;
    @Inject
    private NuLigaDataProvider nuLigaDataProvider;

    public static final String ALTENERDING_CLUB_ID = "10640";

    public void createIcalFileMeetingsForTeam(Team team, OutputStream outputStream) throws IOException {
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//Altenerding Handball//iCal4j 1.0//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);

        List<ClubMeeting> teamMeetings = nuLigaDataProvider.getTeamMeetings(team);

        for (ClubMeeting teamMeeting : teamMeetings) {
            String eventName = createEventName(team, teamMeeting);
            LocalDateTime localStartTime = teamMeeting.getScheduled().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            DateTime startDate = new DateTime(localStartTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            LocalDateTime localEndTime = localStartTime.plusMinutes(CONST_MEETING_DURATION);
            DateTime endDate = new DateTime(localEndTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

            VEvent vEvent = new VEvent(startDate, endDate, eventName);
            vEvent.getProperties().add(new Location(teamMeeting.getCourtHallName()));
            vEvent.getProperties().add(new Uid(RandomStringUtils.random(20, true, true)));

            calendar.getComponents().add(vEvent);
        }

        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(calendar, outputStream);

    }

    private String createEventName(Team team, ClubMeeting teamMeeting) {
        StringBuilder builder = new StringBuilder()
                .append(team.getName())
                .append(" - ");

        if (teamMeeting.getTeamHomeClubNr().equals(ALTENERDING_CLUB_ID)) {
            builder.append("Heimspiel")
                    .append(" - ")
                    .append(teamMeeting.getTeamGuest());
        } else {
            builder.append("Auswärtsspiel")
                    .append(" - ")
                    .append(teamMeeting.getTeamHome());
        }
        return builder.toString();
    }
}
