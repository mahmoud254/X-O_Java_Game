/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
public class PlayLocally {

    Scene scene;
    Image backgroundImage = null;
    BackgroundImage background;
    public Button playVsFriendBtn;
    String cwd;
    public Button playVsComputerBtn;
    Menu levels;
    MenuItem easy;
    MenuItem hard;
    MenuBar bar;
    GridPane playBtns;
    BorderPane root;
    public String level;

    public PlayLocally() {
        cwd = System.getProperty("user.dir");

        try {
            backgroundImage = new Image(new FileInputStream(cwd + "\\images\\bg2.jpg"));
            background = new BackgroundImage(backgroundImage,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PlayLocally.class.getName()).log(Level.SEVERE, null, ex);
        }

//        second scene        
        playVsComputerBtn = new Button();
        playVsComputerBtn.setText("play VS computer");
        playVsComputerBtn.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);-fx-background-radius: 30;-fx-font-size:18;-fx-font-family: \"Palatino Linotype\";-fx-background-insets: 0;-fx-text-fill: white;\n"
                + "");
        playVsFriendBtn = new Button();
        playVsFriendBtn.setText("play VS friend");
        playVsFriendBtn.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);-fx-font-size:18;-fx-font-family: \"Palatino Linotype\";-fx-background-radius: 30;-fx-background-insets: 0;-fx-text-fill: white;\n"
                + "");
        levels = new Menu("level");
        easy = new MenuItem("easy");
        hard = new MenuItem("hard");
        levels.getItems().addAll(easy, hard);
        bar = new MenuBar();
        bar.setStyle("-fx-background-color:linear-gradient(#f2f2f2, #d6d6d6),linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%),linear-gradient(#dddddd 0%, #f6f6f6 50%);-fx-background-radius: 8,7,6;-fx-background-insets: 0,1,2;-fx-text-fill: black;-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        easy.setStyle("-fx-background-color:linear-gradient(#f2f2f2, #d6d6d6),linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%),linear-gradient(#dddddd 0%, #f6f6f6 50%);-fx-background-radius: 8,7,6;-fx-background-insets: 0,1,2;-fx-text-fill: black;-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        hard.setStyle("-fx-background-color:linear-gradient(#f2f2f2, #d6d6d6),linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%),linear-gradient(#dddddd 0%, #f6f6f6 50%);-fx-background-radius: 8,7,6;-fx-background-insets: 0,1,2;-fx-text-fill: black;-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        bar.getMenus().addAll(levels);
        playBtns = new GridPane();
        playBtns.add(playVsComputerBtn, 0, 0);
        playBtns.add(playVsFriendBtn, 1, 0);
        playBtns.add(bar, 0, 1);
        playBtns.setAlignment(Pos.CENTER);
        root = new BorderPane();
        root.setCenter(playBtns);
        root.setBackground(new Background(background));
        easy.setOnAction((ActionEvent event) -> {
            level="easy";
        });
        hard.setOnAction((ActionEvent event) -> {
            level="hard";
        });
//        Scenes
        scene = new Scene(root, 500, 500);

    }

   

    public Scene getScene() {
        return scene;
    }

}
