package de.altenerding.biber.pinkie.presentation.club;

import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.Mapping;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class BiberFriendsBean {

	@Inject
	private FileService fileService;

	private String text;

	public void initFileMappings(String page) {
		Map<String, List<Mapping>> mappingForPage = fileService.getMappingForPage(page);

        if (mappingForPage.size() == 0) {
            return;
        }

        List<Mapping> biberFriendsMapping = mappingForPage.get("biberFriends.group.text");
        if (biberFriendsMapping != null && biberFriendsMapping.size() > 0) {
            text = biberFriendsMapping.get(0).getTextMapping().getText();
        }
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
