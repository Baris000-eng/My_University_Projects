package main;

import java.awt.Dimension;
import java.awt.Toolkit;

public class UIConstants {
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int width=screenSize.width;
	public static final int height=screenSize.height;
	
	public static final int widthButton=width/10;
	public static final int heightButton=height/20;
	public static final int middleX=(width-widthButton)/2;
	public static final int middleY=(height-heightButton)/2;	
}
