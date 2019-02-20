/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package block_gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author amr
 */
public class Block_GUI extends Application {

    boolean flag = true;

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        Button btn2 = new Button();
        btn.setText(" Button 1 ");
        btn2.setText("Button 2 ");

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (flag) {
                    System.out.println("Button 1");
                    flag = false;
                }
            }
        });
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!flag) {
                    System.out.println("Button 2");
                    flag = true;
                }
            }
        });

        StackPane root = new StackPane();
        HBox hBox = new HBox(btn, btn2);
        // root.getChildren().addAll(btn, btn2);

        Scene scene = new Scene(hBox, 400, 350);

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
