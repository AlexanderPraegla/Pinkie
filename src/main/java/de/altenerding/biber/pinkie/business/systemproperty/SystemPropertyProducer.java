package de.altenerding.biber.pinkie.business.systemproperty;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@Dependent
public class SystemPropertyProducer {

	@Produces
	@SystemProperty
	public String provideServerProperties(InjectionPoint ip) {
		//get property name from annotation
		String propertyName = ip.getAnnotated().getAnnotation(SystemProperty.class).name();
		String property = System.getProperty(propertyName);

		if (property.isEmpty()) {
			throw new RuntimeException(String.format("Please provide the SystemProperty to VM Options \'%s\'", propertyName));
		}

		return property;
	}
}
