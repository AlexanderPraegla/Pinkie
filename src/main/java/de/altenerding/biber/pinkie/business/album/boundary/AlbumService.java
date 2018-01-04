package de.altenerding.biber.pinkie.business.album.boundary;

import de.altenerding.biber.pinkie.business.album.entity.Album;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class AlbumService {
	@PersistenceContext
	private EntityManager em;
	@Inject
	private Logger logger;

	public List<Album> getAlbums() {
		logger.info("Loading all albums");
		return em.createNamedQuery("Album.all", Album.class).getResultList();
	}

	public Album getAlbum(long id) {
		logger.info("Getting album for id={}", id);
		Album album = em.find(Album.class, id);
		logger.info("Found album \'{}\'", album.getDescription());
		return album;
	}

	public void createAlbum(Album album) {
		logger.info("Creating album \'{}\'", album.getDescription());
		em.persist(album);
		em.flush();
	}

	public void updateAlbum(Album album) {
		logger.info("Updating album with id={}", album.getId());
		em.merge(album);
		em.flush();
	}
}
