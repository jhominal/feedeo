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
