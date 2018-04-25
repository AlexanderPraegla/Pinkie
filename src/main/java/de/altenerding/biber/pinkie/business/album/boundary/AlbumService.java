package de.altenerding.biber.pinkie.business.album.boundary;

import de.altenerding.biber.pinkie.business.album.entity.Album;
import de.altenerding.biber.pinkie.business.notification.control.MessageSender;
import de.altenerding.biber.pinkie.business.notification.entity.NotificationType;
import de.altenerding.biber.pinkie.business.notification.entity.Placeholder;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class AlbumService {
	@PersistenceContext
	private EntityManager em;
	@Inject
	private Logger logger;
	@Inject
	private MessageSender messageSender;
	;

	public List<Album> getAlbums() {
		logger.info("Loading all albums");
		return em.createNamedQuery("Album.all", Album.class).getResultList();
	}

	public List<Album> getAlbums(int maxResults) {
		logger.info("Loading all albums");
		return em.createNamedQuery("Album.all", Album.class).setMaxResults(maxResults).getResultList();
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

		Map<Placeholder, String> placeholders = new HashMap<>();
		placeholders.put(Placeholder.ALBUM_TITLE, album.getDescription());
		messageSender.sendNotifications(NotificationType.ALBUM_CREATED, placeholders);
	}

	public void updateAlbum(Album album) {
		logger.info("Updating album with id={}", album.getId());
		em.merge(album);
		em.flush();
	}
}
