package serveur;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.stringtree.json.JSONWriter;

public class Main {

	
	public Main()
	{
		
	}
	

	public static void main(String[] arg){
		
		List<HibernateObject> resp=HibernateObject.listObject("select distinct article from Directory as dir inner join dir.listArticle as article where dir.idDirectory= "+1);
		for (Iterator<HibernateObject> iter = resp.iterator(); iter.hasNext();) {
			HibernateObject obj=iter.next();
			if (obj instanceof Article)
			{
				Article art= (Article) obj;
				List<HibernateObject> respo=HibernateObject.listObject("select artprop from Articles_Properties as artprop where artprop.idUserArticle.idArticle.idArticle= "+art.getIdArticle()+" and artprop.idUserArticle.idUser.idUser="+1);
				for (Iterator<HibernateObject> iterArt = respo.iterator(); iterArt.hasNext();) {
					HibernateObject stateObj=iterArt.next();
					if (stateObj instanceof Articles_Properties)
					{
						Articles_Properties articleState=(Articles_Properties)stateObj;
						System.out.print(art.getTitle());
						System.out.print(articleState.getLu());
						

					}
					
				}
			}
		}
		//String[] url=new String[1];
		//url[0]="http://fcargoet.evolix.net/feed/";
		//TEST USER REUSSI 
		/*		
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
		feed.setArticles(dir1_user1);
		dir1_user1.updateDirectory();
		//FIN LECTURE D'UN FLUX
		
		//RECUPERER LES SOUS DOSSIER D'UN DOSSIER DONNE DE L'UTILISATEUR
		List<HibernateObject>rep=HibernateObject.listObject("select dir from Directory as dir, User as user inner join dir.user as user where dir.idParent = 0");
		JSONWriter writer=new JSONWriter(); 
		String jsonrep=writer.write(rep);
		System.out.println(jsonrep);
		//FIN RECUPERER LES SOUS DOSSIER D'UN DOSSIER DONNE DE L'UTILISATEUR
		/*
		String login="mimo";
		FeedeoHandler f=new FeedeoHandler(null);
		HashMap<String, Object> createAccountRequest = new HashMap<String, Object>();
		createAccountRequest.put("login",login);
		createAccountRequest.put("password", "toto");
		createAccountRequest.put("mail", "toto@toto.net");
		f.createAccount(createAccountRequest);
		
			*/
			//Directory dir= (Directory) obj;
			
			//((Directory) obj).setTitle("hello");
			//((Directory) obj).updateDirectory();
			//logger.debug("{}", element);
			}
		 /*JSONWriter writer = new JSONWriter();
		  System.out.println("JSONWriter result is " + writer.write(feed));
		  System.exit(0);*/
	}
	
