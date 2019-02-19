/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ClientController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.StrokeTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Dell
 */
public class PlayWithComputer {

    Vector<StackPane> boxVector = new Vector<StackPane>();
    Vector<Integer> computerFields = new Vector<Integer>();
    Vector<Integer> userFields = new Vector<Integer>();
    Scene scene;
    Image user_image = null;
    Image computer_image = null;
    Image backgroundImage = null;
    BackgroundImage background;
    Color c = Color.rgb(153, 51, 255, 0.5);
    Color c2 = Color.rgb(255, 255, 204, 1);
    Color c3 = Color.rgb(255, 51, 153, 1);
    Color c4 = Color.rgb(255, 0, 0, 1);
    String cwd;
    int fieldNum = 0;
    String insertedType;
    String[] screenArr;
    public boolean GameOver = false;
    TextArea recievedMessage;
    BorderPane root;
    Text winner;
    public Button goToHome;
    boolean test = false;
    boolean testGameOver = false;
    public String level;
    String lineOver;

    public PlayWithComputer() {

        cwd = System.getProperty("user.dir");
//        reading images
        try {
            backgroundImage = new Image(new FileInputStream(cwd + "\\images\\bg7.jpg"));
            background = new BackgroundImage(backgroundImage,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PlayWithComputer.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            user_image = new Image(new FileInputStream(cwd + "\\images\\A.png"),
                    100, 100, false, false);
            computer_image = new Image(new FileInputStream(cwd + "\\images\\computer.png"),
                    100, 100, false, false);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PlayWithComputer.class.getName()).log(Level.SEVERE, null, ex);
        }

//        fourth scene
        GridPane GameArea = new GridPane();
        screenArr = new String[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                StackPane box = new StackPane();
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
                GameArea.add(box, j, i);
                boxVector.add(box);
            }
        }

        for (int i = 0; i < 9; i++) {
            int num = i;
            boxVector.get(i).setOnMouseClicked((MouseEvent event) -> {
                boolean allowClick = true;
                if (GameOver == true) {
                    allowClick = false;
                }
                for (int cf : computerFields) {
                    if (cf == num) {
                        allowClick = false;
                    }
                }
                for (int cf : userFields) {
                    if (cf == num) {
                        allowClick = false;
                    }
                }
                System.out.println(allowClick);
                if (allowClick == true) {

                    insertedType = "x";
                    userFields.add(num);
                    box_filler(boxVector.get(num), num);
                    if (GameOver == false) {
                        insertedType = "o";
                        int pos;
                        if (level == "easy") {
                            pos = ourAI(screenArr);
                        } else {
                            pos = ourAIHard(screenArr);
                        }
                        System.out.println(pos);
                        box_filler(boxVector.get(pos), pos);
                    }

                }

            });
        }

        GameArea.setAlignment(Pos.CENTER);
        GameArea.setHgap(10);
        GameArea.setVgap(10);
        FlowPane topPane = new FlowPane();
        ImageView userIcon = new ImageView(user_image);
        ImageView computerIcon = new ImageView(computer_image);
        topPane.getChildren().addAll(userIcon, computerIcon);
        topPane.setHgap(300);
        goToHome = new Button("Go to Home");
        root = new BorderPane();
        root.setCenter(GameArea);
        root.setTop(topPane);
        root.setBackground(new Background(background));

        scene = new Scene(root, 500, 500);

    }

    public int myCount(String[] arr, String item) {
        int count = 0;
        for (String element : arr) {
            if (item == element) {
                count += 1;
            }
        }
        return count;
    }

        public void drawLineOver() {
        System.out.println(lineOver);
        Line line1;
        Line line2;
        Line line3;
        if (lineOver.equals("cross1")) {
            line1 = new Line(10, 10, 90, 90);
            line2 = new Line(10, 10, 90, 90);
            line3 = new Line(10, 10, 90, 90);
            boxVector.get(0).getChildren().add(line1);
            boxVector.get(4).getChildren().add(line2);
            boxVector.get(8).getChildren().add(line3);

        } else if (lineOver.equals("cross2")) {
            line1 = new Line(90, 10, 10, 90);
            line2 = new Line(90, 10, 10, 90);
            line3 = new Line(90, 10, 10, 90);
            boxVector.get(2).getChildren().add(line1);
            boxVector.get(4).getChildren().add(line2);
            boxVector.get(6).getChildren().add(line3);
        } else if (lineOver.equals("row1")) {
            line1 = new Line(10, 10, 90, 10);
            line2 = new Line(10, 10, 90, 10);
            line3 = new Line(10, 10, 90, 10);
            boxVector.get(0).getChildren().add(line1);
            boxVector.get(1).getChildren().add(line2);
            boxVector.get(2).getChildren().add(line3);
        } else if (lineOver.equals("row2")) {
            line1 = new Line(10, 10, 90, 10);
            line2 = new Line(10, 10, 90, 10);
            line3 = new Line(10, 10, 90, 10);
            boxVector.get(3).getChildren().add(line1);
            boxVector.get(4).getChildren().add(line2);
            boxVector.get(5).getChildren().add(line3);
        } else if (lineOver.equals("row3")) {
            line1 = new Line(10, 10, 90, 10);
            line2 = new Line(10, 10, 90, 10);
            line3 = new Line(10, 10, 90, 10);
            boxVector.get(6).getChildren().add(line1);
            boxVector.get(7).getChildren().add(line2);
            boxVector.get(8).getChildren().add(line3);
        } else if (lineOver.equals("col1")) {
            line1 = new Line(10, 10, 10, 90);
            line2 = new Line(10, 10, 10, 90);
            line3 = new Line(10, 10, 10, 90);
            boxVector.get(0).getChildren().add(line1);
            boxVector.get(3).getChildren().add(line2);
            boxVector.get(6).getChildren().add(line3);
        } else if (lineOver.equals("col2")) {
            line1 = new Line(10, 10, 10, 90);
            line2 = new Line(10, 10, 10, 90);
            line3 = new Line(10, 10, 10, 90);
            boxVector.get(1).getChildren().add(line1);
            boxVector.get(4).getChildren().add(line2);
            boxVector.get(7).getChildren().add(line3);
        } else {
            line1 = new Line(10, 10, 10, 90);
            line2 = new Line(10, 10, 10, 90);
            line3 = new Line(10, 10, 10, 90);
            boxVector.get(2).getChildren().add(line1);
            boxVector.get(5).getChildren().add(line2);
            boxVector.get(8).getChildren().add(line3);
        }
        line1.setStroke(c4);
        line1.setStrokeWidth(5.0);
        line2.setStroke(c4);
        line2.setStrokeWidth(5.0);
        line3.setStroke(c4);
        line3.setStrokeWidth(5.0);
    }
    public void gameRules(String[] screenArr) {
        String[][] rowsArr = new String[3][3];
        String[][] colsArr = new String[3][3];
        String[][] crossesArr = new String[2][3];
        String[] crossArr1 = new String[3];
        String[] crossArr2 = new String[3];
        for (int i = 0; i < 3; i++) {
            String[] rowArr = new String[3];
            String[] colArr = new String[3];
            crossArr1[i] = screenArr[i * 4];
            crossArr2[i] = screenArr[i * 2 + 2];
            for (int j = 0; j < 3; j++) {
                rowArr[j] = screenArr[i * 3 + j];
                colArr[j] = screenArr[i + j * 3];
            }
            rowsArr[i] = rowArr;
            colsArr[i] = colArr;
        }
        crossesArr[0] = crossArr1;
        crossesArr[1] = crossArr2;
        int r = 1;
        for (String[] arrElement : rowsArr) {

            if (myCount(arrElement, "x") == 3) {

                if (test == false) {
                    System.out.println("hi 1");
                    this.GameOver = true;
                    lineOver = "row" + r;
                } else {
                    testGameOver = true;
                }

            };
            if (myCount(arrElement, "o") == 3) {

                if (test == false) {
                    System.out.println("hi 2");
                    this.GameOver = true;
                    lineOver = "row" + r;
                } else {
                    testGameOver = true;
                }
            };

            r += 1;
        }
        r = 1;
        for (String[] arrElement : colsArr) {
            if (myCount(arrElement, "x") == 3) {

                if (test == false) {
                    System.out.println("hi 3");
                    this.GameOver = true;
                    lineOver = "col" + r;
                } else {
                    testGameOver = true;
                }
            };
            if (myCount(arrElement, "o") == 3) {

                if (test == false) {
                    System.out.println("hi 4");
                    this.GameOver = true;
                    lineOver = "col" + r;
                } else {
                    testGameOver = true;
                }
            };

            r += 1;
        }
        r = 1;
        for (String[] arrElement : crossesArr) {
            if (myCount(arrElement, "x") == 3) {

                if (test == false) {
                    System.out.println("hi 5");
                    this.GameOver = true;
                    lineOver = "cross" + r;
                } else {
                    testGameOver = true;
                }
            };
            if (myCount(arrElement, "o") == 3) {

                if (test == false) {
                    System.out.println("hi 6");
                    this.GameOver = true;
                    lineOver = "cross" + r;
                } else {
                    testGameOver = true;
                }
            };

            r += 1;
        }
    }

    public void box_filler(StackPane box, int num) {
        if (insertedType != "n") {
            test = false;
            screenArr[num] = insertedType;
            if (insertedType == "x") {
                Line x1 = new Line(10, 10, 90, 90);
                Line x2 = new Line(10, 90, 90, 10);
                x1.setStrokeWidth(5.0);
                x2.setStrokeWidth(5.0);
                StrokeTransition st1 = new StrokeTransition();
                StrokeTransition st2 = new StrokeTransition();
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
                box.getChildren().addAll(x1, x2);
            } else {
                Circle oCircle = new Circle(40);
                oCircle.setStrokeWidth(5.0);
                oCircle.setFill(c);
                StrokeTransition st1 = new StrokeTransition();
                StrokeTransition st2 = new StrokeTransition();
                st1.setDuration(Duration.millis(1000));
                st1.setShape(oCircle);
                st1.setFromValue(Color.TRANSPARENT);
                st1.setToValue(c3);
                st1.play();
                box.getChildren().addAll(oCircle);
            }
        }
        gameRules(screenArr);

        if (GameOver == true) { 
            if (insertedType == "o") {
                drawLineOver();
                winner = new Text("Game Over");
            } else if (insertedType == "x") {
                drawLineOver();
                winner = new Text("You are the winner");
            } else {
                winner = new Text("No winner");
            }
            final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            winner.setFont(new Font(60));
                            winner.setFill(Color.AQUAMARINE);
                            root.setCenter(winner);
                            root.setTop(goToHome);
                        }
                    });
                }
            }, 2, 1, TimeUnit.SECONDS);
        }
    }

    public int ourAI(String[] screenArr) {
        test = true;
        String[] PropabilityArr = new String[9];
        PropabilityArr = copy(screenArr);
        Vector<Integer> allowedMoves = new Vector<Integer>();
        for (int i = 0; i < 9; i++) {
            if (PropabilityArr[i] == null) {
                PropabilityArr[i] = "o";
                testGameOver = false;
                gameRules(PropabilityArr);
                if (testGameOver == true) {
                    computerFields.add(i);
                    return i;
                } else {

                    allowedMoves.add(i);
                }
                PropabilityArr = copy(screenArr);

            }
        }
        try {
            computerFields.add(allowedMoves.get(0));
            return allowedMoves.get(0);
        } catch (ArrayIndexOutOfBoundsException ex) {
            insertedType = "n";
            GameOver = true;
            return 0;
        }

    }

    public int ourAIHard(String[] screenArr) {
        test = true;
        String[] PropabilityArr = new String[9];
        PropabilityArr = copy(screenArr);
        Vector<Integer> allowedMoves = new Vector<Integer>();
        for (int i = 0; i < 9; i++) {
            if (PropabilityArr[i] == null) {
                PropabilityArr[i] = "o";
                testGameOver = false;
                gameRules(PropabilityArr);
                if (testGameOver == true) {
                    computerFields.add(i);
                    return i;
                } else {

                    allowedMoves.add(i);
                }
                PropabilityArr = copy(screenArr);
            }
            if (PropabilityArr[i] == null) {
                PropabilityArr[i] = "x";
                testGameOver = false;
                gameRules(PropabilityArr);
                if (testGameOver == true) {
                    computerFields.add(i);
                    return i;
                }
                PropabilityArr = copy(screenArr);
            }
        }
        try {
            computerFields.add(allowedMoves.get(0));
            return allowedMoves.get(0);
        } catch (ArrayIndexOutOfBoundsException ex) {
            insertedType = "n";
            GameOver = true;
            return 0;
        }

    }

    public String[] copy(String[] arr) {
        String[] copiedArr = new String[9];
        int i = 0;
        for (String ch : arr) {
            copiedArr[i] = ch;
            i++;
        }
        return copiedArr;
    }

    public Scene getScene() {
        return scene;
    }

}
