//
//package View;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.util.Vector;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javafx.animation.StrokeTransition;
//import javafx.geometry.Orientation;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
//import javafx.scene.control.TitledPane;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BackgroundImage;
//import javafx.scene.layout.BackgroundPosition;
//import javafx.scene.layout.BackgroundRepeat;
//import javafx.scene.layout.BackgroundSize;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.FlowPane;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.StackPane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Line;
//import javafx.scene.shape.Rectangle;
//import javafx.util.Duration;
//
///**
// *
// * @author Dell
// */
////The online game
//public class PlayOnline {
//   Vector<StackPane> boxVector = new Vector<StackPane>();
//    Scene scene3;
//    Image user_image = null;
//    Image user_image2 = null;
//    Image backgroundImage3 = null;
//    BackgroundImage background3;
//    Button playVsFriendBtn;
//    Color c = Color.rgb(153, 51, 255, 0.5);
//    Color c2 = Color.rgb(255, 255, 204, 1);
//    Color c3 = Color.rgb(255, 51, 153, 1);
//    String cwd;
//    int fieldNum = 0;
//    String insertedType;
//    String[] screenArr;
//    boolean GameOver = false;
//    public TextArea recievedMessage;
//    public TextField sentMessage;
//    public PlayOnline() {
//      
//        cwd = System.getProperty("user.dir");
////        reading images
//        try {
//            backgroundImage3 = new Image(new FileInputStream(cwd + "\\images\\bg3.jpg"));
//            background3 = new BackgroundImage(backgroundImage3,
//                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
//                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(PlayOnline.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
////      third scene
//
//        try {
//            user_image = new Image(new FileInputStream(cwd + "\\images\\A.png"),
//                    100, 100, false, false);
//            user_image2 = new Image(new FileInputStream(cwd + "\\images\\B.png"),
//                    100, 100, false, false);
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(PlayOnline.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        GridPane GameArea = new GridPane();
//        screenArr = new String[9];
//        insertedType = "x";
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                int num = fieldNum;
//                StackPane box = new StackPane();
//                Rectangle rect = new Rectangle();
//                rect.setX(0f);
//                rect.setY(0f);
//                rect.setArcWidth(30.0);
//                rect.setArcHeight(20.0);
//                rect.setWidth(100.0f);
//                rect.setHeight(100.0f);
//                rect.setStrokeWidth(5.0);
//                rect.setStroke(c2);
//                rect.setFill(c);
//                box.getChildren().add(rect);
//                boxVector.add(box);
//                box.setOnMouseClicked((MouseEvent event) -> {
//                    box_filler(box, num);
//                });
//                GameArea.addColumn(i, box);
//                fieldNum += 1;
//            }
//        }
//        GameArea.setAlignment(Pos.CENTER);
//        GameArea.setHgap(10);
//        GameArea.setVgap(10);
//        //        Chat Area
//        TitledPane chatPane = new TitledPane();
//        chatPane.setText("Chat");
//        sentMessage = new TextField();
//        recievedMessage = new TextArea();
//        FlowPane chatItems = new FlowPane();
//        chatItems.getChildren().addAll(sentMessage, recievedMessage);
//        chatItems.setOrientation(Orientation.VERTICAL);
//        chatPane.setContent(chatItems);
//        //        players Data Area 
//        FlowPane playersArea = new FlowPane();
//        FlowPane topPane = new FlowPane();
//        ImageView user1Icon = new ImageView(user_image);
//        ImageView user2Icon = new ImageView(user_image2);
//        topPane.getChildren().addAll(user1Icon, user2Icon);
//        topPane.setHgap(300);
//        BorderPane root3 = new BorderPane();
//        root3.setCenter(GameArea);
//        root3.setBottom(chatPane);
//        root3.setTop(topPane);
//        root3.setBackground(new Background(background3));
//
//
//
//        scene3 = new Scene(root3, 500, 500);
//    }
//
// public int myCount(String[] arr, String item) {
//        int count = 0;
//        for (String element : arr) {
//            if (item == element) {
//                count += 1;
//            }
//        }
//        return count;
//    }
//
//    public void gameRules(String[] screenArr) {
//        String[][] rowsArr = new String[3][3];
//        String[][] colsArr = new String[3][3];
//        String[][] crossesArr = new String[2][3];
//        String[] crossArr1 = new String[3];
//        String[] crossArr2 = new String[3];
//        for (int i = 0; i < 3; i++) {
//            String[] rowArr = new String[3];
//            String[] colArr = new String[3];
//            crossArr1[i] = screenArr[i * 4];
//            crossArr2[i] = screenArr[i * 2 + 2];
//            for (int j = 0; j < 3; j++) {
//                rowArr[j] = screenArr[i * 3 + j];
//                colArr[j] = screenArr[i + j * 3];
//            }
//            rowsArr[i] = rowArr;
//            colsArr[i] = colArr;
//        }
//        crossesArr[0] = crossArr1;
//        crossesArr[1] = crossArr2;
//        for (String[] arrElement : rowsArr) {
//            if (myCount(arrElement, "x") == 3) {
//                this.GameOver = true;
//            };
//            if (myCount(arrElement, "o") == 3) {
//                this.GameOver = true;
//            };
//        }
//        for (String[] arrElement : colsArr) {
//            if (myCount(arrElement, "x") == 3) {
//                this.GameOver = true;
//            };
//            if (myCount(arrElement, "o") == 3) {
//                this.GameOver = true;
//            };
//        }
//        for (String[] arrElement : crossesArr) {
//            if (myCount(arrElement, "x") == 3) {
//                this.GameOver = true;
//            };
//            if (myCount(arrElement, "o") == 3) {
//                this.GameOver = true;
//            };
//        }
//    }
//    public void box_filler(StackPane box, int num) {
//        screenArr[num] = insertedType;
//        gameRules(screenArr);
//        Line x1 = new Line(10, 10, 90, 90);
//        Line x2 = new Line(10, 90, 90, 10);
//        x1.setStrokeWidth(5.0);
//        x2.setStrokeWidth(5.0);
//        StrokeTransition st1 = new StrokeTransition();
//        StrokeTransition st2 = new StrokeTransition();
//        st1.setDuration(Duration.millis(1000));
//        st1.setShape(x1);
//        st1.setFromValue(Color.TRANSPARENT);
//        st1.setToValue(c3);
//        st1.play();
//        st2.setDuration(Duration.millis(1000));
//        st2.setShape(x2);
//        st2.setFromValue(Color.TRANSPARENT);
//        st2.setToValue(c3);
//        st2.play();
//        box.getChildren().addAll(x1, x2);
//    }
//
//    public Scene getScene() {
//        return scene3;
//    }
//
//    }
//    
//    
