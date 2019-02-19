package Controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import Models.Message;
import View.LogIn;
import View.MainHome;
import View.PlayLocally;
import View.PlayOnlineIntro;
import View.PlayVsFriend;
import View.PlayWithComputer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.animation.StrokeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Bassant Sayed
 */
public class ClientController extends Application implements Runnable {

    private Socket mySocket;
    private Thread networkHandler;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Message msg;
    private Message connectMsg;
    boolean accept=false;
    private String against;
    private String player;
    public static Stage stage;
    MainHome homePage;
    PlayLocally playLocallyPage;
    LogIn logINPage;
    PlayOnlineIntro playOnlineIntroPage;
    PlayWithComputer playWithCompPage;
    PlayOnline playOnlinePage;
    PlayVsFriend playVsFriendPage;

    public boolean establishConnection() {
        try {
            mySocket = new Socket("127.0.0.1", 5005);
            try {
                out = new ObjectOutputStream(mySocket.getOutputStream());
                in = new ObjectInputStream(mySocket.getInputStream());
            } catch (Exception ex) {
                System.out.println("Error in input stream or print stream");
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
        networkHandler = new Thread(this);
        return true;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage=primaryStage;
        //audio
        Media media = new Media(getClass().getResource("\\main.mp3").toExternalForm()); //replace /Movies/test.mp3 with your file
        MediaPlayer player = new MediaPlayer(media);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.play();
        //initiating scenes
        homePage = new MainHome();
        playLocallyPage = new PlayLocally();
        logINPage = new LogIn();
        playOnlineIntroPage = new PlayOnlineIntro();
        playWithCompPage = new PlayWithComputer();
        playOnlinePage = new PlayOnline();
        playVsFriendPage = new PlayVsFriend();

        //        Event Listeners
        //signin listener
        logINPage.signInBtn.setOnAction((ActionEvent event) -> {
            Message mylogin = new Message();
            if (isValidEmail(logINPage.emailField.getText())) {
                logINPage.emailMsg.setText("");
                int passwordCheck = isValidPassword(logINPage.passwordField.getText());
                if (passwordCheck == 1) {
                    logINPage.passMsg.setText("");
                    mylogin.setType("LogIn");
                    mylogin.getSender().setEmail(logINPage.emailField.getText());
                    mylogin.getSender().setPassword(logINPage.passwordField.getText());
                    Message m;
                    try {
                        out.writeObject(mylogin);
                        m = (Message) in.readObject();
                        if (m.getType().equals("LogIn")) {
                            if (!m.isIsValidUser()) {
                                Alert a = new Alert(Alert.AlertType.WARNING);
                                a.setHeaderText("Invalid Email or password");
                                a.setTitle("Invalid Email");
                                a.setContentText("Please make sure you enter the valid email and password .");
                                a.show();
                            } else {
                                Button x=new Button();
                                x.setOnAction((ActionEvent event2) -> {
                                try {
                                    connectMsg= new Message();
                                    System.out.println("button ");
                                    connectMsg.getSender().setEmail(logINPage.emailField.getText());
                                    connectMsg.setType("PlayRequest");
                                    against=playOnlineIntroPage.opponent;
                                    connectMsg.setReciever(playOnlineIntroPage.opponent);
                                    out.writeObject(connectMsg);
                                    TimeUnit.SECONDS.sleep(10);
                                    accept=true;
                                    if(accept){
                                        primaryStage.setScene(playOnlinePage.getScene());
                                        System.out.println("inside accept");
                                    }
                                    System.out.println("DOne");
                                } catch (IOException ex) {
                                    System.out.println("Error in sending request");
                                }   catch (InterruptedException ex) {
                                        Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                            });
                               playOnlineIntroPage=getUserslist(m.getMsg(),x);
                                primaryStage.setScene(playOnlineIntroPage.getScene());
                                networkHandler.start();
                            }
                        }
                    } catch (IOException ex) {
                        System.out.println("Error sending object");
                    } catch (ClassNotFoundException ex) {
                        System.out.println("Error reading object");
                    }
                } else {
                    if (passwordCheck == 0) {
                        logINPage.passMsg.setText("Password at leaset has to be 8 charchters!");
                    } else {
                        logINPage.passMsg.setText("Password required!");
                    }
                    logINPage.passMsg.setTextFill(Color.rgb(210, 39, 30));
                }
            } else {
                logINPage.emailMsg.setText("Invalid email!");
                logINPage.emailMsg.setTextFill(Color.rgb(210, 39, 30));
            }
        });
        //signup listener
        logINPage.signUpBtn.setOnAction((ActionEvent event) -> {
            Message mySignUp = new Message();
            if (isValidEmail(logINPage.emailField.getText())) {
                logINPage.emailMsg.setText("");
                int passwordCheck = isValidPassword(logINPage.passwordField.getText());
                if (passwordCheck == 1) {
                    logINPage.passMsg.setText("");
                    mySignUp.setType("SignUp");
                    mySignUp.getSender().setEmail(logINPage.emailField.getText());
                    mySignUp.getSender().setPassword(logINPage.passwordField.getText());
                    Message m;
                    try {
                        out.writeObject(mySignUp);
                        connectMsg=mySignUp;
                        m = (Message) in.readObject();
                        if (m.getType().equals("SignUp")) {
                            if (!m.isIsValidUser()) {
                                Alert a = new Alert(Alert.AlertType.WARNING);
                                a.setHeaderText("Invalid Email");
                                a.setTitle("Invalid Email");
                                a.setContentText("Sorry, this email already exist.");
                                a.show();
                            } else {
                                Button x=new Button();
                                x.setOnAction((ActionEvent event2) -> {
                                try {
                                    connectMsg= new Message();
                                    System.out.println("button ");
                                    connectMsg.getSender().setEmail(logINPage.emailField.getText());
                                    connectMsg.setType("PlayRequest");
                                    against=playOnlineIntroPage.opponent;
                                    connectMsg.setReciever(playOnlineIntroPage.opponent);
                                    out.writeObject(connectMsg);
                                    TimeUnit.SECONDS.sleep(10);
                                    if(accept){
                                        primaryStage.setScene(playOnlinePage.getScene());
                                    }
                                    System.out.println("DOne");
                                } catch (IOException ex) {
                                    System.out.println("Error in sending request");
                                }   catch (InterruptedException ex) {
                                        Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                            });
                               playOnlineIntroPage=getUserslist(m.getMsg(),x);
                                primaryStage.setScene(playOnlineIntroPage.getScene());
                                networkHandler.start();
                            }
                        }
                    } catch (IOException ex) {
                        System.out.println("Error sending object");
                    } catch (ClassNotFoundException ex) {
                        System.out.println("Error reading object");
                    }
                } else {
                    if (passwordCheck == 0) {
                        logINPage.passMsg.setText("Password at leaset has to be 8 charchters!");
                    } else {
                        logINPage.passMsg.setText("Password required!");
                    }
                    logINPage.passMsg.setTextFill(Color.rgb(210, 39, 30));
                }
            } else {
                logINPage.emailMsg.setText("Invalid email!");
                logINPage.emailMsg.setTextFill(Color.rgb(210, 39, 30));
            }
        });
        homePage.playOnline.setOnAction((ActionEvent event) -> {
            if (establishConnection()) {
                primaryStage.setScene(logINPage.getScene());
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Error information");
                a.setTitle("Server Time out");
                a.setContentText("Sorry, can't connect to the server right now plaese try again later");
                a.show();
            }

        });
        homePage.playOffline.setOnAction((ActionEvent event) -> {
            primaryStage.setScene(playLocallyPage.getScene());
        });
        playLocallyPage.playVsComputerBtn.setOnAction((ActionEvent event) -> {
            primaryStage.setScene(playWithCompPage.getScene());
            playWithCompPage.level=playLocallyPage.level;
            playWithCompPage.goToHome.setOnAction((ActionEvent event2) -> {
                primaryStage.setScene(homePage.getScene());
                playWithCompPage = new PlayWithComputer();
            });            
        });
        playLocallyPage.playVsFriendBtn.setOnAction((ActionEvent event) -> {
            primaryStage.setScene(playVsFriendPage.getScene());
            playVsFriendPage.goToHome.setOnAction((ActionEvent event2) -> {
                primaryStage.setScene(homePage.getScene());
                playVsFriendPage = new PlayVsFriend();
            });
        });
        

        playOnlinePage.sentMessage.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                Message m = new Message();
                m.setType("Chat");
                m.getSender().setEmail("bs@gmail.com");
                m.setReciever(against);
                System.out.println("reciever"+against);
                try {
                    m.setMsg(playOnlinePage.sentMessage.getText());
                    out.writeObject(m);
                    playOnlinePage.sentMessage.setText("");
                } catch (IOException ex) {
                    System.out.println("error in sending chat");
                }

            }
        });
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(homePage.getScene());
        primaryStage.show();
    }
    public static boolean playInv(String name) {
		Alert a = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK,ButtonType.CANCEL);
		Image image = new Image("https://image.flaticon.com/icons/png/128/181/181515.png");
		ImageView imageView = new ImageView(image);
		a.setGraphic(imageView);
		 a.setTitle("Invitation Message");
	     a.setHeaderText(name+" invites you to play");
	     a.setResizable(true);
	     Optional<ButtonType> result = a.showAndWait();
	     if(result.get().getText().equals("OK")) {
	    	 return true;
	     }
	     else {
	    	 return false;
	     }
			
	}

    @Override
    public void run() {
        System.out.println("inside run");
        while (true) {
            try {
                msg = (Message) in.readObject();
                System.out.println(msg.getType());
               
                if (msg.getType().equals("PlayRequest")) {
                    
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            accept=playInv(msg.getSender().getEmail());
                            System.out.println("accept state "+accept);
                            if(accept){
                            stage.setScene(playOnlinePage.getScene());
                            }
                        }
                    });
                    String re=msg.getSender().getEmail();
                    msg.getSender().setEmail(msg.getReciever());
                    msg.setReciever(re);
                    TimeUnit.SECONDS.sleep(10);
                    if(accept){
                        msg.setType("AcceptRequest");
                    }else{
                        msg.setType("RefuseRequest");
                    }
                    out.writeObject(msg);
                    System.out.println("sending answer");
                }
                if (msg.getType().equals("AcceptRequest")) {
                    
                    accept=true;
                }
                if (msg.getType().equals("Chat")) {
                    System.out.println("msg accepted"+msg.getMsg());
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                           playOnlinePage.sentMessage.setText(msg.getMsg());
                        }
                    });
                }
                
                System.out.println("thread is working");
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("error in recieving");
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public boolean isValidEmail(String email) {
        Pattern valid_mail = Pattern.compile("^[A-Za-z]+[_A-Za-z0-9]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z]+(\\.[A-Za-z]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = valid_mail.matcher(email);
        return matcher.find();
    }

    public int isValidPassword(String password) {
        if (password.equals("")) {
            return -1;
        }
        if (password.length() < 8) {
            return 0;
        }
        return 1;
    }
    PlayOnlineIntro getUserslist(String users,Button x){
        System.out.println(users);
        String[] types= users.split("\\|");
        if(types.length>0){
        if(types.length>0){
            String [] Empty_str = types[0].split(",");
               if(Empty_str[0].length() != 0 ){
                   playOnlineIntroPage.onlineUsers=new ArrayList<>(Arrays.asList(types[0].split(",")));
                   System.out.println("online"+types[0].split(","));
               }
        }
        if(types.length>1){
            String [] Empty_str = types[1].split(",");
               if(Empty_str[0].length() != 0 ){
                   playOnlineIntroPage.offlineUsers=new ArrayList<>(Arrays.asList(types[1].split(",")));
               }        
        }}
        return new PlayOnlineIntro(playOnlineIntroPage.onlineUsers,playOnlineIntroPage.offlineUsers,x);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
class PlayOnline {

    Vector<StackPane> boxVector = new Vector<StackPane>();
    Vector<Integer> xFields = new Vector<Integer>();
    Vector<Integer> oFields = new Vector<Integer>();
    Scene scene3;
    Image user_image = null;
    Image user_image2 = null;
    Image backgroundImage3 = null;
    BackgroundImage background3;
    Button playVsFriendBtn;
    Color c = Color.rgb(153, 51, 255, 0.5);
    Color c2 = Color.rgb(255, 255, 204, 1);
    Color c3 = Color.rgb(255, 51, 153, 1);
    Color c4 = Color.rgb(255, 0, 0, 1);
    String cwd;
    int fieldNum = 0;
    String insertedType;
    String[] screenArr;
    boolean GameOver = false;
    public TextArea recievedMessage;
    Text winner;
    BorderPane root;
    public Button goToHome;
    String lineOver;
    boolean noWinner = false;
    public TextField sentMessage;

    public PlayOnline() {

        cwd = System.getProperty("user.dir");
//        reading images
        try {
            backgroundImage3 = new Image(new FileInputStream(cwd + "\\images\\bg3.jpg"));
            background3 = new BackgroundImage(backgroundImage3,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PlayOnline.class.getName()).log(Level.SEVERE, null, ex);
        }

//      third scene
        try {
            user_image = new Image(new FileInputStream(cwd + "\\images\\A.png"),
                    100, 100, false, false);
            user_image2 = new Image(new FileInputStream(cwd + "\\images\\B.png"),
                    100, 100, false, false);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PlayOnline.class.getName()).log(Level.SEVERE, null, ex);
        }
        GridPane GameArea = new GridPane();
        fieldNum = 0;
        screenArr = new String[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int num = fieldNum;
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
                boxVector.add(box);
                box.setOnMouseClicked((MouseEvent event) -> {
                    boolean allowClick = true;
                    for (int cf : xFields) {
                        if (cf == num) {
                            allowClick = false;
                        }
                    }
                    for (int cf : oFields) {
                        if (cf == num) {
                            allowClick = false;
                        }
                    }

                    if (GameOver == false && allowClick == true) {
                        if (insertedType == null) {
                            insertedType = "x";
                            xFields.add(num);
                        } else if (insertedType == "x") {
                            insertedType = "o";
                            oFields.add(num);
                        } else {
                            insertedType = "x";
                            xFields.add(num);
                        }
                        box_filler(box, num);
                    }
                });
                GameArea.add(box, j, i);
                fieldNum += 1;
            }
        }
        GameArea.setAlignment(Pos.CENTER);
        GameArea.setHgap(10);
        GameArea.setVgap(10);
        //        Chat Area
        TitledPane chatPane = new TitledPane();
        chatPane.setText("Chat");
        sentMessage = new TextField();
        recievedMessage = new TextArea();
        FlowPane chatItems = new FlowPane();
        chatItems.getChildren().addAll(sentMessage, recievedMessage);
        chatItems.setOrientation(Orientation.VERTICAL);
        chatPane.setContent(chatItems);
        //        players Data Area 
        FlowPane playersArea = new FlowPane();
        FlowPane topPane = new FlowPane();
        ImageView user1Icon = new ImageView(user_image);
        ImageView user2Icon = new ImageView(user_image2);
        topPane.getChildren().addAll(user1Icon, user2Icon);
        topPane.setHgap(300);
        goToHome = new Button("Go to Home");
        BorderPane root3 = new BorderPane();
        root3.setCenter(GameArea);
        root3.setBottom(chatPane);
        root3.setTop(topPane);
        root3.setBackground(new Background(background3));

        scene3 = new Scene(root3, 500, 500);
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
                this.GameOver = true;
                lineOver = "row" + r;
            };
            if (myCount(arrElement, "o") == 3) {
                this.GameOver = true;
                lineOver = "row" + r;
            };

            r += 1;
        }
        r = 1;
        for (String[] arrElement : colsArr) {
            if (myCount(arrElement, "x") == 3) {
                this.GameOver = true;
                lineOver = "col" + r;
            };
            if (myCount(arrElement, "o") == 3) {

                this.GameOver = true;
                lineOver = "col" + r;

            };

            r += 1;
        }
        r = 1;
        for (String[] arrElement : crossesArr) {
            if (myCount(arrElement, "x") == 3) {

                this.GameOver = true;
                lineOver = "cross" + r;

            };
            if (myCount(arrElement, "o") == 3) {

                this.GameOver = true;
                lineOver = "cross" + r;
            };

            r += 1;
        }
    }

    public void box_filler(StackPane box, int num) {

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
        gameRules(screenArr);
        if (GameOver == false && myCount(screenArr, "x") + myCount(screenArr, "o") == 9) {
            GameOver = true;
            noWinner = true;
        }
        if (GameOver == true) {
            if (noWinner == false) {
                drawLineOver();
                winner = new Text(insertedType + " wins");
            } else {
                winner = new Text("No Winner");
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

    public Scene getScene() {
        return scene3;
    }

}

