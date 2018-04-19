package de.altenerding.biber.pinkie.business.notification.control;

import de.altenerding.biber.pinkie.business.notification.entity.CommunicationTemplate;
import de.altenerding.biber.pinkie.business.notification.entity.CommunicationType;
import de.altenerding.biber.pinkie.business.notification.entity.TemplateType;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class TemplateProvider {

	@PersistenceContext
	private EntityManager em;
	@Inject
	private Logger logger;

	public CommunicationTemplate getCommunicationTemplate(CommunicationType communicationType, TemplateType templateType) throws Exception {
		logger.info("Getting communication template for communicationType={} and templateType={}", communicationType, templateType);
		CommunicationTemplate template = em.createNamedQuery("CommunicationTemplate.findByType", CommunicationTemplate.class)
				.setParameter("communicationType", communicationType)
				.setParameter("templateType", templateType)
				.getSingleResult();

		if (template == null) {
			logger.error("No communication template available for communicationType={} and templateType={}", communicationType, templateType);
			throw new Exception("No template available");
		}

		return template;
	}
}
