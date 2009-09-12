package org.feedeo.clientcomm;

import static org.feedeo.hibernate.InitSessionFactory.getSession;

import java.util.Map;

import org.hibernate.HibernateException;

import org.feedeo.model.feed.Article;
import org.feedeo.model.user.ArticleProperties;
import org.feedeo.model.user.Folder;
import org.feedeo.model.user.User;
import org.feedeo.syndication.FeedFetcher;

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
    user = User.getUserByLogin(login);
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
                  response.put("children", targetDirectory.getChildrenMap());
                  response.put("success", Boolean.TRUE);
                } else if ("getArticles".equals(action)) {
                  targetDirectory.updateArticles();
                  response.put("articles", targetDirectory.getArticlesMap(targetDirectory.getArticles()));
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
                  response.put("folder", newDirectory.toMap(false));
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
                    .getArticleProperties(targetArticle);
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
                // "On devrait gerer l'echec de tous les changements d'etat"
                // TODO: kezako ?
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
    User possibleUser = User.getUserByLogin(login);
    if (password != null && password.equals(possibleUser.getPassword())) {
      return login;
    } else {
      return null;
    }
  }

  /**
   * Creates a new account, if not already in the database.
   * 
   * @param newAccountReq
   *          a Map specifying "login", "password", "name", "lastName", "email"
   * @return the created-user's login
   */
  public static String createAccount(Map<String, Object> newAccountReq) {
    String login = (String) newAccountReq.get("login");
    User possibleUser = User.getUserByLogin(login);
    if (possibleUser.getPassword() == null) {
      possibleUser.setPassword((String) newAccountReq.get("password"));
      possibleUser.setFirstName((String) newAccountReq.get("name"));
      possibleUser.setLastName((String) newAccountReq.get("lastname"));
      possibleUser.setEmail((String) newAccountReq.get("mail"));
      getSession().beginTransaction();
      getSession().saveOrUpdate(possibleUser);
      getSession().getTransaction().commit();
      return login;
    } else {
      return null;
    }
  }
}
