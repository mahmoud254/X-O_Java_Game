package servertictactoe;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author Bassant Sayed
 */
public class NetworkModel {
    public static ServerSocket serverSocket;
    public static NetworkModel instance ;
    private NetworkModel() throws IOException {
            serverSocket = new ServerSocket(5005);
    }
     public static NetworkModel getNetwork() throws IOException{
         if(instance == null)
        {
            instance = new NetworkModel();
            return instance;
        }
        else
        {
            return instance;
        }
     
     }
}
