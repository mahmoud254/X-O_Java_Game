package servertictactoe;

import Models.Client;
import Models.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Bassant Sayed
 */
public class NetworkController extends Application implements Runnable {

    private NetworkModel network;
    private ServerView view;
    private Thread networkTh;

    public void establishNetwork() {
        try {
            network = NetworkModel.getNetwork();
            System.out.println("Server started");
            while (true) {
                Socket s = NetworkModel.serverSocket.accept();
                System.out.println("getting connected by a user");
                new ChatHandler(s);
            }
        } catch (IOException ex) {
            Platform.runLater(() -> {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Error information");
                a.setTitle("Error");
                a.setContentText("Error in making Socket this port might be taken");
                a.show();
            });

        }
    }

    @Override
    public void start(Stage primaryStage) {
        networkTh = new Thread(this);
        view = new ServerView();
        view.btnStart.setOnAction((ActionEvent event) -> {
            if (!networkTh.isAlive()) {
                networkTh.start();
            }
            showNotification("bassant");
        });

        view.btnStop.setOnAction((ActionEvent event) -> {
            //action on button stop
            System.out.println("Client online are :");
            System.out.println(ChatHandler.clientsVector);
            ChatHandler.clientsVector.stream().forEach((ch) -> {
                ch.stop();
            });
            try {
                if (network != null) {
                    NetworkModel.serverSocket.close();
                }
            } catch (IOException ex) {
                System.out.println("can't close server socket");
            }
            if (networkTh.isAlive()) {
                networkTh.stop();
            }
        });
        primaryStage.setTitle("Sever");
        primaryStage.setScene(view.getScene());
        primaryStage.show();
    }

    @Override
    public void run() {
        establishNetwork();
    }

    public static void main(String[] args) {

        launch(args);
    }
    private void showNotification(String username){
        Notifications notifications= Notifications.create().title("New user is online").text(username+" is onine now")
                            .hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
                    notifications.show();
    }

}

class ChatHandler extends Thread {

    Client client;
    ObjectInputStream in;
    ObjectOutputStream out;
    public static Vector<ChatHandler> clientsVector = new Vector<>();

    ChatHandler(Socket s) {
        try {
            out = new ObjectOutputStream(s.getOutputStream());
            in = new ObjectInputStream(s.getInputStream());
            clientsVector.add(this);
            start();
        } catch (IOException ex) {
            System.out.println("File r|w exception");
        }

    }

    @Override
    public void run() {
        while (true) {
            Message m;
            try {
                m = (Message) in.readObject();
                System.out.println("recieve msg");
                System.out.println("msg type"+m.getType());
                client = m.getSender();
                //validate email and password for signin/////////////////
                if (m.getType().equals("LogIn")) {
                    Crud crud = new Crud();
                    if (crud.select2(m.getSender().getEmail(), m.getSender().getPassword())) {
                        m.setIsValidUser(true);
                        System.out.println("a valid user");
                    } else {
                        m.setIsValidUser(false);
                        System.out.println("not valid user");
                    }
                    System.out.println("recieved");
                    getAllUser(m);
                    out.writeObject(m);
                    System.out.println("Done ");
                    
                }
                //////////////////////////////////////////////
                // signUp/////////////////
                if (m.getType().equals("SignUp")) {
                    Crud crud = new Crud();
                    SignUp signup = new SignUp();
                    if (crud.select1(m.getSender().getEmail())) {
                        m.setIsValidUser(false);
                    } else {
                        m.setIsValidUser(true);
                        getAllUser(m);
                        signup.setMail(m.getSender().getEmail());
                        signup.setPassword(m.getSender().getPassword());
                        crud.insert(signup);
                    }
                    System.out.println("sign up recieved");
                    out.writeObject(m);
                } //////////////////////////////////////////////                
                if (m.getType().equals("PlayRequest")){
                    System.out.println("sending invetation");
                    System.out.println(m.getSender().getEmail());
                    sendMessage(m);
                    
                }
                if (m.getType().equals("Chat")){
                    System.out.println("chating");
                    sendMessage(m);
                }
            } catch (IOException ex) {
                System.out.println("error sending msgs to one of clients");
                //System.out.println("client " + this.client.getEmail() + " loged out");
                clientsVector.remove(this);
                stop();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ChatHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void getAllUser(Message msg) {
        Crud crud = new Crud();
        ArrayList<String> allClients = (ArrayList<String>) crud.select_all_mails();
        String online = "";
        String offline = "";
	boolean append=false;
        for (String user : allClients) {
            if (!client.getEmail().equals(user)) {
                for (ChatHandler ch : clientsVector) {
                    if (ch.client.getEmail().equals(user)) {
                        online += user + ",";
                    } else {
			append=true;		
                    }
                }
		if (append==true){
		offline += user + ",";}
            }
        }
        msg.setMsg(online + "|" + offline);
        System.out.println("users =" + msg.getMsg());
        try {
            out.writeObject(msg);
            System.out.println("sending data");
        } catch (IOException ex) {
            System.out.println("Error sending list of users");
        }
    }

    void sendMessage(Message msg) {
        clientsVector.stream().filter((ch) -> (ch.client.getEmail().equals(msg.getReciever()))).forEach((ch) -> {
            try {
                ch.out.writeObject(msg);
                System.out.println("msg sent");
            } catch (IOException ex) {
                System.out.println("can't send object from server");
            }
        });
    }
}
