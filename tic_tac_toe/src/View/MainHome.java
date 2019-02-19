
package View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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
//Home page
public class MainHome {
    
    private final Scene scene;
    private Image backgroundImage = null;
    private BackgroundImage background;
    public Button playOffline;
    private String cwd;
    public Button playOnline;
    private final GridPane playBtns;
    private final BorderPane root;

    public MainHome() {
        cwd = System.getProperty("user.dir");

        try {
            backgroundImage = new Image(new FileInputStream(cwd + "\\images\\bg4.jpg"));
            background = new BackgroundImage(backgroundImage,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PlayLocally.class.getName()).log(Level.SEVERE, null, ex);
        }

//        second scene        
        playOnline = new Button();
        playOnline.setText("play online");
        playOnline.setStyle("-fx-background-color:linear-gradient(#f0ff35, #a9ff00),radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%);-fx-background-radius: 6, 5;-fx-font-size:18;-fx-font-family: \"Palatino Linotype\";-fx-background-insets: 0, 1;-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );-fx-text-fill: #395306;\n" +
"");
        playOffline = new Button();
        playOffline.setText("play offline");
        playOffline.setStyle("-fx-background-color:linear-gradient(#f0ff35, #a9ff00),radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%);-fx-background-radius: 6, 5;-fx-font-size:18;-fx-font-family: \"Palatino Linotype\";-fx-background-insets: 0, 1;-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );-fx-text-fill: #395306;\n" +
"");
        playBtns = new GridPane();
        playBtns.add(playOffline, 0, 0);
        playBtns.add(playOnline, 1, 0);
        playBtns.setAlignment(Pos.CENTER);
        root = new BorderPane();
        root.setCenter(playBtns);
        root.setBackground(new Background(background));

//        Scenes
        scene = new Scene(root, 500, 500);

    }

   

    public Scene getScene() {
        return scene;
    }

}
