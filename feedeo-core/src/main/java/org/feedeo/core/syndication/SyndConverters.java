package org.feedeo.core.syndication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndImage;
import com.sun.syndication.feed.synd.SyndLink;
import com.sun.syndication.feed.synd.SyndPerson;

import org.feedeo.core.hibernate.Queries;
import org.feedeo.core.model.feed.Category;
import org.feedeo.core.model.feed.Content;
import org.feedeo.core.model.feed.Enclosure;
import org.feedeo.core.model.feed.ImageReference;
import org.feedeo.core.model.feed.Link;
import org.feedeo.core.model.feed.Writer;

/**
 * This utility class hosts some functions that are useful while
 * converting a Feed from ROME's SyndFeed format to Feedeo's model.
 * 
 * @author Feedeo Team
 *
 */
class SyndConverters {
  
  private SyndConverters() {
    // Prevent instantiation.
  }
  
  static Content convertContent(SyndContent syndContent) {
    if (syndContent == null) {
      return null;
    } else {
      String type = syndContent.getType();
      String mode = syndContent.getMode();
      String value = syndContent.getValue();
      return new Content(type, mode, value);
    }
    
  }

  static Enclosure convertEnclosure(SyndEnclosure syndEnclosure) {
    if (syndEnclosure == null) {
      return null;
    } else {
      String url = syndEnclosure.getUrl();
      long length = syndEnclosure.getLength();
      String type = syndEnclosure.getType();
      return new Enclosure(url, length, type);
    }
  }

  static ImageReference convertImage(SyndImage syndImage) {
    if (syndImage == null) {
      return null;
    } else {
      String title = syndImage.getTitle();
      String url = syndImage.getUrl();
      String link = syndImage.getLink();
      String description = syndImage.getDescription();
      return new ImageReference(title, url, link, description);
    }
  }

  static Link convertLink(SyndLink syndLink) {
    if (syndLink == null) {
      return null;
    } else {
      String rel = syndLink.getRel();
      String href = syndLink.getHref();
      String title = syndLink.getTitle();
      String type = syndLink.getType();
      String hreflang = syndLink.getHreflang();
      long length = syndLink.getLength();
      return new Link(rel, href, title, type, hreflang, length);
    }
  }

  @SuppressWarnings("unchecked")
  static <T> List<T> convertEmbeddedList(List<?> sourceList) {
    List<T> result = new ArrayList<T>();
    for (Object item : sourceList) {
      if (item instanceof SyndContent) {
        result.add((T) convertContent((SyndContent) item));
      } else if (item instanceof SyndEnclosure) {
        result.add((T) convertEnclosure((SyndEnclosure) item));
      } else if (item instanceof SyndImage) {
        result.add((T) convertImage((SyndImage) item));
      } else if (item instanceof SyndLink) {
        result.add((T) convertLink((SyndLink) item));
      }
    }
    return result;
  }

  private static void addPersons(List<?> syndPersons, Collection<Writer> destination) {
    for (Object item : syndPersons) {
      if (item instanceof SyndPerson) {
        SyndPerson person = (SyndPerson) item;
        String name = person.getName();
        String email = person.getEmail();
        String uri = person.getUri();
        destination.add(Queries.getOrSaveWriter(name, email, uri));
      }
    }
  }
  
  static List<Writer> convertAuthors(List<?> syndAuthors) {
    List<Writer> result = new ArrayList<Writer>();
    addPersons(syndAuthors, result);
    return result;
  }
  
  static Set<Writer> convertContributors(List<?> syndContributors) {
    Set<Writer> result = new HashSet<Writer>();
    addPersons(syndContributors, result);
    return result;
  }
  
  static Set<Category> convertCategories(List<?> syndCategories) {
    Set<Category> result = new HashSet<Category>();
    for (Object item : syndCategories) {
      if (item instanceof SyndCategory) {
        SyndCategory category = (SyndCategory) item;
        String name = category.getName();
        String taxonomyUri = category.getTaxonomyUri();
        result.add(Queries.getOrSaveCategory(name, taxonomyUri));
      }
    }
    return result;
  }
  
}
