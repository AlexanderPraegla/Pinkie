package de.altenerding.biber.pinkie.business.config.control;

import de.altenerding.biber.pinkie.business.config.boundary.ConfigService;
import de.altenerding.biber.pinkie.business.config.entity.Config;
import de.altenerding.biber.pinkie.business.config.entity.ConfigProperty;
import de.altenerding.biber.pinkie.business.config.entity.Configuration;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

/**
 * Class to load config values from the database and inject them.
 */
public class ConfigProducer {

	@Inject
	private ConfigService configService;

	@Produces
	@Config
	public String provideServerProperties(InjectionPoint ip) {
		ConfigProperty property = ip.getAnnotated().getAnnotation(Config.class).value();
		Configuration configuration = configService.getConfigByProperty(property.getProperty());
		return configuration.getValue();
	}

}
