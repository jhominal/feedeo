package org.feedeo.syndication;

import static org.feedeo.hibernate.InitSessionFactory.getSession;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashSet;
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
  
  static {
    INSTANCE.init();
  }

  /**
   * This utility class models an update task. If you need to change
   * 
   * @author Feedeo Team
   * 
   */
  public static class Task implements Runnable {
    private static long     defaultPeriod = 1L;
    private static TimeUnit defaultUnit   = TimeUnit.HOURS;
    private final Feed      targetFeed;
    private long            period;
    private TimeUnit        unit;

    /**
     * @param targetFeed
     * @param period
     * @param unit
     */
    public Task(Feed targetFeed, long period, TimeUnit unit) {
      super();
      this.targetFeed = targetFeed;
      this.period = period;
      this.unit = unit;
    }

    /**
     * @param targetFeed
     */
    public Task(Feed targetFeed) {
      this(targetFeed, defaultPeriod, defaultUnit);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
      try {
        FeedFetcher.getFetcher(targetFeed.getUrl()).update();
      } catch (MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (FeedRetrievalException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

  }
  
  private Collection<Long> scheduledFeedsIds = new HashSet<Long>();

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

  public void init() {
    
    getSession().beginTransaction();
    @SuppressWarnings("unchecked")
    List<Feed> feeds = getSession().createCriteria(Feed.class).list();
    for (Feed feed : feeds) {
      INSTANCE.scheduleFeed(feed);
    }
    getSession().getTransaction().commit();
    
    initialized = true;
    
  }
  
  public boolean isFeedScheduled(Feed feed)
  {
    if (feed.getId() == null) {
      return false;
    } else {
      return scheduledFeedsIds.contains(feed.getId());
    }
  }
  
  public boolean scheduleFeed(Feed feed)
  {
    if (feed.getId() == null) {
      return false;
    } else if (scheduledFeedsIds.contains(feed.getId())) {
      return true;
    } else {
      Task task = new Task(feed);
      scheduler.scheduleAtFixedRate(task, 0L, task.period, task.unit);
      scheduledFeedsIds.add(feed.getId());
      return true;
    }
  }
}
