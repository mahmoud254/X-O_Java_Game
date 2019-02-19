package View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Dell
 */
//Intro to play with friends online
public class PlayOnlineIntro {

    Scene scene;
    Image backgroundImage = null;
    BackgroundImage background;
    public Button playVsFriendBtn;
    ListView playersList;
    String cwd;
    GridPane playBtns;
    BorderPane root;
    public String opponent;
    public ArrayList<String> onlineUsers;
    public ArrayList<String> offlineUsers;
    
    public PlayOnlineIntro() {
        offlineUsers=new ArrayList<>();
        onlineUsers=new ArrayList<>();
        playVsFriendBtn = new Button();
        intializeScene();
    }
    public PlayOnlineIntro(ArrayList<String> onlineUsers,ArrayList<String> offlineUsers,Button x) {
        this.offlineUsers=offlineUsers;
        this.onlineUsers=onlineUsers;
        this.playVsFriendBtn=x;
        intializeScene();
    }
    
    final void intializeScene(){
        System.out.println("intialize scene");
        cwd = System.getProperty("user.dir");
        opponent="";
        try {
            backgroundImage = new Image(new FileInputStream(cwd + "\\images\\bg5.jpg"));
            background = new BackgroundImage(backgroundImage,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PlayOnlineIntro.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        playVsFriendBtn.setText("play VS friend");
        playVsFriendBtn.setDisable(true);
        playersList = new ListView();
        listingPlayrs(onlineUsers, "online");
        listingPlayrs(offlineUsers, "offline");
        playersList.setMaxWidth(120);
        playBtns = new GridPane();
        playBtns.add(playVsFriendBtn, 1, 0);
        playBtns.setAlignment(Pos.CENTER);
        root = new BorderPane();
        root.setLeft(playersList);
        root.setCenter(playBtns);
        root.setBackground(new Background(background));

//        Scenes
        scene = new Scene(root, 500, 500);
        
    }

    public final void listingPlayrs(ArrayList<String> arrPlayers, String statusOfPlayers) {
        for (String arrPlayer : arrPlayers) {
            String name;
            Label p=null;
            try {
                if (statusOfPlayers.equals("online")) {
                    name = arrPlayer;
                    p = new Label(arrPlayer, new ImageView(new Image(new FileInputStream(cwd + "\\images\\online.png"), 10, 10, false, false)));
                    p.setOnMouseClicked((MouseEvent event) -> {
                        System.out.println("clicked");
                        opponent = name;
                        playVsFriendBtn.setDisable(false);
                    });
                } else {
                    p = new Label(arrPlayer, new ImageView(new Image(new FileInputStream(cwd + "\\images\\offline.png"), 10, 10, false, false)));
                }
            }catch (FileNotFoundException ex) {
                Logger.getLogger(PlayOnlineIntro.class.getName()).log(Level.SEVERE, null, ex);
            }
            //p.setContentDisplay(ContentDisplay.RIGHT);
            playersList.getItems().addAll(p);
        }

    }

    public Scene getScene() {
        return scene;
    }

}
