package de.altenerding.biber.pinkie.announcement.control;

import de.altenerding.biber.pinkie.announcement.entity.Announcement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class AnnouncementProvider {

	@PersistenceContext
	private EntityManager em;

	public List<Announcement> getAnnouncements() {
		return em.createNamedQuery("Announcement.findAll", Announcement.class).getResultList();
	}
}
