package serveur;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
						JSONReader jsonReader = new JSONReader();
						String articlesArrayJSON = "[{\"author\":\"Florian\",\"title\":\"Youpi\",\"content\":\"balblabal llazllbal yip yop yup titi tot tata\",\"url\":\"http://fcargoet.evolix.net/2009/03/une-liste-alimentee-automatiquement-avec-jquery/\",\"date\":\"2009-03-12\",\"categories\" : [\"info\",\"jquery\"],\"summary\" : \"balblabal llazllbal\",\"read\" : false,\"important\":false}]";
						ArrayList<HashMap> articlesArray =  (ArrayList<HashMap>) jsonReader.read(articlesArrayJSON);
						response.put("articles",articlesArray);
						response.put("success", true);
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
				else if(action.equals("..."))
				{
					
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
