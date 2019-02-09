import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class checkAlive {
	static Set<String> onlineUsers = new HashSet<String>();
	
	public static void checkHosts(String subnet){
		   Thread th=new Thread(new Runnable() {  //Thread that keep checking if the player ip alive or not
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				while(true) {
					int timeout=400;
					   for (int i=1;i<255;i++){   //check the 255 ip in the network and write ip which is reachable
					       String host=subnet + "." + i;
					       try {
					    	   //System.out.println(host);
							if (InetAddress.getByName(host).isReachable(timeout)){
								   
							       System.out.println(host + " is reachable");
							       onlineUsers.add(host);
							   }
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					   }
					}
				}
			
		});
		   th.start();
	}
	public static void main(String[] args) {
		checkHosts("192.168.1");  //write the subnet of the network
		for(String i:onlineUsers) {
			System.out.println(i);
		}
	}

}
