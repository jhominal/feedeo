package serveur;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Main {

	
	public Main()
	{
		
	}
	

	public static void main(String[] arg){
		//String[] url=new String[1];
		//url[0]="http://fcargoet.evolix.net/feed/";
		//TEST USER REUSSI 
				
				 User user1 = new User("meriam","sekkat","mimi","tintin20","merian@yahoo.fr");
				User user2 = new User("ghita","sekkat","tita","tite200","merian@yahoo.fr");
				
				//In a first time, two preferences: color and police size
		
				 Preference color= new Preference("color");
			 	 Preference police=new Preference ("police");
			 	 
			 	 UserPreferencePK pk=new UserPreferencePK(user1, color);
			 	 UserPreference up= new UserPreference(pk,"vert");
			 	 color.createPreference();
			 	 police.createPreference();
				 user1.createUser();
				 user2.createUser();
				 up.createUserPreference();
				 
		//FIN TEST USER REUSSI
		 
		// REUSSI :LECTURE D'UN FLUX CREATION D'ARTICLE DANS UN DOSSIER
		
		Directory dir1_user1=new Directory("racine",user1);
		String url="http://fcargoet.evolix.net/feed/";
		Feed feed=new Feed(url,dir1_user1,user1);
		
		dir1_user1.createDirectory();
		feed.createFeed();
		feed.setArticles(feed.getFeedOrigine(),dir1_user1);
		dir1_user1.updateDirectory();
		//FIN LECTURE D'UN FLUX
		
		//RECUPERER LES SOUS DOSSIER D'UN DOSSIER DONNE DE L'UTILISATEUR
		List<HibernateObject>rep=HibernateObject.listObject("select dir from Directory as dir, User as user inner join dir.user as user where dir.idParent is null");
		for (Iterator<HibernateObject> iter = rep.iterator(); iter.hasNext();) {
			HibernateObject obj=iter.next();
			if (obj instanceof Directory)
			{
			//Directory dir= (Directory) obj;
			System.out.print(((Directory) obj).getTitle() +"\n");
			//((Directory) obj).setTitle("hello");
			//((Directory) obj).updateDirectory();
			//logger.debug("{}", element);
			}
			
		//FIN RECUPERER LES SOUS DOSSIER D'UN DOSSIER DONNE DE L'UTILISATEUR
		
		String login="mimo";
		FeedeoHandler f=new FeedeoHandler(null);
		HashMap<String, Object> createAccountRequest = new HashMap<String, Object>();
		createAccountRequest.put("login",login);
		createAccountRequest.put("password", "toto");
		createAccountRequest.put("mail", "toto@toto.net");
		f.createAccount(createAccountRequest);
		
			
			//Directory dir= (Directory) obj;
			
			//((Directory) obj).setTitle("hello");
			//((Directory) obj).updateDirectory();
			//logger.debug("{}", element);
			}
		 /*JSONWriter writer = new JSONWriter();
		  System.out.println("JSONWriter result is " + writer.write(feed));
		  System.exit(0);*/
	}
}
	
