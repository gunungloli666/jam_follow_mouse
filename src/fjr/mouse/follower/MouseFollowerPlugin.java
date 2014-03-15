package fjr.mouse.follower;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MouseFollowerPlugin extends StackPane{
	double width; 
	double height; 	
	double radius = 90. ;
	
	SimpleDateFormat format = new SimpleDateFormat(
			"EEEEEEEE:d:MMMMMMMM:yyyy:hh:mm:ss", new Locale("in", "ID"));

	Group root; 
	
	ArrayList<Text> textDate = new ArrayList<>(); 
	ArrayList<Text> textMonth = new ArrayList<>(); 
	ArrayList<Text> textYear = new ArrayList<>(); 
	ArrayList<Text> day = new ArrayList<>();
	
	ArrayList<Text> textHours = new ArrayList<>();
	ArrayList<Text> textMinute = new ArrayList<>(); 
	ArrayList<Text> textSecond = new ArrayList<>(); 
	
	ArrayList<Text> combineDate = new ArrayList<>(); 
	
	Timeline animasi; 
	
	double animasiAngle= 0; 
	
	double offsetX; 
	double offsetY; 
	String[] date ; 
	
	final double TWO_PI = Math.PI * 2;

	public MouseFollowerPlugin(double width, double height){
		this.width = width; 
		this.height = height; 
		
		offsetX = width/ 2.0;
		offsetY = height/2.0; 
		
		root = new Group(); 
		
		setPrefSize(width, height);
		
		date = getDate(); 
		
		animasi = new Timeline();
		animasi.getKeyFrames().addAll(
				new KeyFrame(Duration.seconds(1),
						new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent arg0) {
								animasiAngle -=  Math.PI/30;
								setTextPosition(animasiAngle);
								moveClock();
							}
						}));		
		
		animasi.setAutoReverse(false); 
		animasi.setCycleCount(Animation.INDEFINITE);
		
		addEventHandler(MouseEvent.MOUSE_MOVED , new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent evt) {
				mouseRespon(evt.getX(), evt.getY()); 
			}
		});
		
		initDateList();
		setTextPosition(0);
		initClockList();
		setInitClockPotition();
		getChildren().add(root); 
		addClockLabel();
		play();
	}
	
	public void mouseRespon(double x, double y){
	}
	
	
	public void play(){
		initState();
		animasi.play();
	}
	
	public void pause(){
		animasi.pause(); 
	}
	
	public void initState(){
		date = getDate(); 
		initDateList();
		setTextPosition(0);
		setInitClockPotition();
	}
	
	public void initDateList(){
		root.getChildren().removeAll(combineDate);
		combineDate.clear();
		for(String s : date[0].split("")){
			Text t = new Text(s);
			combineDate.add(t); 
		}
		for(int i=0; i< 1; i++){
			Text t = new Text(" ");
			combineDate.add(t); 
		}
		for(String s : date[1].split("")){
			Text t = new Text(s);
			combineDate.add(t); 
		}
		for(int i=0; i< 1; i++){
			Text t = new Text(" ");
			combineDate.add(t); 
		}
		for(String s : date[2].split("")){
			Text t = new Text(s);
			combineDate.add(t); 
		}
		for(int i=0; i< 1; i++){
			Text t = new Text(" ");
			combineDate.add(t); 
		}
		for(String s : date[3].split("")){
			Text t = new Text(s);
			combineDate.add(t); 
		}
		for(int i=0; i< 1; i++){
			Text t = new Text(" ");
			combineDate.add(t); 
		}
		
		root.getChildren().addAll(combineDate);
	}
	
	public void setTextPosition(double initAngle){
		int size = combineDate.size(); 
		double x0 = offsetX; 
		double y0 = offsetY; 
		double angleStep = (2 * Math.PI)/size; 
		double angle = initAngle; 
		for(Text t : combineDate){
			double x = x0 + radius * Math.cos(angle); 
			double y = y0 + radius * Math.sin(angle); 
			t.setTranslateX(x);
			t.setTranslateY(y);
			angle -= angleStep;
		}
	}
	
	public void initClockList(){
		for(int i = 0; i < 13; i++){
			Text text  =  new Text(".");
			text.setFont(Font.font(null, FontWeight.BOLD, 14));
			textSecond.add(text); 
		}
		for(int i = 0; i < 10; i++){
			Text text = new Text(".");
			text.setFont(Font.font(null, FontWeight.BOLD, 14));
			textMinute.add(text); 
		}
		for(int i = 0; i < 7; i++){
			Text text = new Text(".");
			text.setFont(Font.font(null, FontWeight.BOLD, 14));
			textHours.add(text); 
		}
		
		root.getChildren().addAll(textHours); 
		root.getChildren().addAll(textMinute); 
		root.getChildren().addAll(textSecond); 
	}

	double secondAngle; 
	double minuteAngle; 
	double hoursAngle; 

	ArrayList<Text> listLabel = new ArrayList<>(); 
	
	public void addClockLabel(){
		double radius = this.radius - 22;
		double step = TWO_PI / 12.; 
		double angle0 = - Math.PI/ 2 + step ; 
		for(int i=1; i<=12; i++){
			Text t  = new Text(Integer.toString(i)); 
			t.setTranslateX(offsetX + radius * Math.cos( angle0));
			t.setTranslateY(offsetY + radius * Math.sin(angle0)); 
			angle0 += step; 
			listLabel.add(t); 
		}
		
		root.getChildren().addAll(listLabel);
	}
	
	public void setInitClockPotition(){
		String hour = date[4]; 
		String minutes = date[5];
		String seconds = date[6]; 
		int hourValue = Integer.valueOf(hour); 
		int minuteValue = Integer.valueOf(minutes); 
		int secondValue  = Integer.valueOf(seconds);
		
		double secondsAngleStep = TWO_PI/60.; 
		double minuteAngleStep = TWO_PI / 60.0; 
		double hoursAngleStep = TWO_PI / 12.; 
		
		secondAngle =-  Math.PI / 2. + secondValue * secondsAngleStep ;
		minuteAngle = -  Math.PI / 2. + minuteValue * minuteAngleStep ;
		hoursAngle = -  Math.PI / 2. + hourValue * hoursAngleStep ;
		
		setClockPosition(secondAngle, minuteAngle, hoursAngle);
	}
	
	
	public void moveClock(){
		secondAngle += TWO_PI/60.; 
		minuteAngle += TWO_PI/60./60.; 
		hoursAngle += TWO_PI /60./60.; 
		setClockPosition(secondAngle, minuteAngle, hoursAngle);
	}
	
	public void setClockPosition(double secondAngle, double minuteAngle, double hoursAngle){
		double radius  = this.radius - 30; 
		double secondsRadiusStep  = (radius-5 ) / textSecond.size(); 
		double secondsRadius = 0; 
		for(Text t : textSecond){
			t.setTranslateX(offsetX + secondsRadius * Math.cos(secondAngle));
			t.setTranslateY(offsetY + secondsRadius * Math.sin(secondAngle));
			secondsRadius += secondsRadiusStep; 
		}
		
		double  minuteRadiusStep =(radius - 10 ) / textMinute.size(); 
		double minuteRadius = 0; 
		for(Text t : textMinute ){
			t.setTranslateX(offsetX + minuteRadius * Math.cos(minuteAngle));
			t.setTranslateY(offsetY+ minuteRadius * Math.sin(minuteAngle));
			minuteRadius += minuteRadiusStep; 
		}
		
		double hoursRadiusStep = (radius - 10 ) / textHours.size();
		double hoursRadius = 0; 
		for( Text t : textHours){
			t.setTranslateX(offsetX + hoursRadius * Math.cos(hoursAngle));
			t.setTranslateY(offsetY + hoursRadius * Math.sin(hoursAngle));
			hoursRadius += hoursRadiusStep; 
		}
	}
	

	public String[] getDate() {
		String date = format.format(new Date());
		String out[] = date.toUpperCase().split(":");
		return out;
	}

}
