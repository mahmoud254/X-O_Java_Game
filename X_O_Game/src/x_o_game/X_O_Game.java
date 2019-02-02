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
import javafx.animation.StrokeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Dell
 */
public class X_O_Game extends Application {

    Scene scene1, scene2, scene3, scene4;
    Image user_image= null;
    Image user_image2= null;
    Image computer_image= null;
    Image backgroundImage1= null;
    Image backgroundImage2= null;
    Image backgroundImage3= null;
    Image backgroundImage4= null;
    BackgroundImage background1;
    BackgroundImage background2;
    BackgroundImage background3;
    BackgroundImage background4;
    Color c=Color.rgb(153, 51, 255,0.5);
    Color c2=Color.rgb(255, 255, 204,1);   
    Color c3=Color.rgb(255, 51, 153,1); 
    
    
    @Override
    public void start(Stage primaryStage) {

        String cwd = System.getProperty("user.dir");
//        audio
        Media media = new Media(getClass().getResource("\\main.mp3").toExternalForm()); //replace /Movies/test.mp3 with your file
        MediaPlayer player = new MediaPlayer(media); 
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.play();      
//        reading images
        try {
            backgroundImage1=new Image(new FileInputStream(cwd+"\\images\\bg.jpg"));
            backgroundImage2=new Image(new FileInputStream(cwd+"\\images\\bg5.jpg"));
            backgroundImage3=new Image(new FileInputStream(cwd+"\\images\\bg3.jpg"));
            backgroundImage4=new Image(new FileInputStream(cwd+"\\images\\bg7.jpg"));
            background1 = new BackgroundImage(backgroundImage1,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));            
            background2 = new BackgroundImage(backgroundImage2,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));
            background3 = new BackgroundImage(backgroundImage3,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));
            background4 = new BackgroundImage(backgroundImage4,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));          
        } catch (FileNotFoundException ex) {
            Logger.getLogger(X_O_Game.class.getName()).log(Level.SEVERE, null, ex);
        }        
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
        root.setBackground(new Background(background1));
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
        playersList.setMaxWidth(100);
        Menu levels = new Menu("level");
        MenuItem easy = new MenuItem("easy");
        MenuItem hard = new MenuItem("hard");
        levels.getItems().addAll(easy,hard);
        MenuBar bar = new MenuBar();
        bar.getMenus().addAll(levels);
        GridPane playBtns = new GridPane();
        playBtns.add(playVsComputerBtn,0,0);
        playBtns.add(playVsFriendBtn,1,0);
        playBtns.add(bar,0,1);
        playBtns.setAlignment(Pos.CENTER);
        BorderPane root2 = new BorderPane();
        root2.setLeft(playersList);
        root2.setCenter(playBtns);
        root2.setBackground(new Background(background2));
//      third scene

        try {
            user_image = new Image(new FileInputStream(cwd+"\\images\\A.png"),
                    100, 100, false, false);
            user_image2 = new Image(new FileInputStream(cwd+"\\images\\B.png"),
                    100, 100, false, false);
            computer_image = new Image(new FileInputStream(cwd+"\\images\\computer.png"),
                    100, 100, false, false);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(X_O_Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        GridPane GameArea = new GridPane();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    StackPane box=new StackPane();
                    Rectangle rect = new Rectangle();  
                    rect.setX(0f); 
                    rect.setY(0f); 
                    rect.setArcWidth(30.0); 
                    rect.setArcHeight(20.0); 
                    rect.setWidth(100.0f); 
                    rect.setHeight(100.0f);
                    rect.setStrokeWidth(5.0);
                    rect.setStroke(c2);
                    rect.setFill(c);
                    box.getChildren().add(rect);
                    box.setOnMouseClicked((MouseEvent event) -> {
                        Line x1=new Line(10,10,90,90);
                        Line x2=new Line(10,90,90,10);
                        x1.setStrokeWidth(5.0);
                        x2.setStrokeWidth(5.0);
                        StrokeTransition   st1 = new StrokeTransition ();
                        StrokeTransition   st2 = new StrokeTransition ();
                        st1.setDuration(Duration.millis(1000)); 
                        st1.setShape(x1);
                        st1.setFromValue(Color.TRANSPARENT);
                        st1.setToValue(c3);
                        st1.play();  
                        st2.setDuration(Duration.millis(1000)); 
                        st2.setShape(x2);
                        st2.setFromValue(Color.TRANSPARENT);
                        st2.setToValue(c3);
                        st2.play();                        
                        box.getChildren().addAll(x1,x2);
                    });
                    GameArea.add(box, j, i);

                }
            }

        GameArea.setAlignment(Pos.CENTER);
        GameArea.setHgap(10);
        GameArea.setVgap(10);
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
        FlowPane topPane=new FlowPane();
        ImageView user1Icon=new ImageView(user_image);
        ImageView user2Icon=new ImageView(user_image2);
        topPane.getChildren().addAll(user1Icon,user2Icon);
        topPane.setHgap(300);
        BorderPane root3 = new BorderPane();
        root3.setCenter(GameArea);
        root3.setBottom(chatPane);
        root3.setTop(topPane);
        root3.setBackground(new Background(background3)); 
//        fourth scene
        GridPane GameArea2 = new GridPane();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    StackPane box=new StackPane();
                    Rectangle rect = new Rectangle();  
                    rect.setX(0f); 
                    rect.setY(0f); 
                    rect.setArcWidth(30.0); 
                    rect.setArcHeight(20.0); 
                    rect.setWidth(100.0f); 
                    rect.setHeight(100.0f);
                    rect.setStrokeWidth(5.0);
                    rect.setStroke(c2);
                    rect.setFill(c);
                    box.getChildren().add(rect);
                    box.setOnMouseClicked((MouseEvent event) -> {
                        Line x1=new Line(10,10,90,90);
                        Line x2=new Line(10,90,90,10);
                        x1.setStrokeWidth(5.0);
                        x2.setStrokeWidth(5.0);
                        StrokeTransition   st1 = new StrokeTransition ();
                        StrokeTransition   st2 = new StrokeTransition ();
                        st1.setDuration(Duration.millis(1000)); 
                        st1.setShape(x1);
                        st1.setFromValue(Color.TRANSPARENT);
                        st1.setToValue(c3);
                        st1.play();  
                        st2.setDuration(Duration.millis(1000)); 
                        st2.setShape(x2);
                        st2.setFromValue(Color.TRANSPARENT);
                        st2.setToValue(c3);
                        st2.play();                        
                        box.getChildren().addAll(x1,x2);
                        
                    });
                    GameArea2.add(box, j, i);

                }
            }

      
        GameArea2.setAlignment(Pos.CENTER);
        GameArea2.setHgap(10);
        GameArea2.setVgap(10);
        FlowPane topPane2=new FlowPane();
        ImageView userIcon=new ImageView(user_image);
        ImageView computerIcon=new ImageView(computer_image);
        topPane2.getChildren().addAll(userIcon,computerIcon);
        topPane2.setHgap(300);
        BorderPane root4 = new BorderPane();
        root4.setCenter(GameArea2);
        root4.setTop(topPane2);
        root4.setBackground(new Background(background4));        
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
