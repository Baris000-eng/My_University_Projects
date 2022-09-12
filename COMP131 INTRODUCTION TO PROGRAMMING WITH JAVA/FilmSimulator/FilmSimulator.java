
//Name: Baris Kaplan

import java.awt.Color;


import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import acm.graphics.GImage;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;

public class Comp130_HW4_Fall19 extends GraphicsProgram {


	/**
	 * Initializer. Prints the welcome messages. Do <b>not</b> modify.
	 */
	public void init() {
		setTitle("MakeAMovie");
		println("Welcome to Make a Movie!");
		println("Start by either creating a new project or opening an existing one.");
	}
	
	/**
	 * Entry method for your implementation.
	 */
	public void run() {
		//your code starts here 
		while(true) {
			playThemeSong(THEME_SONG);//for playing the theme song
			str= readLine(""+CLI_INPUT_STR+": "); //Taking command inputs from user as strings.
			if(str.equals("setback")) {
				specifyBackgroundColor();//for determining the color type among the choices green,white,magenta,blue
			 }
	else if(str.equals("setgrammar")) {//I am setting the grammar in this step.
			grammar();
		}
	else if(str.equals("addscene")){//I am adding scenes to an array list
				addScene();
		 }
	else if(str.equals("listscenes")) {//I am listing the scenes
			listScenes();
	}
	else if(str.equals("removescene")) {//I am removing the scene I have specified in this step.
		takeTheSceneOut();
	}

	else if(str.equals("play")) {//play part 
		     for(int i=0;i<scenes.size();i++) {
		     String string= scenes.get(i);
		     parts= string.split(" ");//spliting the strings that I got from the arraylist. It will return an array of individiual elements of the string.
		     ArrayList<String>list1= new ArrayList<String>(Arrays.asList(parts));
		     int imageInd=s.indexOf("image");
		     scaleInd= s.indexOf("scale");
		     timeInd= s.indexOf("time");
		     int toInd= s.indexOf("to");
		     int fromInd= s.indexOf("from");
		     GImage image= new GImage(LIBRARY_PATH+"fish"+IMAGE_TYPE);
		     image.scale(Double.parseDouble(list1.get(scaleInd)));//for scaling the image with the constant I get from the list. First, I should convert this constant to a double.
		       forInd= list1.indexOf("for");
		       time= Integer.parseInt(list1.get(forInd+1));
		      if(list1.get(list1.indexOf("from")+1).equals("left")&&((list1.get(list1.indexOf("to")+1).equals("right")))) {
		    	  y=getHeight()/2-image.getHeight()/2;
		    	  x=0;
		    	  add(image,x,y);
		      while(image.getX()<getWidth()-image.getWidth()) {
		    	  dx=1;
		    	  dy=0;
		    	  image.move(dx, dy);// moving according to the directions
		    	  if(list1.contains("milliseconds")){
		    		  pause(PAUSE_TIME*time/(getWidth()-image.getWidth()));
		    	  } else {
		    		  pause(PAUSE_TIME*time*1000/(getWidth()-image.getWidth()));
		    	  }
		      }
		    }
		      if(list1.get(list1.indexOf("from")+1).equals("left")&&((list1.get(list1.indexOf("to")+1).equals("center")))) {
		    	  y=getHeight()/2-image.getHeight()/2;
		    	  x=0;
		    	  add(image,x,y);
		      while(image.getX()<getWidth()/2-image.getWidth()/2) {
		    	  dx=1;
		    	  dy=0;
		    	  image.move(dx, dy);
		    	  if(list1.contains("milliseconds")){
		    		  pause(PAUSE_TIME*time/(getWidth()/2-image.getWidth()/2));
		    	  } else {
		    		  pause(PAUSE_TIME*time*1000/(getWidth()/2-image.getWidth()/2));
		    	  }
		      }
		  }
		      if(list1.get(list1.indexOf("from")+1).equals("right")&&((list1.get(list1.indexOf("to")+1).equals("left")))) {
		    	  y=getHeight()/2-image.getHeight()/2;
		    	  x=getWidth()-image.getWidth();
		    	  add(image,x,y);
		      while(image.getX()>0) {
		    	  dx=-1;
		    	  dy=0;
		    	  image.move(dx, dy);
		    	  if(list1.contains("milliseconds")){
		    		  pause(PAUSE_TIME*time/(getWidth()-image.getWidth()));
		    	  } else {
		    		  pause(PAUSE_TIME*time*1000/(getWidth()-image.getWidth()));
		    	  }
		      }
		  }
		      if(list1.get(list1.indexOf("from")+1).equals("right")&&((list1.get(list1.indexOf("to")+1).equals("center")))) {
		    	  y=getHeight()/2-image.getHeight()/2;
		    	  x=getWidth()-image.getWidth();
		    	  add(image,x,y);
		      while(image.getX()>getWidth()/2-image.getWidth()/2) {
		    	  dx=-1;
		    	  dy=0;
		    	  image.move(dx, dy);
		    	  if(list1.contains("milliseconds")){
		    		  pause(PAUSE_TIME*time/(getWidth()/2-image.getWidth()/2));
		    	  } else {
		    		  pause(PAUSE_TIME*time*1000/(getWidth()/2-image.getWidth()/2));
		    	  }
		      }
		   }
		      if(list1.get(list1.indexOf("from")+1).equals("bottom")&&((list1.get(list1.indexOf("to")+1).equals("top")))) {
		    	  y=getHeight()-image.getHeight();
		    	  x=getWidth()/2-image.getWidth()/2;
		    	  add(image,x,y);//adding the image to the center of the bottom
		      while(image.getY()>0) {
		    	  dx=0;
		    	  dy=-1;
		    	  image.move(dx, dy);
		    	  if(list1.contains("milliseconds")){
		    		  pause(PAUSE_TIME*time/(getHeight()-image.getHeight()));
		    	  } else {
		    		  pause(PAUSE_TIME*time*1000/(getHeight()-image.getHeight()));
		    	  }
		      }
		   }
		      if(list1.get(list1.indexOf("from")+1).equals("bottom")&&((list1.get(list1.indexOf("to")+1).equals("center")))) {
		    	  y=getHeight()-image.getHeight();
		    	  x=getWidth()/2-image.getWidth()/2;
		    	  add(image,x,y);
		      while(image.getY()>getHeight()/2-image.getHeight()/2) {
		    	  dx=0;
		    	  dy=-1;
		    	  image.move(dx, dy);
		    	  if(list1.contains("milliseconds")){
		    		  pause(PAUSE_TIME*time/(getWidth()/2-image.getWidth()/2));
		    	  } else {
		    		  pause(PAUSE_TIME*time*1000/(getWidth()/2-image.getWidth()/2));
		    	  }
		      }
		  }
		      if(list1.get(list1.indexOf("from")+1).equals("center")&&((list1.get(list1.indexOf("to")+1).equals("left")))) {
		    	  y=getHeight()/2-image.getHeight()/2;
		    	  x=getWidth()/2-image.getWidth()/2;
		    	  add(image,x,y);
		      while(image.getX()>0) {
		    	  dx=-1;
		    	  dy=0;
		    	  image.move(dx, dy);
		    	  if(list1.contains("milliseconds")){
		    		  pause(PAUSE_TIME*time/(getWidth()/2-image.getWidth()/2));
		    	  } else {
		    		  pause(PAUSE_TIME*time*1000/(getWidth()/2-image.getWidth()/2));
		    	  }
		      }
		  }
		      if(list1.get(list1.indexOf("from")+1).equals("center")&&((list1.get(list1.indexOf("to")+1).equals("top")))) {
		    	  y=getHeight()/2-image.getHeight()/2;
		    	  x=getWidth()/2-image.getWidth()/2;
		    	  add(image,x,y);//adding the image to the center
		      while(image.getY()>0) {
		    	  dx=0;
		    	  dy=-1;
		    	  image.move(dx, dy);
		    	  if(list1.contains("milliseconds")){
		    		  pause(PAUSE_TIME*time/(getHeight()/2-image.getHeight()/2));
		    	  } else {
		    		  pause(PAUSE_TIME*time*1000/(getHeight()/2-image.getHeight()/2));
		    	  }
		      }
		   }
		      if(list1.get(list1.indexOf("from")+1).equals("center")&&((list1.get(list1.indexOf("to")+1).equals("bottom")))) {
		    	  y=getHeight()/2-image.getHeight()/2;
		    	  x=getWidth()/2-image.getWidth()/2;
		    	  add(image,x,y);
		      while(image.getY()<getHeight()-image.getHeight()) {
		    	  dx=0;
		    	  dy=1;
		    	  image.move(dx, dy);
		    	  if(list1.contains("milliseconds")){
		    		  pause(PAUSE_TIME*time/(getHeight()/2-image.getHeight()/2));
		    	  } else {
		    		  pause(PAUSE_TIME*time*1000/(getHeight()/2-image.getHeight()/2));
		    	  }
		      }
		    }
		      if(list1.get(list1.indexOf("from")+1).equals("center")&&((list1.get(list1.indexOf("to")+1).equals("right")))) {
		    	  y=getHeight()/2-image.getHeight()/2;
		    	  x=getWidth()/2-image.getWidth()/2;
		    	  add(image,x,y);
		      while(image.getX()<getWidth()-image.getWidth()) {
		    	  dx=1;
		    	  dy=0;
		    	  image.move(dx, dy);
		    	  if(list1.contains("milliseconds")){
		    		  pause(PAUSE_TIME*time/(getHeight()/2-image.getHeight()/2));
		    	  } else {
		    		  pause(PAUSE_TIME*time*1000/(getHeight()/2-image.getHeight()/2));//When the list1 contains milliseconds, I have increased the waiting time by multiplying it with 1000.
		    	  }
		      }
		      }
		      }
		     }
	else if(str.equals("listreversedorderofscenes")) {
		listreversedorderofscenes();
    }
	else if(str.equals("exit")) {// when the user enters exit, the program will end.
		    	playThemeSong(END_SONG);
				break;//for end of the program
	}
           else if(str.equals("clear")) {//when the user enters clear, the background color will be white and all of the things will be removed from the array list and canvas.
 		     clearAll();
 		    }
           else {
		illegalCommands();//Printing unknown command for other commands that user can enter.
	}}
		     }
		// your code ends here
		    
	
	//ADDITIONAL HELPER METHODS//
	//your code starts here 
	private void clearAll() {//when the user enters clear, the background color will be white and all of the things will be removed from the array list and canvas.
		      for(int o=0;o<scenes.size();o++) {
		    	scenes.remove(o);
		    }
		      removeAll();
		      setBackground(Color.WHITE);
		    }
	private void illegalCommands() {
		println("Unknown command !");//Printing unknown command for other commands that user can enter.
	}
	private void addScene() {//I am adding scenes to an array list
				println("Describe the new scene: ");
				input= readLine("");//taking inputs as strings from the user
				scenes.add(input);//adding scenes to an array list
	}
	private void takeTheSceneOut() {//I am removing the scene I have specified in this step.
			int b = readInt("Specify the scene you want to delete : ");
			scenes.remove(b-1);//removing the specific numbered element that user gives.
			for(int j=0;j<scenes.size();j++) {
				println(""+(j+1)+")"+scenes.get(j));//printing the new order of the scenes.
			}
		}
	private void listScenes() {//I am listing the scenes
				for(int j=0;j<scenes.size();j++) {
					println(""+(j+1)+")"+scenes.get(j));//getting the elements of the array list 
				}
		}
	private void listreversedorderofscenes() {
		    	for(int j=scenes.size()-1;j>=0;j--) {//reversing the order of the scenes
					println(""+(j+1)+")"+scenes.get(j));//printing the reversed order
				}
		    }
	private void specifyBackgroundColor() {
			 String st= readLine("Specify the background color(White,Green,Blue,Magenta): "); 
			 if(st.equals("Green")) {
				 setBackground(Color.GREEN);// settting the background color to green
			 }
				 else if(st.equals("Blue")){
					 setBackground(Color.BLUE);//setting the background color to blue 
				 }
				 else if(st.equals("Magenta")){
					 setBackground(Color.MAGENTA);//setting the background color to magenta
				 }
				 else if(st.equals("White")) {
					 setBackground(Color.WHITE);//setting the background color to white 
				 }
		 }
	private void grammar() {//I am setting the grammar in this step.
			println("Please specify the order of the grammar elements. ");
			list = new ArrayList<String>();
			list.add("scale");// adding the grammar elements
			list.add("image");
			list.add("from");
			list.add("to");
			list.add("time");
			println(list);//printing the arraylist of the grammar elements
			s= readLine(grammar);// taking the order of grammar elements
		}
	
	// your code ends here
	
	//ADDITIONAL INSTANCE AND CONSTANT VARIABLES//
	//your code starts here 
	private double dx;
	private double dy;
	private String input;
	private String s;
	private int c1= 0;
	private  String[]parts;
	private char ch;
	private int time;
	private ArrayList<String>list;
	private String str;
	private static final String THEME_SONG = "Underwater_Pool.wav";
	private static final long serialVersionUID = 1L;
	private static final String END_SONG = "victory.wav";
	/* Class Instance Variables */
	private Clip clip;
	private AudioInputStream inputStream;
	// your code ends here
	private void playThemeSong(String fileLocation) {
		try {
			inputStream = AudioSystem.getAudioInputStream(new File(fileLocation));
			clip = AudioSystem.getClip();
			clip.open(inputStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}	

	
	// DO NOT REMOVE THIS SECTION//
	private int fromInd;
    private int rightInd;
    private int leftInd;
    private int topInd;
    private  int bottomInd;
    private int centerInd;
    private int toInd;
    private int scaleInd;
    private int timeInd;
	private String grammar = "";
	private List<String> scenes = new ArrayList<String>();
	private static int PAUSE_TIME = 1;
	private int index;
	private int forInd;
	private double x;
	private double y;
	// INSTANCE VARIABLES AND CONSTANTS
	/**
	 * Constant <code>String</code> used to prompt user for commands.
	 */
	public static String CLI_INPUT_STR = "MakeAMovie -> ";

	/**
	 * Path to the folder enclosing the images. Read images from this path.
	 */
	public static String LIBRARY_PATH = "../lib/";

	/**
	 * File extension of image files.
	 */
	public static String IMAGE_TYPE = ".png";

}
