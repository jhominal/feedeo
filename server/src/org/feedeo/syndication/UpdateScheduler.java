package org.feedeo.syndication;

import static org.feedeo.hibernate.InitSessionFactory.getSession;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.feedeo.Feed;

/**
 * Provides a class to schedule updates, with most interactions being a trace
 * over a number of the standard methods of the java.util.Map interface.
 * 
 * The implementation for this Scheduler combines: -A Map<Feed,UpdateTask> to
 * keep a reference to all currently run tasks -A Timer that effectively runs
 * the
 * 
 * @author Feedeo Team
 * 
 */
public enum UpdateScheduler {

	/**
	 * This is the UpdateScheduler instance.
	 */
	instance;

	private int threadPoolSize = 1;
	private ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(threadPoolSize);
	private Map<Feed, UpdateTask> taskMap = Collections
			.synchronizedMap(new HashMap<Feed, UpdateTask>());
	private boolean initialized = false;

	/**
	 * Gets the active UpdateScheduler instance.
	 * 
	 * @return the UpdateScheduler instance in use.
	 */
	public static UpdateScheduler getInstance() {
		if (!instance.initialized) {
			instance.init();
		}
		return instance;
	}

	private UpdateScheduler() {
	}

	void init() {
		@SuppressWarnings("unchecked")
		List<Feed> feeds = getSession().createCriteria(Feed.class).list();
		for (Feed feed : feeds) {
			add(feed);
		}
		initialized = true;
	}

	/**
	 * This functions adds a Feed to the Scheduler. However, if the Feed is
	 * already in the Scheduler, then it will not be replaced by a new generated
	 * UpdateTask.
	 * 
	 * @param feed
	 * @return true if the adding was successful, false otherwise
	 */
	public boolean add(Feed feed) {
		if (!containsKey(feed)) {
			UpdateTask task = new UpdateTask(feed);
			this.put(feed, task);
			return true;
		}
		return false;
	}

	/**
	 * This functions stops the UpdateScheduler instance by: -cancelling all
	 * remaining tasks (scheduler.shutdown()) -clearing the underlying Map
	 * (map.clear()) -marking that an initialization is necessary the next time
	 * that getInstance() will be called.
	 */
	public void stop() {
		initialized = false;
		scheduler.shutdown();
		taskMap.clear();
	}

	/**
	 * @param feed
	 * @return a boolean indicating whether the given Feed is in this Scheduler.
	 */
	public boolean contains(Feed feed) {
		return containsKey(feed);
	}

	/**
	 * @see java.util.Map#containsKey(Object)
	 * 
	 * @param key
	 * @return true if key is present as a key in the underlying map
	 */
	public boolean containsKey(Object key) {
		return taskMap.containsKey(key);
	}

	/**
	 * @see java.util.Map#containsValue(Object)
	 * 
	 * @param value
	 * @return true if the value is present as a value in the underlying map
	 */
	public boolean containsValue(Object value) {
		return taskMap.containsValue(value);
	}

	/**
	 * @see java.util.Map#get(Object)
	 * 
	 * @param key
	 * @return the UpdateTask associated to this key
	 */
	public UpdateTask get(Object key) {
		return taskMap.get(key);
	}

	/**
	 * @return true if the Scheduler has been initialized
	 */
	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * @return true if no task was scheduled
	 */
	public boolean isEmpty() {
		return taskMap.isEmpty();
	}

	/**
	 * @see java.util.Map#put(Object, Object)
	 * 
	 * @param feed
	 * @param task
	 * @return the UpdateTask that has just been added
	 */
	public UpdateTask put(Feed feed, UpdateTask task) {
		if (this.containsKey(feed)) {
			this.remove(feed);
		}
		taskMap.put(feed, task);
		scheduler.scheduleAtFixedRate(task, 0, task.period, task.unit);
		return task;
	}

	/**
	 * @see java.util.Map#remove(Object)
	 * 
	 * @param key
	 * @return the UpdateTask that has just been removed
	 */
	public UpdateTask remove(Object key) {
		UpdateTask task = taskMap.remove(key);
		if (task != null) {
			task.cancel();
		}
		return task;
	}

	/**
	 * @see java.util.Map#size()
	 * 
	 * @return the size of the underlying HashMap, which is also the number of
	 *         scheduled feeds.
	 */
	public int size() {
		return taskMap.size();
	}
}
