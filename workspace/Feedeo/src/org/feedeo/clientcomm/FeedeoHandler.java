package org.feedeo.clientcomm;

import java.util.HashMap;
import java.util.Map;

import org.feedeo.Article;
import org.feedeo.ArticleProperties;
import org.feedeo.Directory;
import org.feedeo.Feed;
import org.feedeo.User;
import org.feedeo.syndication.FeedReader;
import org.hibernate.Session;

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

	private Session getSession() {
		return user.getSession();
	}

	/**
	 * The "basic" handler method: takes a JSON request object, and makes a JSON
	 * response object.
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	public void handle(Map<String, Object> request, Map<String, Object> response) {
		// request may contain (in general):
		// action = [delete, add, update, get, login, register, move...]
		// object = [article, user, folder, preferences, feed...]
		// and parameters relative to the action: id, target, value...
		// the response object is the one that will be sent to the client.
		String action = (String) request.get("action");
		String object = (String) request.get("object");
		try {
			TargetObject target = TargetObject.valueOf(object);
			switch (target) {
			case folder:
				String folderIdString = (String) request.get("folderId");
				if (folderIdString != null) {
					try {
						long folderId = Long.parseLong(folderIdString);
						getSession().beginTransaction();
						Directory targetDirectory = (Directory) getSession()
								.get(Directory.class, folderId);
						if (targetDirectory != null) {
							if ("getChildren".equals(action)) {
								response.put("children", (targetDirectory
										.toMap(true)).get("children"));
								response.put("success", true);
							} else if ("getArticles".equals(action)) {
								response.put("articles", (targetDirectory
										.toMap(true)).get("articles"));
								response.put("success", true);
							} else if ("addFeed".equals(action)) {
								String feedUrl = (String) request
										.get("feedUrl");
								Feed targetFeed = FeedReader.checkout(feedUrl);
								targetFeed.saveOrUpdate();
								user.subscribeFeed(targetFeed, targetDirectory);
								targetFeed.putArticles(targetDirectory);
								user.saveOrUpdate();
								response.put("success", true);
							}
						}
						getSession().getTransaction().commit();
					} catch (NumberFormatException e) {
					}
				} else if ("add".equals(action)) {
					Directory newDirectory = new Directory();
					newDirectory.setTitle((String) request.get("name"));
					folderIdString = (String) request.get("parentId");
					if (folderIdString != null) {
						long parentId = Long.parseLong(folderIdString);
						getSession().beginTransaction();
						getSession().getTransaction().commit();
						Directory parentDirectory = (Directory) getSession()
								.get(Directory.class, parentId);
						if (parentDirectory != null) {
							parentDirectory.attachDirectory(newDirectory);
						} else {
							user.attachDirectory(newDirectory);
						}
						getSession().getTransaction().commit();
						response.put("folder", newDirectory.toMap(false));
						response.put("success", true);
					}
				}

				break;
			case article:
				String folderIdString2 = (String) request.get("folderId");
				String articleIdString = (String) request.get("articleId");
				if (folderIdString2 != null && articleIdString != null) {
					long folderId = Long.parseLong(folderIdString2);
					getSession().beginTransaction();
					Directory targetDirectory = (Directory) getSession().get(
							Directory.class, folderId);
					long articleId = Long.parseLong(articleIdString);
					Article targetArticle = (Article) getSession().get(
							Article.class, articleId);
					if("update".equals(action)) {
						ArticleProperties properties = user.getArticleProperties(targetArticle);
						HashMap<String,Object> changes=(HashMap<String,Object>) request.get("changes");
						Boolean read = (Boolean) changes.get("read");
						Boolean important= (Boolean) changes.get("important");
						if(read != null)
						{
							properties.setImportant(important);
						}
						if(read != null)
						{
							properties.setAlreadyRead(read);
						}
						//"On devrait gerer l'echec de tous les changements d'etat" kezako ?
						response.put("success", true);
					}
					getSession().getTransaction().commit();
				}
				break;
			case preferences:
				if ("get".equals(action)) {
					response.put("success", true);
					response.put("preferences", null);
				}
				break;
			case feed:
				if ("update".equals(action)) {
					response.put("success", true);
					response.put("preferences", null);
				}
				break;
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param loginRequest
	 * @return the login if login is successful.
	 */
	public String login(HashMap<String, Object> loginRequest) {
		String login = (String) loginRequest.get("login");
		String password = (String) loginRequest.get("password");
		User possibleUser = User.getUserByLogin(login);
		if (password != null && password.equals(possibleUser.getPassword())) {
			return login;
		} else {
			return null;
		}
	}
	
	public String createAccount(HashMap<String, Object> newAccountReq) {
		String login = (String) newAccountReq.get("login");
		User possibleUser = User.getUserByLogin(login);
		if (possibleUser.getPassword() == null) {
			possibleUser.setPassword((String) newAccountReq.get("password"));
			possibleUser.setFirstName((String) newAccountReq.get("name"));
			possibleUser.setLastName((String) newAccountReq.get("lastName"));
			possibleUser.setEmail((String) newAccountReq.get("email"));
			possibleUser.saveOrUpdate();
			return login;
		} else {
			return null;
		}
	}
	
}
