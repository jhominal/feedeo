package feedeo;

public class Preference extends HibernateObject {
	
	 Integer id;
	 String name;
	 String value;
	 
	 public Preference(){}
	 
	 public void setId(Integer id){
		 this.id=id;
	 }
	 
	 public Integer getId(){
		 return id;
	 }
	 public void setName(String name){
		 this.name=name;
	 }
	 
	 public String getName(){
		 return name;
	 }
	 
	 public void setValue(String value){
		 this.value=value;
	 }
	 
	 public String getValue(){
		 return name;
	 }
	 
	 
	

}
