package serveur;

import java.util.HashMap;
import java.util.ArrayList;

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
				
			
			}
			else if (object.equals("article"))
			{
				
			}
			else if(object.equals("preferences"))
			{
				
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
