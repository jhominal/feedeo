package org.feedeo.syndication;

import static org.feedeo.hibernate.InitSessionFactory.getSession;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.feedeo.model.feed.Feed;

/**
 * 
 * 
 * @author Feedeo Team
 * 
 */
public enum UpdateScheduler {

  /**
   * This is the UpdateScheduler INSTANCE.
   */
  INSTANCE;

  /**
   * This utility class models an update task. If you need to change
   * 
   * @author Feedeo Team
   * 
   */
  public static class UpdateTask implements Runnable {
    static long        defaultPeriod = 1L;
    static TimeUnit    defaultUnit   = TimeUnit.HOURS;
    private final Feed targetFeed;
    long               period;
    TimeUnit           unit;

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
     * {@inheritDoc}
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
      FeedReader.update(targetFeed);
    }

  }

  private int                      threadPoolSize = 1;
  private ScheduledExecutorService scheduler      = Executors
                                                      .newScheduledThreadPool(threadPoolSize);
  private boolean                  initialized    = false;

  /**
   * Gets the active UpdateScheduler INSTANCE.
   * 
   * @return the UpdateScheduler INSTANCE in use.
   */
  public static UpdateScheduler getInstance() {
    if (!INSTANCE.initialized) {
      INSTANCE.init();
    }
    return INSTANCE;
  }

  private UpdateScheduler() {
    // Forbidding instantiation by hiding constructor
  }

  void init() {
    @SuppressWarnings("unchecked")
    List<Feed> feeds = getSession().createCriteria(Feed.class).list();
    for (Feed feed : feeds) {
      UpdateTask task = new UpdateTask(feed);
      scheduler.scheduleAtFixedRate(task, 0L, task.period, task.unit);
    }
    initialized = true;
  }

}
