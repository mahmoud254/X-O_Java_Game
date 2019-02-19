package View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Bassant Sayed
 */
public class LogIn {

    Scene scene1;
    Image backgroundImage1 = null;
    BackgroundImage background1;
    String cwd;
    public TextField emailField;
    public PasswordField passwordField;
    public Button signInBtn;
    public Button signUpBtn;
    public final Label emailMsg = new Label("");
    public final Label passMsg = new Label("");
    Label emailLabel;
    Label passwordLabel;
    FlowPane signOptions;
    FlowPane logIn;
    BorderPane root;

    public LogIn() {

        cwd = System.getProperty("user.dir");
//        reading images
        try {
            backgroundImage1 = new Image(new FileInputStream(cwd + "\\images\\bg.jpg"));
            background1 = new BackgroundImage(backgroundImage1,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));

        } catch (FileNotFoundException ex) {
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
//        first scene components
        emailField = new TextField();
        emailField.setPromptText("username@example.com");
        passwordField = new PasswordField();
        passwordField.setPromptText("Your password");
        signInBtn = new Button();
        signUpBtn = new Button();
        signInBtn.setText("Sign In");
        signUpBtn.setText("Sign Up");
        signInBtn.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);-fx-font-size:18;-fx-font-family: \"Palatino Linotype\";-fx-background-radius: 30;-fx-background-insets: 0;-fx-text-fill: white;\n"
                + "");
        signUpBtn.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);-fx-font-size:18;-fx-font-family: \"Palatino Linotype\";-fx-background-radius: 30;-fx-background-insets: 0;-fx-text-fill: white;\n"
                + "");
        emailLabel = new Label("Email");
        emailLabel.setFont(new Font(24));
        emailLabel.setTextFill(Color.AQUA);
        passwordLabel = new Label("Password");
        passwordLabel.setFont(new Font(24));
        passwordLabel.setTextFill(Color.AQUA);
        signOptions = new FlowPane();
        logIn = new FlowPane();

        //Setting the vertical and horizontal gaps between the columns 
        logIn.setVgap(10);
        logIn.setHgap(5);
        signOptions.setHgap(5);
        logIn.setOrientation(Orientation.VERTICAL);
        //Setting the Grid alignment 
        logIn.setAlignment(Pos.CENTER);
        signOptions.getChildren().addAll(signInBtn, signUpBtn);
        logIn.getChildren().addAll(emailLabel, emailField,emailMsg, passwordLabel, passwordField,passMsg, signOptions);
        root = new BorderPane();
        root.setCenter(logIn);
        root.setBackground(new Background(background1));
//        Scenes
        scene1 = new Scene(root, 500, 500);

    }

    public Scene getScene() {
        return scene1;
    }

}
