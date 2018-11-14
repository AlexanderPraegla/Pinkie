package de.altenerding.biber.pinkie.presentation.team;

import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.FileCategory;
import de.altenerding.biber.pinkie.business.file.entity.Image;
import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import de.altenerding.biber.pinkie.business.team.boundary.TeamService;
import de.altenerding.biber.pinkie.business.team.entity.Team;
import de.altenerding.biber.pinkie.presentation.member.MemberBean;
import net.bootsfaces.utils.FacesMessages;
import nu.liga.open.rs.v2014.dto.championships.TeamAbbrDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
@Named
@ViewScoped
public class TeamCreationBean implements Serializable {

    private TeamService teamService;
    private FileService fileService;
    @Inject
    private MemberBean memberBean;
    private Logger logger;

    private Team team;
    private Part file;
    private String memberIndex = "";
    private String trainerIndex = "";
    private TeamAbbrDTO selectedNuLigaTeam = null;

    public void initNewTeam() {
        team = new Team();
        team.setImage(new Image());
    }

    @Access(role = Role.ADMIN)
    public String createTeam() {
        String result;
        //noinspection Duplicates,Duplicates
        try {

            if (file != null) {
                Image image = fileService.uploadImage(file, FileCategory.IMAGES_TEAM_GROUP, team.getImage().getDescription());
                team.setImage(image);
            } else {
                team.setImage(null);
            }

            List<Member> teamMembers = new ArrayList<>();
            List<Member> members = memberBean.getMembers();
            for (String index : StringUtils.split(memberIndex, ",")) {
                teamMembers.add(members.get(Integer.parseInt(index)));
            }
            team.setTeamMembers(teamMembers);

            List<Member> trainers = new ArrayList<>();
            for (String index : StringUtils.split(trainerIndex, ",")) {
                trainers.add(members.get(Integer.parseInt(index)));
            }

            if (trainers.size() > 0) {
                team.setTrainers(trainers);
            } else {
                team.setTrainers(null);
            }

            if (selectedNuLigaTeam != null) {
                team.setNuLigaGroupId(selectedNuLigaTeam.getGroupId());
                team.setNuLigaTeamId(selectedNuLigaTeam.getTeamId());
            } else {
                team.setNuLigaTeamId(null);
                team.setNuLigaGroupId(null);
            }

            Team team = teamService.createTeam(this.team);
            FacesMessages.info("Erstellt", this.team.getName());
            result = "/public/team/team.xhtml?faces-redirect=true&includeViewParams=true&teamId=" + team.getId();
        } catch (Exception e) {
            FacesMessages.error("Fehler beim erstellen eines Teams");
            logger.error("Error while creating team", e);
            result = "/secure/team/teamAdd.xhtml?faces-redirect=true&includeViewParams=true";
        }

        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        return result;
    }

    @Inject
    public void setTeamService(TeamService teamService) {
        this.teamService = teamService;
    }

    @Inject
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Inject
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public Team getTeam() {
        return team;
    }

    public String getMemberIndex() {
        return memberIndex;
    }

    public void setMemberIndex(String memberIndex) {
        this.memberIndex = memberIndex;
    }

    public String getTrainerIndex() {
        return trainerIndex;
    }

    public void setTrainerIndex(String trainerIndex) {
        this.trainerIndex = trainerIndex;
    }

    public void setMemberBean(MemberBean memberBean) {
        this.memberBean = memberBean;
    }

    public TeamAbbrDTO getSelectedNuLigaTeam() {
        return selectedNuLigaTeam;
    }

    public void setSelectedNuLigaTeam(TeamAbbrDTO selectedNuLigaTeam) {
        this.selectedNuLigaTeam = selectedNuLigaTeam;
    }
}
