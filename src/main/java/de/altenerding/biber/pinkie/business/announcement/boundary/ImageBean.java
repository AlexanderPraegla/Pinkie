package de.altenerding.biber.pinkie.business.announcement.boundary;

import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@ManagedBean
@RequestScoped
public class ImageBean {

	private Logger logger;

	public InputStream getPictureOfTheWeek() throws Exception{
		logger.info("Loading picture of the week");
//		return new FileInputStream(new File("/home/glassfish", "1508191781_Sportpark_Schollbach_Handball_Altenerding_Foto_Naglik_8207.jpg"));
		return new FileInputStream(new File("C:\\Users\\Alex\\Pictures", "1508191781_Sportpark_Schollbach_Handball_Altenerding_Foto_Naglik_8207.jpg"));
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
