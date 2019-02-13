package x_o_game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import static x_o_game.ChatHandler.clientsVector;

/**
 *
 * @author Bassant Sayed
 */
public class NetworkConnection {
    
    public static ServerSocket serverSocket;
    int[] ids;
    static int i;
    public NetworkConnection() {
        ids = new int[]{10, 15, 20, 25};
        i=0;
        try {
            serverSocket = new ServerSocket(5005);
            while (true) {
               Socket s = serverSocket.accept();
                System.out.println("started");
                new ChatHandler(s,ids[i++]);
            }

        } catch (Exception ex) {
            System.out.println("Unavailable port");
        }
    }

}

class ChatHandler extends Thread {

    int clientId;
    ObjectInputStream in;
    ObjectOutputStream out;
    public static Vector<ChatHandler> clientsVector = new Vector<ChatHandler>();

    ChatHandler(Socket s, int id) {
        try {
            System.out.println("client been created with id "+id);
            clientId = id;
            out = new ObjectOutputStream(s.getOutputStream());
            in = new ObjectInputStream(s.getInputStream());
            clientsVector.add(this);
            start();
        } catch (IOException ex) {
            System.out.println("File r|w exception");
        }

    }

    public void run() {
        while (true) {
            Message m;
            try {

                m = (Message) in.readObject();
                //validate email and password for signin/////////////////
                if (m.getType().equals("LogIn")){
                    m.setIsValidUser(true);
                    System.out.println("recieved");
                    out.writeObject(m);
                }
                //////////////////////////////////////////////
                //validate email and password for signUp/////////////////
                System.out.println(m.getType());
                if (m.getType().equals("SignUp")){
                    m.setIsValidUser(true);
                    System.out.println("recieved");
                    out.writeObject(m);
                }
                //////////////////////////////////////////////                
                else{
                System.out.println("masseage recieved = " + m.getMsg());
                if(!m.isConnectrequest()){
                    sendMessage(m);
                }else{
                    m.setMsg(m.getSenderId()+" want to play with you");
                     sendMessage(m);
                }
                }
            } catch (IOException ex) {
                System.out.println("error sending msgs to one of clients");
                System.out.println("client "+this.clientId+" loged out");
                clientsVector.remove(this);
                stop();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ChatHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }


    void sendMessage(Message msg) {
        for (ChatHandler ch : clientsVector) {
            if (ch.clientId == msg.getRecieverId()) {
                try {
                    ch.out.writeObject(msg);
                    System.out.println("msg sent");
                } catch (IOException ex) {
                    System.out.println("can't send object from server");
                }
            }
        }
    }
}

