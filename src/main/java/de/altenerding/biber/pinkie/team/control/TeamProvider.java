package de.altenerding.biber.pinkie.team.control;

import de.altenerding.biber.pinkie.team.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class TeamProvider {

	@PersistenceContext
	private EntityManager em;

	public List<Team> getTeams() {
		return em.createNamedQuery("Team.findAll", Team.class).getResultList();
	}

	public Team getTeamById(long id) {
		return em.createNamedQuery("Team.findById", Team.class).setParameter("id", id).getSingleResult();
	}
}
