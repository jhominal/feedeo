package org.feedeo.jsonserver;

import static org.feedeo.core.hibernate.InitSessionFactory.getSession;

import java.util.Map;

import org.hibernate.HibernateException;

import org.feedeo.core.hibernate.Queries;
import org.feedeo.core.model.feed.Article;
import org.feedeo.core.model.user.ArticleProperties;
import org.feedeo.core.model.user.Folder;
import org.feedeo.core.model.user.User;
import org.feedeo.core.syndication.FeedFetcher;

/**
 * This class handles the JSON requests built by the index.jsp page, and gives
 * appropriate replies in return.
 * 
 * @author Feedeo Team
 * 
 */
public class FeedeoHandler {
  private User user;

  private enum TargetObject {
    folder, article, preferences, feed
  }

  /**
   * Builds an instance of FeedeoHandler for the target client.
   * 
   * @param login
   */
  public FeedeoHandler(String login) {
    getSession().beginTransaction();
    user = Queries.getUserByLogin(login);
    getSession().getTransaction().commit();
  }

  /**
   * The "basic" handler method: takes a JSON request object, and makes a JSON
   * response object.
   * 
   * @param request
   * @param response
   */
  public void handle(Map<String, Object> request, Map<String, Object> response)
  {
    //TODO: redo this mammoth method
    // request may contain (in general):
    // action = [delete, add, update, get, login, register, move...]
    // object = [article, user, folder, preferences, feed...]
    // and parameters relative to the action: id, target, value...
    // the response object is the one that will be sent to the client.
    String action = (String) request.get("action");
    String object = (String) request.get("object");
    if ("login".equals(action)) {
      response.put("error", "Already logged");
    } else {
      try {
        getSession().beginTransaction();
        user = (User) getSession().merge(user);
        TargetObject target = TargetObject.valueOf(object);
        switch (target) {
          case folder:
            String folderIdString = (String) request.get("folderId");
            if (folderIdString != null) {
              Folder targetDirectory;
              long folderId = Long.parseLong(folderIdString);
              if (folderId == 0L) {
                targetDirectory = user.getRootDirectory();
              } else {
                targetDirectory = (Folder) getSession().get(Folder.class,
                    Long.valueOf(folderId));
              }
              if (targetDirectory != null) {
                if ("getChildren".equals(action)) {
                  response.put("children", JsonMaps.childrenOf(targetDirectory));
                  response.put("success", Boolean.TRUE);
                } else if ("getArticles".equals(action)) {
                  Queries.updateArticles(targetDirectory);
                  response.put("articles", JsonMaps.articlesOf(targetDirectory));
                  response.put("success", Boolean.TRUE);
                } else if ("addFeed".equals(action)) {
                  String feedUrl = (String) request.get("feedUrl");
                  FeedFetcher fetcher = FeedFetcher.getFetcher(feedUrl);
                  fetcher.update();
                  targetDirectory.subscribeFeed(fetcher.getFeed());
                  response.put("success", Boolean.TRUE);
                } else if ("delete".equals(action)) {
                  getSession().delete(targetDirectory);
                  response.put("success", Boolean.TRUE);
                }
              }
            } else if ("add".equals(action)) {
              Folder newDirectory = new Folder();
              newDirectory.setTitle((String) request.get("name"));
              folderIdString = (String) request.get("parentId");
              if (folderIdString != null) {
                long parentId = Long.parseLong(folderIdString);
                Folder parentDirectory;
                if (parentId == 0L) {
                  parentDirectory = user.getRootDirectory();
                } else {
                  parentDirectory = (Folder) getSession().get(
                      Folder.class, Long.valueOf(parentId));
                }
                if (parentDirectory != null) {
                  parentDirectory.attachFolder(newDirectory);
                  getSession().persist(newDirectory);
                  response.put("folder", JsonMaps.of(newDirectory, false));
                  response.put("success", Boolean.TRUE);

                }
              }
            }
            break;

          case article:
            String articleIdString = (String) request.get("articleId");
            if (articleIdString != null) {
              long articleId = Long.parseLong(articleIdString);
              Article targetArticle = (Article) getSession().get(Article.class,
                  Long.valueOf(articleId));
              if ("update".equals(action)) {
                ArticleProperties properties = user
                    .propertiesOf(targetArticle);
                // Suppress warning because the Feedeo client/server (implicit) API specifies that.
                @SuppressWarnings("unchecked")
                Map<String, Object> changes = (Map<String, Object>) request
                    .get("changes");
                Boolean read = (Boolean) changes.get("read");
                Boolean important = (Boolean) changes.get("important");
                if (important != null) {
                  properties.setImportant(important.booleanValue());
                }
                if (read != null) {
                  properties.setAlreadyRead(read.booleanValue());
                }
                response.put("success", Boolean.TRUE);
              }
            }
            break;
          case preferences:
            if ("get".equals(action)) {
              response.put("success", Boolean.TRUE);
              response.put("preferences", null);
              response.put("note", "not really implemented");
            }
            break;
          case feed:
            if ("update".equals(action)) {
              response.put("success", Boolean.TRUE);
              response.put("preferences", null);
              response.put("note", "not really implemented");
            }
            break;
        }
        getSession().getTransaction().commit();
      } catch (Exception e) {
        JsonExceptionPrinter ePrinter = new JsonExceptionPrinter(e);

        // Trying to roll back the transaction if active.
        if (getSession().getTransaction() != null
            && getSession().getTransaction().isActive())
        {
          try {
            // Second try catch as the rollback could fail as well
            getSession().getTransaction().rollback();
            ePrinter.setTransactionRollback("successful");
          } catch (HibernateException e1) {
            ePrinter.setTransactionRollback("failed");
          }
        } else {
          ePrinter.setTransactionRollback("unnecessary");
        }

        response.put("success", Boolean.FALSE);
        response.put("error", e.getMessage());
        response.put("JavaProblem", ePrinter.toMap(true));
      }
    }
  }

  /**
   * 
   * @param loginRequest
   * @return the login if login is successful.
   */
  public static String login(Map<String, Object> loginRequest) {
    String login = (String) loginRequest.get("login");
    String password = (String) loginRequest.get("password");
    String result;
    getSession().beginTransaction();
    User possibleUser = Queries.getUserByLogin(login);
    if (possibleUser.checkPassword(password)) {
      result = login;
    } else {
      result = null;
    }
    getSession().getTransaction().commit();
    return result;
  }

  /**
   * Creates a new account, if not already in the database.
   * 
   * @param newAccountReq
   *          a Map specifying "login", "password", "name", "lastName", "mail"
   * @return the created-user's login
   */
  public static String createAccount(Map<String, Object> newAccountReq) {
    String login = (String) newAccountReq.get("login");
    String result;
    getSession().beginTransaction();
    User possibleUser = Queries.getUserByLogin(login);
    if (possibleUser.getId() == null) {
      possibleUser.setPassword((String) newAccountReq.get("password"));
      possibleUser.setFirstName((String) newAccountReq.get("name"));
      possibleUser.setLastName((String) newAccountReq.get("lastname"));
      possibleUser.setEmail((String) newAccountReq.get("mail"));
      result = login;
    } else {
      result = null;
    }
    getSession().getTransaction().commit();
    return result;
  }
}
