package de.altenerding.biber.pinkie.business.systemproperty;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SystemProperty {

	/**
	 * This value must be a provided system properties
	 */
	@Nonbinding
	String name() default "";
}