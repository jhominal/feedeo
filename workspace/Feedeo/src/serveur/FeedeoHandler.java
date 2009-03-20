package serveur;

import java.util.HashMap;

public class FeedeoHandler {

	public static void handle(HashMap request, HashMap response){
		//request peut contenir (en général) :
		//action = [delete,add,update,get,login,register,move...]
		//object = [article,user,folder,preferences,feed...]
		//et des params relatifs à l'action id, target, value...
		response.put("success", true);
		response.put("msg", "eurydice !");
	}
}
