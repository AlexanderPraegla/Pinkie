package de.altenerding.biber.pinkie.business.nuLiga.control;

import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.Date;

@Singleton
@Startup
public class NuLigaDataTimer {

	@Inject
	private Logger logger;
	@Inject
	private NuLigaDataProcessor nuLigaDataProcessor;

	@PostConstruct
	public void init() {
        // TODO vllt brauchen wir das gar nicht, da es ja reicht wenn das 10min nach dem start ausgeführt und außerdem sollten ja schon vorher daten drinnen sein.
        // TODO so geht das deployment viel schneller
		try {
			nuLigaDataProcessor.loadNuLigaTeamData();
		} catch (Exception e) {
			logger.error("excsdfasd", e);
		}
	}

	@Schedule(minute = "*/10", hour = "*", persistent = false)
	public void loadNuLigaData() {
		logger.info("Scheduler for loading nuLiga data exectued at {}", new Date());
		try {
			nuLigaDataProcessor.loadNuLigaTeamData();
		} catch (Exception e) {
			logger.error("Error while loading nuLiga data", e);
		}
	}

}
