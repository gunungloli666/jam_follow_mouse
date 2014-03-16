package fjr.mouse.follower;

import javafx.scene.text.Text;

public class DynamicsText extends Text{
	
	double offsetx; double offsety; 
	
	public DynamicsText(String text, double x, double y){
		super(text); 
		this.offsetx = x; 
		this.offsety = y; 
	}
	
	public void setOffSet(double x, double y){
		this.offsetx = x; 
		this.offsety = y; 
	}
	
	public void move(double x, double y){
		super.setTranslateX(offsetx + x); 
		super.setTranslateY(offsety + y);
	}
	
	public double getOffsetX(){return offsetx; }
	public double getOffsetY(){return offsety ; }
}
