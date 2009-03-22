package serveur;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
//import hibernate.*;

import org.stringtree.json.JSONReader;
import org.stringtree.json.JSONWriter;

public class FeedeoHandler {
	private String userName = null;
	private User user = null;
	//private ;
	public FeedeoHandler(String userName)
	{
		this.userName = userName;
		if(this.userName != null)
		{
			List<HibernateObject> rep=HibernateObject.listObject("select user from User as user where login="+ "'"+this.userName+"'");
			Iterator<HibernateObject> iter = rep.iterator(); 
			if(iter.hasNext())
			{
				this.user=(User)iter.next();
			}
		}
	};
	@SuppressWarnings("unchecked")
	public void handle( HashMap<String, Object> request, HashMap<String, Object> response)
	{
		//request peut contenir (en général) :
		//action = [delete,add,update,get,login,register,move...]
		//object = [article,user,folder,preferences,feed...]
		//et des params relatifs à l'action id, target, value...
		//on peut manipuler response, c'est ce qui sera envoyé au client

		String action = (String) request.get("action");
		String object = (String) request.get("object");
		if(object != null && action !=null)
		{			
			if(object.equals("folder"))
			{

				if(action.equals("getChildren"))
				{
					String folderIdString = (String) request.get("folderId");
					Integer folderId = Integer.parseInt(folderIdString,10);
					if(folderId!=null)
					{
						List<HibernateObject>rep=HibernateObject.listObject("select distinct dir from Directory as dir, User as user inner join dir.user as user where dir.idParent = "+folderId+" and user.idUser="+this.user.getIdUser());
						ArrayList<HashMap<String,Object> > children = new ArrayList<HashMap<String,Object>>();

						for (Iterator<HibernateObject> iter = rep.iterator(); iter.hasNext();) {
							HibernateObject obj=iter.next();
							if (obj instanceof Directory)
							{
								Directory dir= (Directory) obj;
								children.add(dir.toHashMap());
							}
						}
						response.put("children", children);
						response.put("success", true);

					}
				}
				else if(action.equals("getArticles"))
				{
					String folderIdString = (String) request.get("folderId");
					Integer folderId = Integer.parseInt(folderIdString,10);


					if(folderId!=null)
					{

						ArrayList<HashMap<String,Object> > articlesArray = new ArrayList<HashMap<String,Object>>();

						//Set<Article> articles= new HashSet<Article>();
						List<HibernateObject> resp=HibernateObject.listObject("select distinct article from Directory as dir inner join dir.listArticle as article where dir.idDirectory= "+folderId);
						for (Iterator<HibernateObject> iter = resp.iterator(); iter.hasNext();) {
							HibernateObject obj=iter.next();
							if (obj instanceof Article)
							{
								Article art= (Article) obj;
								//System.out.print(dir.getTitle());
								HashMap<String, Object> article=art.toHashMap();
								Articles_Properties artState= new Articles_Properties();

								List<HibernateObject> respo=HibernateObject.listObject("select artprop from Articles_Properties as artprop where artprop.idUserArticle.idArticle.idArticle= "+art.getIdArticle()+" and artprop.idUserArticle.idUser.idUser="+1);

								for (Iterator<HibernateObject> iterArt = respo.iterator(); iterArt.hasNext();) {
									HibernateObject stateObj=iterArt.next();
									if (stateObj instanceof Articles_Properties)
									{
										Articles_Properties articleState=(Articles_Properties)stateObj;
										article.put("read", articleState.getLu());
										article.put("important", articleState.getImportant());

									}

								}
								articlesArray.add(article);
							}

						}
						//	}

						response.put("success",true);
						response.put("articles", articlesArray);

					}
				}
				else if(action.equals("add"))
				{
					String dirName=(String) request.get("name");
					String id= (String) request.get("parentId");
					Integer idParent = Integer.parseInt(id,10);
					Directory dir= new Directory(dirName,idParent,this.user);
					dir.createDirectory();
					if (idParent>0)
					{	
						List<HibernateObject>rep=HibernateObject.listObject("select distinct dir from Directory as dir, User as user inner join dir.user as user where dir.idDirectory= "+idParent+" and user.idUser="+this.user.getIdUser());
						for (Iterator<HibernateObject> iter = rep.iterator(); iter.hasNext();) {
							HibernateObject obj=iter.next();
							if (obj instanceof Directory)
							{
								Directory dirParent= (Directory) obj;
								if (!dirParent.getHasChildren())
								{
									dirParent.setHasChildren(true);
									dirParent.updateDirectory();
								}
							}
						}
					}
					response.put("folder", dir.toHashMap());
					response.put("success", true);

				}
				else if (action.equals("addFeed"))
				{
					String folderIdString = (String) request.get("folderId");
					Integer folderId = Integer.parseInt(folderIdString,10);
					if(folderId!=null)
					{
						List<HibernateObject>rep=HibernateObject.listObject("select distinct dir from Directory as dir, User as user inner join dir.user as user where dir.idDirectory= "+folderId+" and user.idUser="+this.user.getIdUser());
						for (Iterator<HibernateObject> iter = rep.iterator(); iter.hasNext();) {
							HibernateObject obj=iter.next();
							if (obj instanceof Directory)
							{
								Directory dir= (Directory) obj;
								String feedUrl=(String)request.get("feedUrl");
								Feed feed=new Feed(feedUrl,dir,this.user);
								feed.createFeed();
								feed.setArticles(dir);
								dir.updateDirectory();
							}
						}
						response.put("success", true);
						/*Directory dir1_user1=new Directory("racine",user1);
						String url="http://fcargoet.evolix.net/feed/";
						Feed feed=new Feed(url,dir1_user1,user1);

						dir1_user1.createDirectory();
						feed.createFeed();
						feed.setArticles(feed.getFeedOrigine(),dir1_user1);
						dir1_user1.updateDirectory();*/
					}
					//feedUrl

				}


			}
			else if (object.equals("article"))
			{
				String folderIdString = (String) request.get("folderId");
				Integer folderId = Integer.parseInt(folderIdString,10);
				if(action.equals("get"))
				{

				}
				else if(action.equals("update"))
				{
					String articleIdString = (String) request.get("articleId");
					Integer articleId = Integer.parseInt(folderIdString,10);
					List<HibernateObject> resp=HibernateObject.listObject("select artprop from Articles_Properties as artprop where artprop.idUserArticle.idArticle.idArticle= "+articleId+" and artprop.idUserArticle.idUser.idUser="+1);

					for (Iterator<HibernateObject> iterArt = resp.iterator(); iterArt.hasNext();) {
						HibernateObject stateObj=iterArt.next();
						if (stateObj instanceof Articles_Properties)
						{
							Articles_Properties articleState=(Articles_Properties)stateObj;
							HashMap<String,Object> changes=(HashMap<String,Object> )request.get("changes");
							Boolean read = (Boolean) changes.get("read");
							Boolean important= (Boolean) changes.get("important");
							if(read != null)
							{
								articleState.setImportant(important);
							}
							if(read != null)
							{
								articleState.setLu(read);
							}
							//ON DEVRAIT GERER L'ECHEC DE TOUS LES CHANGEMENT D'ETAT
							response.put("success", true);
						}

					}
				}
			}
			else if(object.equals("preferences"))
			{
				if(action.equals("get"))
				{
					response.put("success",true);
					response.put("preferences", null);
				}
				else if(action.equals("..."))
				{

				}

			}else if(object.equals("feed"))
			{
				if(action.equals("update"))
				{
					response.put("success",true);
					response.put("preferences", null);
				}
			}		
		}

	}
	public String login(HashMap<String, Object> loginRequest)
	{
		//ckeck login/password
		String login = (String) loginRequest.get("login");
		String password = (String) loginRequest.get("password");
		List<HibernateObject> rep=HibernateObject.listObject("select user from User as user where login="+ "'"+login+"'");
		Iterator<HibernateObject> iter = rep.iterator(); 
		if(iter.hasNext())
		{
			HibernateObject obj=iter.next();
			if (obj instanceof User)
			{
				String pswd=((User) obj).getPassword();
				if (pswd.equals(password))
				{
					return login;
				}
			}
			return null;
		}
		else
		{
			return null;
		}


	};
	public String createAccount(HashMap<String, Object> createAccountRequest)
	{
		//create account
		String login = (String) createAccountRequest.get("login");
		String password = (String) createAccountRequest.get("password");
		String name= (String) createAccountRequest.get("name");
		String lastName= (String) createAccountRequest.get("lastName");
		String email= (String) createAccountRequest.get("email");
		List<HibernateObject> rep=HibernateObject.listObject("select user from User as user where login="+ "'"+login+"'");
		Iterator<HibernateObject> iter = rep.iterator(); 
		if(iter.hasNext())
		{
			return null;
		}
		else
		{
			User user=new User(name,lastName,login,password,email);
			user.createUser();
			return user.getLogin();
		}
		/*for (Iterator<HibernateObject> iter = rep.iterator(); iter.hasNext();) {
			HibernateObject obj=iter.next();

			if (obj instanceof User)
			{
				System.out.print(((User) obj).getLogin() +"\n");
			}
		 */
		//and other data...


	};
}
