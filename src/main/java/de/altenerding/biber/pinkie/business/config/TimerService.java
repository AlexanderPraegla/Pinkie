package de.altenerding.biber.pinkie.business.config;

import de.altenerding.biber.pinkie.business.nuLiga.boundary.NuLigaService;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.Date;

@Singleton
@Startup
public class TimerService {

	@Inject
	private Logger logger;
	@Inject
	private NuLigaService nuLigaService;

	@PostConstruct
	public void init() {
		loadNuLigaData();
	}

	@Schedule(minute = "*/10", hour = "*", persistent = false)
	public void loadNuLigaData() {
		logger.info("Scheduler for loading nuLiga data exectued at {}", new Date());
		try {
			nuLigaService.loadNuLigaData();
		} catch (Exception e) {
			logger.error("Error while loading nuLiga data", e);
		}
	}

}
