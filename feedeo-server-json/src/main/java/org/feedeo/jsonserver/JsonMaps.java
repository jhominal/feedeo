package org.feedeo.jsonserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.feedeo.core.model.feed.Article;
import org.feedeo.core.model.user.ArticleProperties;
import org.feedeo.core.model.user.Folder;
import org.feedeo.core.model.user.User;

/**
 * Refactoring of the old Mappable interface so that the core is better insulated from the client communication problems.
 * 
 * @author jean
 *
 */
public class JsonMaps {
  public static Map<String, Object> of(Article article) {
    Map<String, Object> result = new HashMap<String, Object>();
    if (article.getId() != null) {
      result.put("id", article.getId().toString());
    }
    if (article.getTitle() != null && article.getTitle().getValue() != null) {
      result.put("title", article.getTitle().getValue());
    }
    if (!article.getAuthors().isEmpty()) {
      result.put("author", article.getAuthors().get(0).getName());
    }
    // Summary
    if (article.getDescription() != null) {
      result.put("summary", article.getDescription().getValue());
    } else if (!article.getContents().isEmpty()) {
      result.put("summary", article.getContents().get(0).getValue());
    }
    // Content
    if (!article.getContents().isEmpty()) {
      result.put("content", article.getContents().get(0).getValue());
    } else if (article.getDescription() != null) {
      result.put("content", article.getDescription().getValue());
    }
    if (article.getDisplayedPubDate() != null) {
      result.put("date", Long.valueOf(article.getDisplayedPubDate().getTime() / 1000L));
    }
    if (article.getLink() != null) {
      result.put("url", article.getLink());
    }
    return result;
  }
  
  public static Map<String, Object> of(User user, Article article) {
    Map<String, Object> result = JsonMaps.of(article);
    ArticleProperties currProperties = user.propertiesOf(article);
    result.put("read", Boolean.valueOf(currProperties.isAlreadyRead()));
    result.put("important", Boolean.valueOf(currProperties.isImportant()));
    return result;
  }
  
  public static Map<String, Object> of(Folder folder, boolean deep) {
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("id", folder.getId().toString());
    result.put("text", folder.getTitle());
    result.put("leaf", Boolean.FALSE);
    if (deep) {
      result.put("children", childrenOf(folder));
    }
    if (deep) {
      result.put("articles", articlesOf(folder));
    }
    return result;
  }
  
  public static List<Map<String, Object>> childrenOf(Folder folder) {
    List<Map<String, Object>> childrenMap = new ArrayList<Map<String, Object>>();
    for (Folder subFolder : folder.getSubFolders()) {
      childrenMap.add(JsonMaps.of(subFolder, false));
    }
    return childrenMap;
  }
  
  public static List<Map<String, Object>> articlesOf(Folder folder) {
    List<Map<String, Object>> articlesMap = new ArrayList<Map<String, Object>>();
    for (Article article : folder.getArticles()) {
      articlesMap.add(JsonMaps.of(folder.getOwner(), article));
    }
    return articlesMap;
  }
  



}
