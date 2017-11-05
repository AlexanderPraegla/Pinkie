package de.altenerding.biber.pinkie.business.file.control;

import de.altenerding.biber.pinkie.business.file.entity.FileMapping;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class FileMappingControl {

	private Logger logger;
	@PersistenceContext
	private EntityManager em;

	public FileMapping getFileMappingbyKeyPage(String page, String key) {
		logger.info("Getting file for page={} and key={}", page, key);
		return em.createNamedQuery("FileMapping.getByPageKey", FileMapping.class)
				.setParameter("page", page)
				.setParameter("key", key)
				.getSingleResult();
	}

	public void replaceFileMapping(FileMapping fileMapping) {
		logger.info("Archive old file mapping for page={} and key={} befor creating new entry", fileMapping.getPage(), fileMapping.getKey());

		em.createNamedQuery("FileMapping.updateArchivedOn")
				.setParameter("page", fileMapping.getPage())
				.setParameter("key", fileMapping.getKey())
				.executeUpdate();

		logger.info("Creating file mapping for page={} and key={}", fileMapping.getPage(), fileMapping.getKey());
		em.persist(fileMapping);
		em.flush();
	}

	public void addFileMapping(FileMapping fileMapping) {
		logger.info("Creating file mapping for page={} and key={}", fileMapping.getPage(), fileMapping.getKey());

		em.persist(fileMapping);
		em.flush();
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
