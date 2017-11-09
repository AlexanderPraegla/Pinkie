package de.altenerding.biber.pinkie.business.file.control;

import de.altenerding.biber.pinkie.business.file.entity.FileMapping;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class FileMappingControl {

	private Logger logger;
	@PersistenceContext
	private EntityManager em;

	public FileMapping getFileMappingbyKeyPage(String page, String key) throws Exception {
		logger.info("Getting file for page={} and key={}", page, key);
		List<FileMapping> fileMappings = em.createNamedQuery("FileMapping.getByPageKey", FileMapping.class)
				.setParameter("page", page)
				.setParameter("key", key)
				.getResultList();

		if (fileMappings.size() == 0) {
			return null;
		} else if (fileMappings.size() > 1) {
			logger.error("There is more than one mapping for page={} and key={}", page, key);
			throw new Exception("There is more than one mapping for one key");
		}

		return fileMappings.get(0);
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

	public void updateFileMapping(FileMapping fileMapping) {
		logger.info("Updating fileMapping with id={}", fileMapping.getId());
		em.merge(fileMapping);
		em.flush();
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
