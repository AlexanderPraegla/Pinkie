package de.altenerding.biber.pinkie.business.sponsor;

import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class SponsorService {

	@Inject
	private Logger logger;

	@PersistenceContext
	private EntityManager em;

	public List<Sponsor> getPremiumSponsors() {
		logger.info("Loading all premum sponsors");
		return em.createNamedQuery("Sponsor.premium", Sponsor.class).getResultList();
	}

	public List<Sponsor> getNonePremiumSponsors() {
		logger.info("Loading all none premium sponsors");
		return em.createNamedQuery("Sponsor.notPremium", Sponsor.class).getResultList();
	}

	public List<Sponsor> allSponsors() {
		logger.info("Loading all sponsors");
		return em.createNamedQuery("Sponsor.all", Sponsor.class).getResultList();
	}

	public void updateSponsor(Sponsor sponsor) {
		logger.info("Updating sponsor (\'{}\') with id={}", sponsor.getName(), sponsor.getId());
		em.merge(sponsor);
		em.flush();
	}

	public void createSponsor(Sponsor sponsor) {
		logger.info("Creating sponsor with name=\'{}\'", sponsor.getName());
		em.persist(sponsor);
		em.flush();
	}
}
