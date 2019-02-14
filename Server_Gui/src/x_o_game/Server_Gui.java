/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package x_o_game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author user
 */
public class Server_Gui extends Application implements Runnable{
    String cwd;
    Thread th;
    @Override
        
    public void run() {
    new NetworkConnection();  
    }
    @Override
    public void start(Stage primaryStage) {
        th=new Thread(this);
        cwd = System.getProperty("user.dir");
        Button btnStart = new Button("Start");
        Button btnStop = new Button("Stop");
        ListView<Label> lvList = new ListView<Label>();
        VBox vbButtons = new VBox();
        BorderPane borderPane = new BorderPane();

        btnStart.setMinSize(200, 20);
        btnStop.setMinSize(200, 20);

        vbButtons.setSpacing(10);
        vbButtons.setPadding(new Insets(0, 20, 10, 20));
        vbButtons.getChildren().addAll(btnStart, btnStop);
        Label l1 = new Label("player1");
       Label p1 = null;
        try {
            p1 = new Label("player1",
                    new ImageView(new Image(new FileInputStream(cwd + "\\images\\online.png"), 10, 10, false, false)));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Server_Gui.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        lvList.getItems().addAll(p1);
        lvList.setMaxHeight(Control.USE_PREF_SIZE);

        borderPane.setPadding(new Insets(20, 0, 20, 20));
        borderPane.setRight(vbButtons);
        borderPane.setLeft(lvList);
        
        //action listeners
        btnStart.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                th.start();
                 showNotification("bassant");
            }
        });

        btnStop.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //action on button stop
                System.out.println(ChatHandler.clientsVector);
                 for (ChatHandler ch : ChatHandler.clientsVector) {
                    ch.stop();
                }
                try {
                    NetworkConnection.serverSocket.close();
                } catch (IOException ex) {
                    System.out.println("can't close server socket");
                }
                th.stop();
                
                
            }
        });
         

        Scene scene = new Scene(borderPane, 500, 400);
        primaryStage.setTitle("Sever");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private void showNotification(String username){
        Notifications notifications= Notifications.create().title("New user is online").text(username+" is onine now")
                            .hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
                    notifications.show();
    }



}
