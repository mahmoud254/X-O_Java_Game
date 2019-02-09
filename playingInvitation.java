package HelloWorldFx;

import java.awt.GradientPaint;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class playingInvitation extends Application {
	
	
	public void init() throws Exception {
		
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		boolean l=playInv("ali");
		System.out.println(l);
		
		
	}
	public static boolean playInv(String name) {
		Alert a = new Alert(AlertType.CONFIRMATION, "", ButtonType.OK,ButtonType.CANCEL);
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
	public static void main(String[] args) {
		launch(args);
	}
	

}
