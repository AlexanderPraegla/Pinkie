package de.altenerding.biber.pinkie.presentation.mapping;

import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.FileMapping;
import de.altenerding.biber.pinkie.business.file.entity.TextMapping;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class FileMappingBean {

	@Inject
	private FileService fileService;

	public FileMapping getSingleFileMapping(String page, String key) {
		return fileService.getSingleFileMapping(page, key);
	}

	public TextMapping getSingleTextMapping(String page, String key) {
		return fileService.getSingleTextMapping(page, key);
	}

	public List<FileMapping> getMultipeFileMappings(String page, String key) {
		return fileService.getMultipeFileMappings(page, key);
	}
}
