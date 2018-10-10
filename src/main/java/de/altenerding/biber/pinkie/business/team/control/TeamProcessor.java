package de.altenerding.biber.pinkie.business.team.control;

import de.altenerding.biber.pinkie.business.file.entity.Image;
import de.altenerding.biber.pinkie.business.team.entity.Team;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class TeamProcessor {

	@PersistenceContext
	private EntityManager em;
	private Logger logger;


	public void updateTeam(Team team) {
		logger.info("Updating team={} with id={}", team.getName(), team.getId());
		Image image = team.getImage();
		if (image != null) {
			em.merge(image);
		}
		em.merge(team);
		em.flush();
	}

	public Team createTeam(Team team) {
		logger.info("Creating team \'{}\'", team.getName());
		em.persist(team);
		em.flush();

		return team;
	}

	public void updateTeams(List<Team> teams) {
		for (Team team : teams) {
			updateTeam(team);
		}
	}

	public void archiveTeam(Team team) {
		logger.info("Archiving team={} with id={}", team.getName(), team.getId());
		em.createNamedQuery("Team.archiveTeam").setParameter("id", team.getId()).executeUpdate();

		logger.info("Removing existing notification setting for team with id={}", team.getId());
		em.createNamedQuery("ReportNotificationSetting.deleteByTeamId").setParameter("teamId", team.getId()).executeUpdate();
		em.flush();
	}


	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
