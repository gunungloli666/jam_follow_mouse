package fjr.mouse.follower;

import javafx.scene.text.Text;

public class DynamicsText extends Text{
	
	double offsetx; double offsety; 
	
	double angle; 
	
	double radius; 
	
	double xPosition; 
	double yPosition; 
	
	double destinyOffsetX; 
	double destinyOffsetY; 
	
	public DynamicsText(String text, double x, double y){
		super(text); 
		this.offsetx = x; 
		this.offsety = y; 
	}
	
	public void setOffSet(double x, double y){
		this.offsetx = x; 
		this.offsety = y; 
	}
	
	public void setAngle(double angle){
		this.angle = angle; 
	}
	
	public void setRadius(double radius){
		this.radius = radius ; 
	}
	
	public void move(){
		super.setTranslateX(offsetx + radius * Math.cos(angle));
		super.setTranslateY(offsety + radius * Math.sin(angle)); 
	}
	
	public void setDestinyOffset(double x, double y){
		this.destinyOffsetX = x; 
		this.destinyOffsetY = y; 
	}
	
	public void moveToDestiny(){
		super.setTranslateX(destinyOffsetX + radius * Math.cos(angle));
		super.setTranslateY(destinyOffsetY + radius * Math.sin(angle)); 
	}
	
	public void initCurrentOffSet(){
		this.offsetx = destinyOffsetX; 
		this.offsety = destinyOffsetY; 
	}
	
	public double getPotitionX(){
		return offsetx + radius * Math.cos(angle);
	}
	
	public double getPotitonY(){
		return offsety + radius * Math.sin(angle); 
	}
	
	public void move(double angle){
		super.setTranslateX(offsetx + radius * Math.cos(angle));
		super.setTranslateY(offsety + radius * Math.sin(angle)); 
	}
	
	public void move(double x, double y){
		super.setTranslateX(offsetx + x); 
		super.setTranslateY(offsety + y);
	}
	
	public double getOffsetX(){return offsetx; }
	public double getOffsetY(){return offsety ; }
}
