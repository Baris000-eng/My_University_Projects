package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

public class UIConstants {
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int width=screenSize.width;
	public static final int height=screenSize.height;
	
	public static final int sphereInitialX = 19*width/40;
	public static final int sphereInitialY = 12*height/20;
	public static final int phantasmInitialX = 19*width/40-width/100;
	public static final int phantasmInitialY = 8*height/10;
	
	public static final int phantasmLength = width/10;
	public static final int rectangleObstacleWidth = phantasmLength/5;
	public static final int circularObstacleRadius=15;
	
	public static final int maxNumberOfObstacles = 200;
	
	
	public static final int widthButton=width/10;
	public static final int heightButton=height/20;
	public static final int middleX=(width-widthButton)/2;
	public static final int middleY=(height-heightButton)/2;
	
	public static final int rightBoundaryForMap=width-widthButton*2-width/50;
	public static final int upperBoundaryForMap=heightButton;
	public static final int lowerBoundaryForMap=height/2;
	
	public static final int phantasmMovement = UIConstants.width/50;
	
	
}
