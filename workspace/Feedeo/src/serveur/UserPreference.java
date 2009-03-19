package serveur;



public class UserPreference extends HibernateObject  {
	
	public UserPreferencePK  idUserPreference;
	public String value;
	
	
	public UserPreference(){}
	
	public UserPreference(UserPreferencePK  idUserPreference,String value){
		
		this.idUserPreference=idUserPreference;
		
		this.value=value;
	}
	
	public UserPreferencePK  getIdUserPreference() {
		return idUserPreference;
		}
		public void setIdUserPreference(UserPreferencePK  idUserPreference) {
		this.idUserPreference = idUserPreference;
		}
		
		public String getValue(){
			return value;
			
		}
		
       public void setValue(String value){
    	   
    	   this.value=value;
    	   
       }
       
      
		public void createUserPreference() {
			HibernateObject.create(this);	
		}
		
		public void deleteUserPreference() {
			HibernateObject.delete(this);	
		}
		
		public void updateUserPreference() {
			HibernateObject.update(this);	
		}
}
