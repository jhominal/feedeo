package org.feedeo.syndication;

import java.util.TimerTask;

import org.feedeo.Feed;

/**
 * Type a description for UpdateTask here.
 * 
 * @author Feedeo Team
 *
 */
public final class UpdateTask extends TimerTask {
	static long defaultPeriod = 3600L*1000L;
	final Feed targetFeed;
	long period;
	
	/**
	 * @param targetFeed
	 * @param period 
	 */
	public UpdateTask(Feed targetFeed, long period) {
		super();
		this.targetFeed = targetFeed;
		this.period = period;
	}
	/**
	 * @param targetFeed
	 */
	public UpdateTask(Feed targetFeed) {
		this(targetFeed, defaultPeriod);
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
