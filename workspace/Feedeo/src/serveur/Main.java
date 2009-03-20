package serveur;
//import java.util.Iterator;
//import java.util.Vector;
import org.stringtree.json.JSONWriter;
/*import java.util.Iterator;
import java.util.Vector;
*/

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
		String url="http://fcargoet.evolix.net/feed/";
		Feed feed=new Feed(url);
		//Iterator<Article> itr = v.iterator();
		//while (itr.hasNext())
		//{
		//	System.out.println(itr.next().getTittle());
		//}
		
		//TEST 2 REUSSI

		  JSONWriter writer = new JSONWriter();
		  System.out.println("JSONWriter result is " + writer.write(feed));
		  System.exit(0);

}
	
}
