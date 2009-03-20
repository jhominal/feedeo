package serveur;

import java.util.HashMap;

public class FeedeoHandler {
	private String userName = null;
	//private ;
	public FeedeoHandler(String userName)
	{
		this.userName = userName;
	};
	public void handle( HashMap request, HashMap response)
	{
		//request peut contenir (en général) :
		//action = [delete,add,update,get,login,register,move...]
		//object = [article,user,folder,preferences,feed...]
		//et des params relatifs à l'action id, target, value...
		
		String action = (String) request.get("action");
		String object = (String) request.get("object");
		
		
	};
	public String login(HashMap loginRequest)
	{
		//ckeck login/password
		String login = (String) loginRequest.get("login");
		String password = (String) loginRequest.get("password");
			
		return login;
		
	};
	public String createAccount(HashMap createAccountRequest)
	{
		//create account
		String login = (String) createAccountRequest.get("login");
		String password = (String) createAccountRequest.get("password");
		//and other data...
		
		
		return login;

	}
}
