/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Batch2;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author amr
 */
public class Batch2 extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    Robot robot = new Robot();
                   // Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                    Rectangle rectangle = new Rectangle(10,10,1400,700);
                    robot.createScreenCapture(rectangle);
                    BufferedImage image = robot.createScreenCapture(rectangle);
                    Image myImage = SwingFXUtils.toFXImage(image, null);
                    ImageIO.write(image, "jpg", new File("out.jpg"));
                    
                } catch (Exception ex) {
                    Logger.getLogger(Batch2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 1400, 700);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
