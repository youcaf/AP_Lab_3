public class Note implements java.io.Serializable{
	public String username;
	public String data;
	
	Note(){
		username = "Unspecified";
		data = "Nil";		
	}
	
	Note(String argU, String argD){
		username = argU;
		data = argD;		
	}
}
