/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class Server_Gui extends Application {

    @Override
    public void start(Stage primaryStage) {

        Button btnStart = new Button("Start");
        Button btnStop = new Button("Stop");
        ListView<String> lvList = new ListView<String>();
        VBox vbButtons = new VBox();
        BorderPane borderPane = new BorderPane();

        btnStart.setMinSize(200, 20);
        btnStop.setMinSize(200, 20);

        btnStart.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //action on button start
            }
        });

        btnStop.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //action on button stop
            }
        });

        vbButtons.setSpacing(10);
        vbButtons.setPadding(new Insets(0, 20, 10, 20));
        vbButtons.getChildren().addAll(btnStart, btnStop);

        ObservableList<String> items = FXCollections.observableArrayList(
                "Player1", "Player2", "Player3",
                "Player4", "player5");
        lvList.setItems(items);
        lvList.setMaxHeight(Control.USE_PREF_SIZE);

        borderPane.setPadding(new Insets(20, 0, 20, 20));
        borderPane.setRight(vbButtons);
        borderPane.setLeft(lvList);

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

}
