package de.altenerding.biber.pinkie.business.config.control;

import de.altenerding.biber.pinkie.business.config.ConfigService;
import de.altenerding.biber.pinkie.business.config.entity.Config;
import de.altenerding.biber.pinkie.business.config.entity.ConfigProperty;
import de.altenerding.biber.pinkie.business.config.entity.Configuration;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

public class ConfigProducer {

	@Inject
	private ConfigService configService;

	@Produces
	@Config(property = ConfigProperty.NU_LIGA_API_RESFRESH_TOKEN) // TODO hier noch was anderes setzen
	public String provideServerProperties(InjectionPoint ip) {
		//get property name from annotation
		ConfigProperty property = ip.getAnnotated().getAnnotation(Config.class).property();

		Configuration configuration = configService.getConfigByProperty(property.getProperty());
		return configuration.getValue();
	}

}
