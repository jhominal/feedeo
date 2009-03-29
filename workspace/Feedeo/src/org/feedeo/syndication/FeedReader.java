package org.feedeo.syndication;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.feedeo.Article;
import org.feedeo.Feed;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * We felt that this class would be needed in order to build Feeds while still
 * separating the code that relies on the ROME library from code that does not.
 * 
 * @author Feedeo Team
 * 
 */

public class FeedReader {
	/**
	 * This method takes the feed's URL, and translates it to a Feedeo feed
	 * containing all the information contained in the XML file (usually, only
	 * the last ten feeds).
	 * 
	 * In reality, this method creates a new Feed with the given url as a property
	 * and calls this class's update method on it.
	 * 
	 * @param url
	 *            the XML feed's url.
	 * @return a Feed object containing all and only the informations contained
	 *         in the XML source.
	 */
	public static Feed checkout(String url) {
		Feed result = new Feed();
		result.setUrl(url);
		update(result);
		return result;
	}

	/**
	 * This method updates a feed with the latest available syndication data.
	 * 
	 * @param feed
	 * @return returns whether this update has changed anything in this feed.
	 */
	public static boolean update(Feed feed) {
		URL feedURL = null;
		try {
			feedURL = new URL(feed.getUrl());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		SyndFeed syndFeed = null;
		try {
			syndFeed = (new SyndFeedInput()).build(new XmlReader(feedURL));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FeedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if ((syndFeed.getPublishedDate() == null) || feed.getPubDate() == null
				|| syndFeed.getPublishedDate().after(feed.getPubDate())) {
			return putSyndInFeed(syndFeed, feed);
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	private static boolean putSyndInFeed(SyndFeed syndFeed, Feed targetFeed) {
		boolean result = false;
		targetFeed.setTitle(syndFeed.getTitle());
		targetFeed.setLink(syndFeed.getLink());
		targetFeed.setPubDate(syndFeed.getPublishedDate());
		targetFeed.setDescription(syndFeed.getDescription());

		Iterator<SyndEntry> entryIter = syndFeed.getEntries().iterator();

		while (entryIter.hasNext()) {
			SyndEntry currEntry = entryIter.next();
			Article potentialEntry = readArticle(currEntry);
			if (!targetFeed.getArticles().contains(potentialEntry)) {
				if (!potentialEntry.isReliablePubDate()) {
					Calendar calendar = GregorianCalendar.getInstance();
					potentialEntry.setPubDate(calendar.getTime());
				}
				targetFeed.addArticle(potentialEntry);
				result = true;
			} else {
			//TODO case where the article in question has been updated.
			}
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	private static Article readArticle(SyndEntry syndEntry) {
		Article result = new Article();

		result.setTitle(syndEntry.getTitle());
		result.setLink(syndEntry.getLink());
		result.setReliablePubDate(syndEntry.getPublishedDate() != null);
		result.setPubDate(syndEntry.getPublishedDate());
		result.setSummary(syndEntry.getDescription().getValue());
		result.setAuthor(syndEntry.getAuthor());

		String potentialContent = getMainTextLikeContent(syndEntry
				.getContents());
		if (potentialContent == null || potentialContent.equals(""))
			potentialContent = syndEntry.getDescription().getValue();
		result.setContent(potentialContent);

		return result;
	}

	private static String getMainTextLikeContent(List<SyndContent> contents) {
		if (contents.size() <= 0) {
			return null;
		} else {
			return contents.get(0).getValue();
		}
	}
}
