/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package x_o_game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Dell
 */
public class X_O_Game extends Application {

    Scene scene1, scene2, scene3, scene4;
    Image x_image = null;
    

    @Override
    public void start(Stage primaryStage) {
        String cwd = System.getProperty("user.dir");
//        first scene components
        TextField emailField = new TextField();
        TextField passwordField = new TextField();
        Button signInBtn = new Button();
        Button signUpBtn = new Button();
        signInBtn.setText("Sign In");
        signUpBtn.setText("Sign Up");
        Label emailLabel = new Label("Email");
        emailLabel.setFont(new Font(24));
        emailLabel.setTextFill(Color.AQUA);
        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(new Font(24));
        passwordLabel.setTextFill(Color.AQUA);
        FlowPane signOptions = new FlowPane();
        FlowPane logIn = new FlowPane();

        //Setting the vertical and horizontal gaps between the columns 
        logIn.setVgap(10);
        logIn.setHgap(5);
        signOptions.setHgap(5);
        logIn.setOrientation(Orientation.VERTICAL);
        //Setting the Grid alignment 
        logIn.setAlignment(Pos.CENTER);
        signOptions.getChildren().addAll(signInBtn, signUpBtn);
        logIn.getChildren().addAll(emailLabel, emailField, passwordLabel, passwordField, signOptions);
        BorderPane root = new BorderPane();
        root.setCenter(logIn);
        BackgroundImage myBI;
        try {
            myBI = new BackgroundImage(new Image(new FileInputStream(cwd+"\\images\\bg.jpg")),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));
            root.setBackground(new Background(myBI));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(X_O_Game.class.getName()).log(Level.SEVERE, null, ex);
        }
//        second scene        

        Button playVsComputerBtn = new Button();
        playVsComputerBtn.setText("play VS computer");
        Button playVsFriendBtn = new Button();
        playVsFriendBtn.setText("play VS friend");
        playVsFriendBtn.setDisable(true);
        ListView playersList = new ListView();
        Label p1 = null;
        try {

            p1 = new Label("player1",
                    new ImageView(new Image(new FileInputStream(cwd+"\\images\\online.png"), 10, 10, false, false)));
            p1.setOnMouseClicked((MouseEvent event) -> {
                playVsFriendBtn.setDisable(false);
            });
        } catch (FileNotFoundException ex) {
            Logger.getLogger(X_O_Game.class.getName()).log(Level.SEVERE, null, ex);
        }

        p1.setContentDisplay(ContentDisplay.RIGHT);
        playersList.getItems().addAll(p1, "player2");
        FlowPane playBtns = new FlowPane();
        playBtns.getChildren().addAll(playVsComputerBtn, playVsFriendBtn);
        playBtns.setAlignment(Pos.CENTER);
        BorderPane root2 = new BorderPane();
        root2.setLeft(playersList);
        root2.setCenter(playBtns);
//      third scene

        try {
            x_image = new Image(new FileInputStream(cwd+"\\images\\x.jpg"),
                    100, 100, false, false);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(X_O_Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        GridPane GameArea = new GridPane();
        try {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    ImageView box = new ImageView(new Image(new FileInputStream(cwd+"\\images\\empty.jpg"),
                            100, 100, false, false));

                    box.setOnMouseClicked((MouseEvent event) -> {
                        box.setImage(x_image);
                    });
                    GameArea.add(box, j, i);

                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(X_O_Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        GameArea.setAlignment(Pos.CENTER);
    //        Chat Area
        TitledPane chatPane = new TitledPane();
        chatPane.setText("Chat");
        TextField sentMessage = new TextField();
        TextArea recievedMessage = new TextArea();
        FlowPane chatItems = new FlowPane();
        chatItems.getChildren().addAll(sentMessage, recievedMessage);
        chatItems.setOrientation(Orientation.VERTICAL);
        chatPane.setContent(chatItems);
    //        players Data Area 
        FlowPane playersArea = new FlowPane();

        BorderPane root3 = new BorderPane();
        root3.setCenter(GameArea);
        root3.setBottom(chatPane);
//        fourth scene
        GridPane GameArea2 = new GridPane();
        try {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    ImageView box = new ImageView(new Image(new FileInputStream(cwd+"\\images\\empty.jpg"),
                            100, 100, false, false));

                    box.setOnMouseClicked((MouseEvent event) -> {
                        box.setImage(x_image);
                    });
                    GameArea2.add(box, j, i);

                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(X_O_Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        GameArea2.setAlignment(Pos.CENTER);
        BorderPane root4 = new BorderPane();
        root4.setCenter(GameArea2);
//        Event Listeners
        signInBtn.setOnAction((ActionEvent event) -> {
            primaryStage.setScene(scene2);
        });

        playVsComputerBtn.setOnAction((ActionEvent event) -> {
            primaryStage.setScene(scene4);
        });

        playVsFriendBtn.setOnAction((ActionEvent event) -> {
            primaryStage.setScene(scene3);
        });
//        Scenes
        scene1 = new Scene(root, 500, 500);
        scene2 = new Scene(root2, 500, 500);
        scene3 = new Scene(root3, 500, 500);
        scene4 = new Scene(root4, 500, 500);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
