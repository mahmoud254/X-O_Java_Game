package servertictactoe;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


/**
 *
 * @author Bassant Sayed
 */
public class ServerView {
    private String cwd;
    Button btnStart ;
    Button btnStop ;
    private ListView<Label> lvList;
    private VBox vbButtons ;
    private BorderPane borderPane;
    public ServerView() {
        cwd = System.getProperty("user.dir");
        btnStart = new Button("Start");
        btnStop = new Button("Stop");
        lvList = new ListView<>();
        vbButtons = new VBox();
        borderPane = new BorderPane();

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
            
        }
        
        lvList.getItems().addAll(p1);
        lvList.setMaxHeight(Control.USE_PREF_SIZE);

        borderPane.setPadding(new Insets(20, 0, 20, 20));
        borderPane.setRight(vbButtons);
        borderPane.setLeft(lvList);
    }
    
    public Scene getScene(){
        return new Scene(borderPane, 500, 400);
    }
}
