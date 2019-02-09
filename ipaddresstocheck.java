import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ipaddresstocheck {
	public Set<String[]> onlineUsers = new HashSet<String[]>();
	DataInputStream dis=null;
	public ipaddresstocheck(Socket cs) {
		try {
			dis = new DataInputStream(cs.getInputStream());
			String name=dis.readLine();
			onlineUsers.add(new String[] {name,cs.getInetAddress()+""});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
