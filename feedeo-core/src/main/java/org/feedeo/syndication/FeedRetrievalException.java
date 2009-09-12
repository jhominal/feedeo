package org.feedeo.syndication;

/**
 * <p>
 * This exception exists to model an exception that has happened
 * during the Feed's fetching by ROME.
 * </p>
 * <p>
 * That means that the best that can be done, is:
 * <ul>
 * <li>To retry, if it does not take too long.</li>
 * <li>To reschedule the update for later.</li>
 * <li>If that is the first time that a feed is checked, then, delete it.</li>
 * </ul>
 * </p>
 * @author Feedeo Team
 *
 */
public class FeedRetrievalException extends Exception {

  /**
   * Default Constructor.
   */
  public FeedRetrievalException() {
    super();
  }

  /**
   * @param message
   * @param cause
   */
  public FeedRetrievalException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @param message
   */
  public FeedRetrievalException(String message) {
    super(message);
  }

  /**
   * @param cause
   */
  public FeedRetrievalException(Throwable cause) {
    super(cause);
  }

  private static final long serialVersionUID = -8891307244455899251L;

}
