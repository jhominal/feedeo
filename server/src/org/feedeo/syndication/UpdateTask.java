package org.feedeo.syndication;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.feedeo.Feed;

/**
 * Type a description for UpdateTask here.
 * 
 * @author Feedeo Team
 *
 */
public final class UpdateTask extends TimerTask {
	static long defaultPeriod = 1L;
	static TimeUnit defaultUnit = TimeUnit.HOURS;
	final Feed targetFeed;
	long period;
	TimeUnit unit;
	
	/**
	 * @param targetFeed
	 * @param period 
	 * @param unit 
	 */
	public UpdateTask(Feed targetFeed, long period, TimeUnit unit) {
		super();
		this.targetFeed = targetFeed;
		this.period = period;
		this.unit = unit;
	}
	/**
	 * @param targetFeed
	 */
	public UpdateTask(Feed targetFeed) {
		this(targetFeed, defaultPeriod, defaultUnit);
	}
	
	/** 
	 * This implementation of the task simply runs the FeedReader 
	 * 
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		FeedReader.update(targetFeed);
	}

}
