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

	public CommunicationTemplate getCommunicationTemplate(CommunicationType communicationType, TemplateType templateType) {
		logger.info("Getting communication template for communicationType={} and templateType={}", communicationType, templateType);
		CommunicationTemplate template;
		try {
			template = em.createNamedQuery("CommunicationTemplate.findByType", CommunicationTemplate.class)
					.setParameter("communicationType", communicationType)
					.setParameter("templateType", templateType)
					.getSingleResult();
		} catch (Exception e) {
			logger.error("Error loading template for communicationType={} and templateType={}", communicationType, templateType, e);
			throw e;
		}

		return template;
	}
}
