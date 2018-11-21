package de.altenerding.biber.pinkie.business.config.boundary;

import de.altenerding.biber.pinkie.business.config.entity.Configuration;
import de.altenerding.biber.pinkie.business.nuLiga.entity.TokenResult;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static de.altenerding.biber.pinkie.business.config.entity.ConfigProperty.NU_LIGA_API_TOKEN;
import static de.altenerding.biber.pinkie.business.config.entity.ConfigProperty.NU_LIGA_API_TOKEN_REFRESH;

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

	public void updateFromTokenResult(TokenResult tokenResult) {
		Configuration configToken = new Configuration(NU_LIGA_API_TOKEN, tokenResult.getAccessToken());
		Configuration configRefreshToken = new Configuration(NU_LIGA_API_TOKEN_REFRESH, tokenResult.getRefreshToken());
		updateConfig(configToken);
		updateConfig(configRefreshToken);
	}

	public void updateConfig(Configuration configuration) {
		logger.info("Updating config for property={}", configuration.getProperty());
		em.merge(configuration);
		em.flush();

	}
}
