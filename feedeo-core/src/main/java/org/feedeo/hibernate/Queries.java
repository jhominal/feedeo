package org.feedeo.hibernate;

import static org.feedeo.hibernate.InitSessionFactory.getSession;
import static org.hibernate.criterion.Restrictions.eq;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.feedeo.model.feed.Article;
import org.feedeo.model.feed.Category;
import org.feedeo.model.feed.Content;
import org.feedeo.model.feed.Feed;
import org.feedeo.model.feed.Writer;

/**
 * Regroups a number of queries required for the program to function.
 * 
 * All of these methods must be used with an open transaction.
 * 
 * @author Feedeo Team
 * 
 */
public class Queries {

  private Queries() {
    // Empty constructor to prevent instantiation.
  }

  /**
   * Gets a feed by its url.
   * 
   * @param url
   *          the feed's url.
   * @return a Feed with the corresponding url; if he did not exist in the base,
   *         all fields except login will be empty.
   * @throws MalformedURLException
   */
  public static Feed getOrSaveFeedByUrl(String url)
      throws MalformedURLException
  {
    // Check that the URL is valid before saving it to the database.
    new URL(url);
    Feed result = (Feed) getSession().createCriteria(Feed.class).add(
        eq("url", url)).setMaxResults(1).uniqueResult();
    if (result == null) {
      result = new Feed(url);
      getSession().saveOrUpdate(result);
    }
    return result;
  }

  /**
   * @param name
   * @param email
   * @param uri
   * @return a Writer with the corresponding characteristics.
   */
  public static Writer getOrSaveWriter(String name, String email, String uri) {
    Writer result = (Writer) getSession().createCriteria(Writer.class).add(
        eq("name", name)).add(eq("email", email)).add(eq("uri", uri))
        .setMaxResults(1).uniqueResult();
    if (result == null) {
      result = new Writer(name, email, uri);
      getSession().saveOrUpdate(result);
    }
    return result;
  }

  /**
   * @param name
   * @param taxonomyUri
   * @return a Category corresponding to these parameters
   */
  public static Category getOrSaveCategory(String name, String taxonomyUri) {
    Category result = (Category) getSession().createCriteria(Category.class)
        .add(eq("name", name)).add(eq("taxonomyUri", taxonomyUri))
        .setMaxResults(1).uniqueResult();
    if (result == null) {
      result = new Category(name, taxonomyUri);
      getSession().saveOrUpdate(result);
    }
    return result;
  }

  /**
   * @param source
   * @param uri
   * @param title
   * @param pubDate
   * @return an article with the corresponding parameters
   */
  public static Article getOrSaveArticle(Feed source, String uri,
      Content title, Date pubDate)
  {
    Article result = (Article) getSession().createCriteria(Article.class).add(
        eq("source", source)).add(eq("uri", uri)).add(
        eq("entryPubDate", pubDate)).add(eq("title", title)).setMaxResults(1)
        .uniqueResult();
    if (result == null) {
      result = new Article(source, uri, title, pubDate);
      getSession().saveOrUpdate(result);
    }
    return result;
  }
}
