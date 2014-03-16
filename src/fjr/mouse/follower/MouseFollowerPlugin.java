
// @author: mohammad Fajar  


package fjr.mouse.follower;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class MouseFollowerPlugin extends AnchorPane{
	double width; 
	double height; 	
	double radius = 90. ;
	
	SimpleDateFormat format = new SimpleDateFormat(
			"EEEEEEEE:d:MMMMMMMM:yyyy:hh:mm:ss", new Locale("in", "ID"));

	Group root; 
	
	ArrayList<DynamicsText> allList = new ArrayList<>(); 
	
	ArrayList<DynamicsText> textDate = new ArrayList<>(); 
	ArrayList<DynamicsText> textMonth = new ArrayList<>(); 
	ArrayList<DynamicsText> textYear = new ArrayList<>(); 
	ArrayList<DynamicsText> day = new ArrayList<>();
	
	ArrayList<DynamicsText> textHours = new ArrayList<>();
	ArrayList<DynamicsText> textMinute = new ArrayList<>(); 
	ArrayList<DynamicsText> textSecond = new ArrayList<>(); 
	
	ArrayList<DynamicsText> combineDate = new ArrayList<>(); 
	
	double secondAngle; 
	double minuteAngle; 
	double hoursAngle; 

	ArrayList<DynamicsText> listLabel = new ArrayList<>(); 
	
	
	Timeline animasi; 
	
	double animasiAngle= 0; 
	
	double offsetX; 
	double offsetY; 
	String[] date ; 
	
	final double TWO_PI = Math.PI * 2;

	Timeline mouseAnimasi; 
	
	Thread thread; 
	
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
		
		mouseAnimasi = new Timeline(); 
		mouseAnimasi.setAutoReverse(false); 
		mouseAnimasi.setCycleCount(1);
		
		addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent evt) {
				mouseRespon(evt.getX() + 120 , evt.getY()  + 120); 
				
			}
		});
		
		initDateList();
		setTextPosition(0);
		initClockList();
		setInitClockPotition();
	
		addClockLabel();
		
		getChildren().add(root); 
	
		play();

	
		// ini harus benar urutannya... 
		allList.addAll(combineDate);
		allList.addAll(listLabel); 
		allList.addAll(textHours); 
		allList.addAll(textMinute); 
		allList.addAll(textSecond); 
	}
	

	public void mouseRespon(){
		mouseRespon(600, 500);
	}
	
	public void mouseRespon(final double x, final double y){
		animasi.pause(); 
		thread = new Thread(
				new Runnable() {					
					@Override
					public void run() {
						int i = 0; 
						while(threadStatus && i < allList.size()){
							final int j = i;
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									DynamicsText text = allList.get(j); 
									TranslateTransition transition = new TranslateTransition();
									transition.setNode(text); 
									transition.setFromX(text.getTranslateX()); 
									transition.setFromY(text.getTranslateY());
									text.setDestinyOffset(x, y);
									text.initCurrentOffSet();
									double destX = text.getPotitionX();
									double destY = text.getPotitonY(); 
									transition.setToX(destX);
									transition.setToY(destY);
									transition.setDuration(Duration.seconds(1));
									transition.setAutoReverse(false); 
									transition.setCycleCount(1);
									transition.play();
								}
							});
							try{
								Thread.sleep(25);
							}catch(Exception e){}
							i++; 
						}
					}
				});
		thread.start();
		animasi.play();
	}
	
	boolean threadStatus = true; 
	
	public void stopThread(){
		threadStatus = false; 
	}
	
	
	public Thread getThread(){
		return thread; 
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
			DynamicsText t = new DynamicsText(s, offsetX , offsetY );
			combineDate.add(t); 
		}
		for(int i=0; i< 1; i++){
			DynamicsText t = new DynamicsText(" ", offsetX, offsetY);
			combineDate.add(t); 
		}
		for(String s : date[1].split("")){
			DynamicsText t = new DynamicsText(s , offsetX, offsetY);
			combineDate.add(t); 
		}
		for(int i=0; i< 1; i++){
			DynamicsText t = new DynamicsText(" ", offsetX, offsetY );
			combineDate.add(t); 
		}
		for(String s : date[2].split("")){
			DynamicsText t = new DynamicsText(s , offsetX, offsetY);
			combineDate.add(t); 
		}
		for(int i=0; i< 1; i++){
			DynamicsText t = new DynamicsText(" ", offsetX , offsetY );
			combineDate.add(t); 
		}
		for(String s : date[3].split("")){
			DynamicsText t = new DynamicsText(s,  offsetX,  offsetY);
			combineDate.add(t); 
		}
		for(int i=0; i< 1; i++){
			DynamicsText t = new DynamicsText(" " , offsetX , offsetY );
			combineDate.add(t); 
		}
		root.getChildren().addAll(combineDate);
	}
	
	public void setTextPosition(double initAngle){
		int size = combineDate.size(); 
		double angleStep = (2 * Math.PI)/size; 
		double angle = initAngle; 
		for(DynamicsText t : combineDate){
			double x = radius * Math.cos(angle); 
			double y = radius * Math.sin(angle); 
			t.setAngle(angle);
			t.setRadius(radius);
			t.move(x, y);
			angle -= angleStep;
		}
	}
	
	public void initClockList(){
		for(int i = 0; i < 13; i++){
			DynamicsText text  =  new DynamicsText("." ,  offsetX,  offsetY );
			text.setFont(Font.font(null, FontWeight.BOLD, 14));
			textSecond.add(text); 
		}
		for(int i = 0; i < 10; i++){
			DynamicsText text = new DynamicsText("." , offsetX, offsetY);
			text.setFont(Font.font(null, FontWeight.BOLD, 14));
			textMinute.add(text); 
		}
		for(int i = 0; i < 7; i++){
			DynamicsText text = new DynamicsText("." , offsetX, offsetY);
			text.setFont(Font.font(null, FontWeight.BOLD, 14));
			textHours.add(text); 
		}
		root.getChildren().addAll(textHours); 
		root.getChildren().addAll(textMinute); 
		root.getChildren().addAll(textSecond); 
	}


	
	public void addClockLabel(){
		double radius = this.radius - 22;
		double step = TWO_PI / 12.; 
		double angle0 = - Math.PI/ 2 + step ; 
		for(int i=1; i<=12; i++){
			DynamicsText t  = new DynamicsText(Integer.toString(i), offsetX, offsetY); 
			double x = radius * Math.cos(angle0); 
			double y = radius * Math.sin(angle0); 
			t.setAngle(angle0); 
			t.setRadius(radius);
			t.move(x, y);
			angle0 += step; 
			listLabel.add(t); 
		}
		
		root.getChildren().addAll(listLabel);
//		allList.addAll(listLabel); 
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
		for(DynamicsText t : textSecond){
			double x = secondsRadius * Math.cos(secondAngle); 
			double y = secondsRadius * Math.sin(secondAngle); 
			t.setAngle(secondAngle);
			t.setRadius(secondsRadius); 
			t.move(x, y);
			secondsRadius += secondsRadiusStep; 
		}
		
		double  minuteRadiusStep =(radius - 10 ) / textMinute.size(); 
		double minuteRadius = 0; 
		for(DynamicsText t : textMinute ){
			double x = minuteRadius * Math.cos(minuteAngle); 
			double y = minuteRadius * Math.sin(minuteAngle); 
			t.setRadius(minuteRadius); 
			t.setAngle(minuteAngle); 
			t.move(x, y);
			minuteRadius += minuteRadiusStep; 
		}
		
		double hoursRadiusStep = (radius - 10 ) / textHours.size();
		double hoursRadius = 0; 
		for( DynamicsText t : textHours){
			double x = hoursRadius * Math.cos(hoursAngle); 
			double y = hoursRadius * Math.sin(hoursAngle); 
			t.setAngle(hoursAngle); 
			t.setRadius(hoursRadius);
			t.move(x, y);
			hoursRadius += hoursRadiusStep; 
		}
	}
	
	public String[] getDate() {
		String date = format.format(new Date());
		String out[] = date.toUpperCase().split(":");
		return out;
	}

}
