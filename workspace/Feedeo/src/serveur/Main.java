package serveur;

public class Main {

	
	public Main()
	{
		
	}
	

	public static void main(String[] arg){
		//String[] url=new String[1];
		//url[0]="http://fcargoet.evolix.net/feed/";
		//TEST 1 REUSSI
		User user1 = new User("meriam","sekkat","mimi","tintin20");
		User user2 = new User("ghita","sekkat","tita","tite200");
		
		/** In a first time, two preferences: color and police size*/

		 Preference color= new Preference("color");
	 	 Preference police=new Preference ("police");
	 	 
	 	 UserPreferencePK pk=new UserPreferencePK(user1, color);
	 	 UserPreference up= new UserPreference(pk,"vert");
	 	 
	 	 
	 	 color.createPreference();
	 	 police.createPreference();
		 user1.createUser();
		 user2.createUser();
		 up.createUserPreference();
		
		 
		// LECTURE D'UN FLUX CREATION D'ARTICLE DANS UN DOSSIER
		 Directory dir1_user1=new Directory("racine",user1);
		String url="http://fcargoet.evolix.net/feed/";
		Feed feed=new Feed(url,dir1_user1,user1);
		
		dir1_user1.createDirectory();
		feed.createFeed();
		feed.setArticles(feed.getFeedOrigine(),dir1_user1);
		//FIN LECTURE D'UN FLUX
		
		 /* JSONWriter writer = new JSONWriter();
		  System.out.println("JSONWriter result is " + writer.write(feed));
		  System.exit(0);*/

}
	
}
