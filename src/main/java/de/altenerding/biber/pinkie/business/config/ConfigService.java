package de.altenerding.biber.pinkie.business.config;

import de.altenerding.biber.pinkie.business.config.entity.Configuration;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ConfigService {

	@Inject
	private Logger logger;
	@PersistenceContext
	private EntityManager em;

	public Configuration getConfigByProperty(String property) {
		Configuration configuration = em.find(Configuration.class, property);

		if (configuration == null) {
			throw new RuntimeException(String.format("Property \'%s\' does not exists in database", property));
		}

		return configuration;
	}

	public void updateConfig(Configuration configuration) {
		logger.info("Updating config for property={}", configuration.getProperty());
		em.merge(configuration);
		em.flush();

	}
}
