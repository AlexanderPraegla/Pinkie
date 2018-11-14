package de.altenerding.biber.pinkie.business.file.control;

import de.altenerding.biber.pinkie.business.file.entity.FileMapping;
import de.altenerding.biber.pinkie.business.file.entity.Mapping;
import de.altenerding.biber.pinkie.business.file.entity.TextMapping;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileMappingControl {

	private Logger logger;
	@PersistenceContext
	private EntityManager em;

	public void updateMapping(Mapping mapping) {
		logger.info("Updating or creating mapping for page={} with key={} and id={}", mapping.getPage(), mapping.getKey(), mapping.getId());
		em.merge(mapping);
		em.flush();
	}

	public void deleteMapping(Mapping mapping) {
		logger.info("Deleting mapping for page={} with key={} and id={}", mapping.getPage(), mapping.getKey(), mapping.getId());
		mapping = em.merge(mapping);
		em.remove(mapping);
		em.flush();
	}

	public FileMapping getSingleFileMapping(String page, String key) {
		Map<String, List<Mapping>> mappingForPage = getMappingForPage(page);
		List<Mapping> mappings = mappingForPage.get(key);
		if (mappings != null) {
			//There should be only one mapped file for the provided key
			return (FileMapping) mappings.get(0);
		} else {
			return null;
		}
	}

	public TextMapping getSingleTextMapping(String page, String key) {
		Map<String, List<Mapping>> mappingForPage = getMappingForPage(page);
		List<Mapping> mappings = mappingForPage.get(key);
		if (mappings != null) {
			//There should be only one mapped text for the provided key
			return (TextMapping) mappings.get(0);
		} else {
			return null;
		}
	}

	public List<FileMapping> getMultipeFileMappings(String page, String key) {
		Map<String, List<Mapping>> mappingForPage = getMappingForPage(page);
		List<FileMapping> fileMappings = new ArrayList<>();

		if (!mappingForPage.containsKey(key)) {
			return fileMappings;
		}

		for (Mapping mapping : mappingForPage.get(key)) {
			fileMappings.add((FileMapping) mapping);
		}
		return fileMappings;
	}

	public Map<String, List<Mapping>> getMappingForPage(String page) {
		logger.debug("Loading file and text mappings for page={}", page);

		List<Mapping> mappingList = em.createNamedQuery("Mapping.getByPage", Mapping.class).setParameter("page", page).getResultList();

		Map<String, List<Mapping>> mappings = new HashMap<>();
		for (Mapping mapping : mappingList) {
			if (mappings.containsKey(mapping.getKey())) {
				List<Mapping> list = mappings.get(mapping.getKey());
				list.add(mapping);
			} else {
				List<Mapping> list = new ArrayList<>();
				list.add(mapping);
				mappings.put(mapping.getKey(), list);
			}
		}

		return mappings;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
