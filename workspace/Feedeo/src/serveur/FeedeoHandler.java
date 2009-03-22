package serveur;

import java.util.HashMap;
import java.util.ArrayList;

import org.stringtree.json.JSONReader;

public class FeedeoHandler {
	private String userName = null;
	//private ;
	public FeedeoHandler(String userName)
	{
		this.userName = userName;
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
					String folderId = (String) request.get("folderId");
					if(folderId!=null)
					{
					
						//debug tree
						// [{id:"2",text:"dossier 1",children:[node2]}]
						
						ArrayList<HashMap<String,Object> > children = new ArrayList<HashMap<String,Object>>();
						HashMap<String, Object> node1 = new HashMap<String, Object>();
						HashMap<String, Object> node2 = new HashMap<String, Object>();
						node1.put("id", "2");
						node1.put("text","Dossier 1");
						node2.put("id", "4");
						node2.put("text","Leaf 1");
						node2.put("leaf", true);
						ArrayList<HashMap<String,Object> > node1_children = new ArrayList<HashMap<String,Object>>();
						node1_children.add(node2);
						node1.put("children",node1_children);
						children.add(node1);
						
						response.put("children", children);
						response.put("success", true);
					}
				}
				else if(action.equals("getArticles"))
				{
					String folderId = (String) request.get("folderId");
					if(folderId!=null)
					{
						JSONReader jsonReader = new JSONReader();
						String articlesArrayJSON = "[{\"author\":\"Florian\",\"title\":\"Youpi\",\"content\":\"balblabal llazllbal yip yop yup titi tot tata\",\"url\":\"http://fcargoet.evolix.net/2009/03/une-liste-alimentee-automatiquement-avec-jquery/\",\"date\":\"2009-03-12\",\"categories\" : [\"info\",\"jquery\"],\"summary\" : \"balblabal llazllbal\",\"read\" : false,\"important\":false}]";
						ArrayList<HashMap> articlesArray =  (ArrayList<HashMap>) jsonReader.read(articlesArrayJSON);
						response.put("articles",articlesArray);
						response.put("success", true);
					}
				}
				else if(action.equals("..."))
				{
					
				}
			
			}
			else if (object.equals("article"))
			{
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
				
			}else if(object.equals("..."))
			{
				
			}		
		}
		
	};
	public String login(HashMap<String, Object> loginRequest)
	{
		//ckeck login/password
		String login = (String) loginRequest.get("login");
		String password = (String) loginRequest.get("password");
			
		return login;
		
	};
	public String createAccount(HashMap<String, Object> createAccountRequest)
	{
		//create account
		String login = (String) createAccountRequest.get("login");
		String password = (String) createAccountRequest.get("password");
		//and other data...
		
		
		return login;

	};
}
