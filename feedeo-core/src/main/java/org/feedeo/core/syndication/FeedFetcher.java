package org.feedeo.core.syndication;

import static org.feedeo.core.syndication.SyndConverters.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import org.feedeo.core.hibernate.Queries;
import org.feedeo.core.model.feed.Article;
import org.feedeo.core.model.feed.Content;
import org.feedeo.core.model.feed.Enclosure;
import org.feedeo.core.model.feed.Feed;
import org.feedeo.core.model.feed.Link;

/**
 * Type a description for FeedFetcher here.
 * 
 * NB: all these methods must be used with an open Hibernate Session to work, in
 * part because of the lazy fetching of collections.
 * 
 * @author Feedeo Team
 * 
 */
public class FeedFetcher {

  /**
   * This is a factory method that is associated to the feed asociated to a URL;
   * it uses the {@link Queries#getOrSaveFeedByUrl(String)} method.
   * 
   * @param url
   * @return a FeedFetcher that has this Feed as an argument.
   * @throws MalformedURLException
   *           if the URL is invalid according to {@link URL}
   */
  public static FeedFetcher getFetcher(String url) throws MalformedURLException
  {
    Feed feed = Queries.getOrSaveFeedByUrl(url);
    return new FeedFetcher(feed);
  }

  private final Feed feed;
  private boolean    feedChanged = false;

  private FeedFetcher(Feed feed) {
    this.feed = feed;
  }

  /**
   * @return the Feed updated by this fetcher.
   */
  public Feed getFeed() {
    return feed;
  }
  
  /**
   * This method updates the Feed associated to this FeedFetcher.
   * 
   * @throws FeedRetrievalException
   */
  public void update() throws FeedRetrievalException {
    SyndFeed source = retrieve();
    
    

    updateFeedFields(source);
    updateFeedArticles(source.getEntries());
    
    if (feedChanged) {
      feed.setLastChange(new Date(System.currentTimeMillis()));
    }
  }

  private SyndFeed retrieve() throws FeedRetrievalException {
    URL feedURL = null;
    try {
      feedURL = new URL(feed.getUrl());
    } catch (MalformedURLException e) {
      throw new RuntimeException(
          "URL for feed is invalid, though it should have been checked…", e);
    }
    SyndFeed result = null;
    try {
      SyndFeedInput feedInput = new SyndFeedInput();
      feedInput.setPreserveWireFeed(true);
      result = feedInput.build(new XmlReader(feedURL));
    } catch (Exception e) {
      throw new FeedRetrievalException("Feed cannot be retrieved by ROME.", e);
    }
    return result;
  }
  
  private void updateFeedFields(SyndFeed source) {
    feed.setFeedType(compAndGet(feed.getFeedType(),source.getFeedType()));
    feed.setEncoding(compAndGet(feed.getEncoding(),source.getEncoding()));
    feed.setUri(compAndGet(feed.getUri(),source.getUri()));
    
    feed.setTitle(compAndGet(feed.getTitle(), convertContent(source.getTitleEx())));
    
    feed.setLink(compAndGet(feed.getLink(), source.getLink()));
    List<Link> potentialLinks = convertEmbeddedList(source.getLinks());
    feed.setLinks(compAndGet(feed.getLinks(),potentialLinks));
    
    feed.setDescription(compAndGet(feed.getDescription(), convertContent(source
        .getDescriptionEx())));
    feed.setPubDate(compAndGet(feed.getPubDate(),source.getPublishedDate()));
    
    feed.setAuthors(compAndGet(feed.getAuthors(),convertAuthors(source.getAuthors())));
    feed.setContributors(compAndGet(feed.getContributors(),convertContributors(source.getContributors())));
    
    feed.setCopyright(compAndGet(feed.getCopyright(),source.getCopyright()));
    feed.setImage(compAndGet(feed.getImage(),convertImage(source.getImage())));
    
    feed.setCategories(compAndGet(feed.getCategories(),convertCategories(source.getCategories())));
    
    feed.setLanguage(compAndGet(feed.getLanguage(),source.getLanguage()));
    
    
  }
  
  private <T> T compAndGet(T saved, T source) {
    if (!feedChanged) {
      if (saved == null) {
        if (source != null) {
          feedChanged = true;
        }
      } else if (!(saved.equals(source))) {
        feedChanged = true;
      }
    }
    return source;
  }

  private void updateFeedArticles(List<?> entries) {
    for (Object item : entries) {
      if (item instanceof SyndEntry) {
        //TODO: What happens now?
        SyndEntry entry = (SyndEntry) item;
        ArticleUpdater updater = getUpdater(entry);
        updater.update(entry);
        if (!feedChanged && updater.hasChanged()) {
          feedChanged = true;
        }
      }
    }
  }
  
  private ArticleUpdater getUpdater(SyndEntry entry) {
    String uri = entry.getUri();
    Content title = convertContent(entry.getTitleEx());
    Date pubDate = entry.getPublishedDate();
    Article article = Queries.getOrSaveArticle(feed, uri, title, pubDate);
    return new ArticleUpdater(article);
  }
  
  private class ArticleUpdater {
    private final Article article;
    private boolean articleChanged = false;
    
    public ArticleUpdater(Article article) {
      this.article = article;
    }
    
    public void update(SyndEntry entry) {
      //TODO import parameters.
      article.setLink(compAndGet(article.getLink(),entry.getLink()));
      List<Link> potentialLinks = convertEmbeddedList(entry.getLinks());
      article.setLinks(compAndGet(article.getLinks(),potentialLinks));
      
      article.setDescription(compAndGet(article.getDescription(),convertContent(entry.getDescription())));
      List<Content> potentialContents = convertEmbeddedList(entry.getContents());
      article.setContents(compAndGet(article.getContents(),potentialContents));
      List<Enclosure> potentialEnclosures = convertEmbeddedList(entry.getEnclosures());
      article.setEnclosures(compAndGet(article.getEnclosures(),potentialEnclosures));
      
      article.setEntryUpdate(compAndGet(article.getEntryUpdate(),entry.getUpdatedDate()));
      
      article.setAuthors(compAndGet(article.getAuthors(),convertAuthors(entry.getAuthors())));
      article.setContributors(compAndGet(article.getContributors(),convertContributors(entry.getContributors())));
      
      article.setCategories(compAndGet(article.getCategories(),convertCategories(entry.getCategories())));
      
      
      if (articleChanged) {
        article.setLastChange(new Date(System.currentTimeMillis()));
      }
    }
    
    public boolean hasChanged() {
      return articleChanged;
    }
    
    private <T> T compAndGet(T saved, T source) {
      if (!articleChanged) {
        if (saved == null) {
          if (source != null) {
            articleChanged = true;
          }
        } else if (!(saved.equals(source))) {
          articleChanged = true;
        }
      }
      return source;
    }
  }

}
