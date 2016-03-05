import java.net.*;
import java.util.*;
import java.io.*;

public class Notes_Server {
	private static void storeInFile(ArrayList<Note> al){
			
		try 
		{
			FileOutputStream fileOut = new FileOutputStream("ser_note.ser");
			ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
			
			objOut.writeObject(al);
			objOut.close();
			fileOut.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException{
		int port = 9008;
		ServerSocket listener = null;
		
		ArrayList<Note> stored = new ArrayList<Note>();
		stored.add(new Note("Youcaf","Something"));
		
		try {
			listener = new ServerSocket(port);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("Running...");
		try{
			while(true){
				Socket cSocket = listener.accept();
				
				InputStream fromClient = cSocket.getInputStream();
				DataInputStream in_string = new DataInputStream(cSocket.getInputStream());
				DataOutputStream out = new DataOutputStream(cSocket.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(cSocket.getInputStream());
				//Check whether to send or receive
				int choice = fromClient.read();
				
				if(choice==0){
					//Send all notes according to username
					String uname = in_string.readUTF();
					System.out.println("Received: " + uname);
					String toSend = "Hahahah";
					for(int i=0; i<stored.size(); i++){
						if(stored.get(i).username.equals(uname)){
							toSend.concat(stored.get(i).data+"\n");
						}
					}
					System.out.println("toSend: " +toSend);
					
					out.writeUTF(toSend);
					
					
				}else if(choice==1){
					Note tempNote = (Note)ois.readObject();
					if(tempNote != null){
						System.out.println("Storing: " +tempNote.username +" | " +tempNote.data);
						stored.add(tempNote);
					}
					else{
						System.out.println("reading error");
					}
					
					System.out.println("Current stored: ");
					for(int i=0; i<stored.size(); i++){
						System.out.println(stored.get(i).username + " | " +stored.get(i).data);
					}
				}	
				out.writeUTF(" Congratulations! Done");
				
				
				cSocket.close();
				}			
		} catch(IOException e){
			e.printStackTrace();
		}
		
		listener.close();
	}
}
