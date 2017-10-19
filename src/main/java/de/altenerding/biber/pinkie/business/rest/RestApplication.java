package de.altenerding.biber.pinkie.business.rest;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("rest")
public class RestApplication extends ResourceConfig {

	public RestApplication() {
		super.register(JacksonFeature.class)
				.packages("de.altenerding.biber.pinkie.business");
	}
}
