package de.altenerding.biber.pinkie.business.config.entity;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static de.altenerding.biber.pinkie.business.config.entity.ConfigProperty.DEFAULT;

/**
 * Custom annotation to inject config properties from the database. Values are read in the class {@link de.altenerding.biber.pinkie.business.config.control.ConfigProducer}
 */
@Qualifier
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Config {

	/**
	 * This value must be a provided system properties
	 */
	@Nonbinding
	ConfigProperty value() default DEFAULT;
}