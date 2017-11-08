package de.altenerding.biber.pinkie.business.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.io.Serializable;

public class LoggerProducer implements Serializable {
	/**
	 * @param injectionPoint
	 * @return logger
	 */
	@Produces
	public Logger produceLogger(InjectionPoint injectionPoint) {
		return LogManager.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}
}