package de.altenerding.biber.pinkie.business.weeklyimage.boundary;

import de.altenerding.biber.pinkie.business.weeklyimage.control.WeeklyImageProcessor;
import de.altenerding.biber.pinkie.business.weeklyimage.control.WeeklyImageProvider;
import de.altenerding.biber.pinkie.business.weeklyimage.entity.WeeklyImage;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class WeeklyImageService {

	private WeeklyImageProvider weeklyImageProvider;
	private WeeklyImageProcessor weeklyImageProcessor;

	public List<WeeklyImage> getlatestWeeklyImages() {
		return weeklyImageProvider.getlatestWeeklyImages();
	}

	public void archiveWeeklyImage(long weeklyImageId) {
		weeklyImageProcessor.archiveWeeklyImage(weeklyImageId);
	}


	public void saveWeeklyImage(WeeklyImage weeklyImage) {
		weeklyImageProcessor.saveWeeklyImage(weeklyImage);
	}

	@Inject
	public void setWeeklyImageProvider(WeeklyImageProvider weeklyImageProvider) {
		this.weeklyImageProvider = weeklyImageProvider;
	}

	@Inject
	public void setWeeklyImageProcessor(WeeklyImageProcessor weeklyImageProcessor) {
		this.weeklyImageProcessor = weeklyImageProcessor;
	}
}
