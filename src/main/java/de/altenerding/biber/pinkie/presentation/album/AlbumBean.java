package de.altenerding.biber.pinkie.presentation.album;

import de.altenerding.biber.pinkie.business.album.boundary.AlbumService;
import de.altenerding.biber.pinkie.business.album.entity.Album;
import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.FileCategory;
import de.altenerding.biber.pinkie.business.file.entity.Image;
import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import net.bootsfaces.utils.FacesMessages;
import org.apache.logging.log4j.Logger;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
public class AlbumBean implements Serializable {

    @Inject
    private AlbumService albumService;
    @Inject
    private FileService fileService;
    @Inject
    private Logger logger;
    private List<Album> albums;
    private List<Image> albumImages;
    private List<Part> files;
    private Part coverImage;
    private String albumDescription;
    private long albumId;
    private Album album;

    public void initAlbum() {
        album = albumService.getAlbum(albumId);
        albumImages = album.getImages();
    }

    @Access(role = Role.PRESS)
    public String upload() {
        logger.info("Creating new album with description=\'{}\'", albumDescription);
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);

        try {
            Album album = new Album();
            album.setDescription(albumDescription);

            if (descriptionNotUnique(album)) {
                FacesMessages.error("Beschreibung darf nur einmal vorkommen");
                return "success";
            }

            List<Image> images = new ArrayList<>();
            String folder = albumDescription.toLowerCase().replace(" ", "_");
            album.setFolder(folder);
            fileService.createFolder(FileCategory.ALBUMS, folder);

            for (Part albumImage : files) {
                Image image = fileService.uploadAlbumImage(albumImage, folder);
                images.add(image);
            }
            album.setImages(images);
            albumService.createAlbum(album);
        } catch (Exception e) {
            logger.error("Error while creating new album", e);
            FacesMessages.error("Es ist ein Fehler beim Erstellen des Albums aufgetreten");
        }

        return "success";
    }

    @Access(role = Role.PRESS)
    public String addImages() {
        logger.info("Adding images to album with id={}", album.getId());
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);

        if (files.size() == 0) {
            FacesMessages.error("Bitte Bilder zum Hochladen angeben");
            return "success";
        }

        try {
            List<Image> images = new ArrayList<>();
            for (Part albumImage : files) {
                Image image = fileService.uploadAlbumImage(albumImage, album.getFolder());
                images.add(image);
            }
            album.getImages().addAll(images);
            albumService.updateAlbum(album);
        } catch (Exception e) {
            logger.info("Error while adding images to album", e);
            FacesMessages.error("Es ist ein Fehler beim Hinzufügen neuer Bilder aufgetreten");
        }
        return "success";
    }

    @Access(role = Role.PRESS)
    public String archiveAlbum(Album album) {
        logger.info("Archiving album with id={}", album.getId());
        try {
            album.setArchivedOn(new Date());
            albumService.updateAlbum(album);

            FacesMessages.info("Archiviert", album.getDescription());
        } catch (Exception e) {
            logger.error("Error while archiving album", e);
            FacesMessages.error("Fehler beim Archivieren des Albums");
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);

        return "success";
    }

    @Access(role = Role.PRESS)
    public String updateAlbumDescription(Album album) {
        logger.info("Updating description for album with id={}", album.getId());
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);

        try {
            if (descriptionNotUnique(album)) {
                FacesMessages.error("Beschreibung darf nur einmal vorkommen sein");
                return "success";
            }
            albumService.updateAlbum(album);
            FacesMessages.info("Aktualisiert", album.getDescription());
        } catch (Exception e) {
            logger.error("Error while updating album", e);
            FacesMessages.error("Fehler beim Aktualisieren des Albums");
        }

        return "success";
    }

    @Access(role = Role.PRESS)
    public String removeImage(Image image) {
        logger.info("Removing image from album with id={}", album.getId());
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);

        try {
            List<Image> images = album.getImages();
            images.remove(image);
            albumService.updateAlbum(album);
            fileService.deleteImage(image);
            FacesMessages.info("Bild entfernt");
        } catch (Exception e) {
            logger.error("Error while removing image with id={} from album with={}", image.getId(), album.getId());
            FacesMessages.error("Fehler beim entfernen des Bildes");
        }
        return "success";
    }

    private boolean descriptionNotUnique(Album album) {
        for (Album a : albums) {
            if (a.getDescription()
                    .toLowerCase()
                    .replace(" ", "")
                    .equals(album.getDescription()
                            .toLowerCase()
                            .replace(" ", ""))
                    && a.getId() != album.getId()) {
                return true;
            }
        }
        return false;
    }

    public List<Album> getAlbums() {
        if (albums == null) {
            albums = albumService.getAlbums();
        }
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<Part> getFiles() {
        return files;
    }

    public void setFiles(List<Part> files) {
        this.files = files;
    }

    public Part getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(Part coverImage) {
        this.coverImage = coverImage;
    }

    public String getAlbumDescription() {
        return albumDescription;
    }

    public void setAlbumDescription(String albumDescription) {
        this.albumDescription = albumDescription;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<Image> getAlbumImages() {
        return albumImages;
    }

    public void setAlbumImages(List<Image> albumImages) {
        this.albumImages = albumImages;
    }
}
