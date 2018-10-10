package de.altenerding.biber.pinkie.presentation.mapping;

import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.Document;
import de.altenerding.biber.pinkie.business.file.entity.FileCategory;
import de.altenerding.biber.pinkie.business.file.entity.FileMapping;
import de.altenerding.biber.pinkie.business.file.entity.Image;
import de.altenerding.biber.pinkie.business.file.entity.TextMapping;
import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import net.bootsfaces.utils.FacesMessages;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;

@Named
@RequestScoped
public class FileMappingBean {

	@Inject
	private FileService fileService;
	@Inject
	private Logger logger;
	private Part file;
	private String displayedName;

	public FileMapping getSingleFileMapping(String page, String key) {
		return fileService.getSingleFileMapping(page, key);
	}

	public TextMapping getSingleTextMapping(String page, String key) {
		return fileService.getSingleTextMapping(page, key);
	}

	public List<FileMapping> getMultipeFileMappings(String page, String key) {
		return fileService.getMultipeFileMappings(page, key);
	}

	@Access(role = Role.ADMIN)
	public void updateSingleMappingText(String text, String page, String key) {
		TextMapping mapping = fileService.getSingleTextMapping(page, key);

		if (mapping == null) {
			mapping = new TextMapping();
			mapping.setPage(page);
			mapping.setKey(key);
		}

		mapping.setText(text);
		fileService.updateMapping(mapping);
	}


	@Access(role = Role.ADMIN)
	public void updateSingleMappingFileImage(String page, String key) throws Exception {
		FileMapping mapping = fileService.getSingleFileMapping(page, key);

		if (mapping == null) {
			mapping = new FileMapping();
			mapping.setPage(page);
			mapping.setKey(key);
		}

		Image image = fileService.uploadImage(file, FileCategory.IMAGES, displayedName);
		mapping.setFile(image);
		fileService.updateMapping(mapping);
	}

	@Access(role = Role.ADMIN)
	public void createMappingfile(String page, String key) throws Exception {
		FileMapping fileMapping = new FileMapping();
		fileMapping.setPage(page);
		fileMapping.setKey(key);

		Document document = fileService.uploadDocument(file, FileCategory.DOCUMENTS, displayedName);
		fileMapping.setFile(document);

		fileService.updateMapping(fileMapping);
	}

	@Access(role = Role.ADMIN)
	public void deleteDocument(FileMapping fileMapping) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		try {
			fileService.deleteMapping(fileMapping);
			fileService.deleteDocument(fileMapping.getDocument());
		} catch (IOException e) {
			logger.error("Error while deleting document for mapping with id={}", fileMapping.getId(), e);
			FacesMessages.error("Fehler beim löschen des Dokuments");
		}

//		return "/secure/about/editLottery.xhtml?faces-redirect=true&includeViewParams=true";
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public void setDisplayedName(String displayedName) {
		this.displayedName = displayedName;
	}

	public String getDisplayedName() {
		return displayedName;
	}
}
