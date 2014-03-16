package fjr.mouse.follower;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MouseFollower extends Application{
	
	double  width = 800;
	double  height = 600; 
	
	MouseFollowerPlugin plugin ; 
	
	public void start(Stage stage) throws Exception {
		Group root = new Group(); 
		stage.setScene(new Scene(root, width, height)); 
		
		plugin = new MouseFollowerPlugin(width, height) ; 
		
		root.getChildren().add(
				plugin); 
//		
//		root.getChildren().add(
//				new VBox(){{
//					setSpacing(5); 
//					setTranslateY(10);
//					setTranslateX(800 - 150);
//					getChildren().add(new Button("PLAY"){{
//						setPrefWidth(100);
//						setOnAction(new EventHandler<ActionEvent>() {										
//							@Override
//							public void handle(ActionEvent arg0) {
////								plugin.play();
//								plugin.mouseRespon();
//							}
//						});
//					}});
//					
//					getChildren().add(new Button("PAUSE"){{
//						setPrefWidth(100);
//						setOnAction(new EventHandler<ActionEvent>() {
//							@Override
//							public void handle(ActionEvent arg0) {	
////								plugin.pause(); 
//							}
//						});
//					}}); 
//				}}
//				); 
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent arg0) {
//				plugin.getThread().stop();
				plugin.stopThread(); 
			}
		});
		stage.show();
	}
	
	
	
	public static void main(String[] args){
		launch(args); 
	}
}
