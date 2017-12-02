package de.altenerding.biber.pinkie.presentation.album;

import de.altenerding.biber.pinkie.business.album.boundary.AlbumService;
import de.altenerding.biber.pinkie.business.album.entity.Album;
import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.FileCategory;
import de.altenerding.biber.pinkie.business.file.entity.Image;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class AlbumBean {

	@Inject
	private AlbumService albumService;
	@Inject
	private FileService fileService;
	private List<Album> albums;
	private List<Part> albumImages;
	private Part coverImage;
	private String albumDescription;
	private long albumId;
	private Album album;

	public void initAlbum() {
		album = albumService.getAlbum(albumId);
	}

	public void upload() throws Exception {
		Album album = new Album();
		album.setDescription(albumDescription);
		List<Image> images = new ArrayList<>();
		String folder = albumDescription.toLowerCase().replace(" ", "_");
		fileService.createFolder(FileCategory.ALBUMS, folder);

		for (Part albumImage : albumImages) {
			Image image = fileService.uploadAlbumImage(albumImage, folder);
			images.add(image);
		}
		album.setImages(images);
		albumService.createAlbum(album);
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

	public List<Part> getAlbumImages() {
		return albumImages;
	}

	public void setAlbumImages(List<Part> albumImages) {
		this.albumImages = albumImages;
	}

	public Part getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(Part coverImage) {
		this.coverImage = coverImage;
	}

	public void setAlbumDescription(String albumDescription) {
		this.albumDescription = albumDescription;
	}

	public String getAlbumDescription() {
		return albumDescription;
	}

	public void setAlbumId(long albumId) {
		this.albumId = albumId;
	}

	public long getAlbumId() {
		return albumId;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}
}
