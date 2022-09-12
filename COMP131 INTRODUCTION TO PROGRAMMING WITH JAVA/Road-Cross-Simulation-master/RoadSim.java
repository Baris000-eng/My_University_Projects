/*|Student Name: Barış Kaplan


 *Homework #3				
 *|Fine A Car! - A Crossroads Simulation	
 */

import acm.program.GraphicsProgram;


import acm.util.RandomGenerator;
import acm.graphics.GLine;
import acm.graphics.GOval;
import acm.graphics.GRect;
import java.awt.Color;
import java.awt.Font;
import acm.program.*;
import acm.graphics.GLabel;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


//Ignore SuppressWarnings tag, it is irrelevant to the course.
@SuppressWarnings("serial")
public class HW3 extends GraphicsProgram {

	//Do not change these variables.
	/**
	 * Light objects.
	 */
	GOval vLight, hLight;
	// Additional instance variables
	private int totalFine1=0;
	private int totalFine2=0;
	private GLabel a;
	private GLabel b;
	private GLabel redCar;
	private GLabel c;
	private GLabel blueCar;
	private GLabel d;
	private GLabel totalFineRed;
	private GLabel totalFineBlue;
	private int v1;
	private int v2;
	private int sum1= ((RED_LIGHT_FINE)+((SPEED_FINE)*(v1-SPEED_LIMIT)));
	private int sum2= ((RED_LIGHT_FINE)+(SPEED_FINE)*(v2-SPEED_LIMIT));
	private int vLightTime= 0;
	private int hLightTime= 0;
	private int vLightTime1=0;
	private int hLightTime1=0;
	private GRect car1;
	private GRect car2;
	
	//Your code starts here.

	
	//Your code ends here.
	
	public void run(){
		playThemeSong(THEME_SONG);
		GRect d = new GRect(65,75,20,20);
		GOval f= new GOval(65,75,20,20);
		GRect e = new GRect(70,95,10,45);
		d.setFilled(true);
		e.setFilled(true);
		f.setFilled(true);
		d.setColor(rgen.nextColor());
		e.setColor(rgen.nextColor());
		d.setFillColor(rgen.nextColor());
		e.setFillColor(rgen.nextColor());
		f.setFillColor(Color.BLACK);
		f.setColor(Color.YELLOW);
		add(d);
		add(e);
		add(f);
		add(e);
		GLabel c = new GLabel("ATTENTION.THERE CAN BE AN ANIMAL");
		c.setColor(rgen.nextColor());
		c.setFont(LABEL_FONT);
		add(c,50,40);
		initializeLabels();
		addCars();//By using this method, I have created the cars and add them.I have used rectangles for the representation of the cars. 
		v1= rgen.nextInt(MIN_SPEED,MAX_SPEED);// I have initially set the speed of car1 to v1.
		v2= rgen.nextInt(MIN_SPEED,MAX_SPEED);// I have initially set the speed of car1 to v2.
		while(true) {// For doing the animation again and again.
			arrangeLightTimes();//By using this method, I have changed the light times. 
			moveAndrenewLocationAndSpeed();
	        displayFines();  // To show the red light fines and speeding fines on the screen. 
	        if((car1.contains(car2.getX(),car2.getY())||car1.contains(car2.getX(),car2.getY()+CAR_WIDTH))||car2.contains(car1.getX(),car1.getY()+CAR_LENGHT)||car2.contains(car1.getX()+CAR_WIDTH,car1.getY()+CAR_LENGHT)||car1.contains(car2.getX()+CAR_LENGHT/2,car2.getY()+CAR_WIDTH/2)) {
	        	  playVictorySong(VICTORY_SONG);
	        	  displayTotalFine();//to indicate what the total fines are on the upper right and left corners
				  break;//If the car accident happens, program terminates. 
	        }
	        GLabel d1 = new GLabel("CAR_ACCIDENT");
	        d1.setColor(rgen.nextColor());
	        add(d1,SCREEN_WIDTH/2-ROAD_THICKNESS/2-15,-95);
	     }
		}
		
		
		    
	



		   

		    
		    
			
	
		
		
		//Your code ends here.
		//Initialize the labels.
		
		
		//Initiate cars and their speeds.
		
	
		
		
		//Initiate light times.
		
		
		//Main animation loop.
		
		
		//Post collision final display.
		
		//Your code ends here.
		
	
	/*
	 * DO NOT change anything below this line!
	 */
	
	/** 
	 * Initialization method. This method is guaranteed to run before run().
	 */
	public void init(){
		
		//Initialize the screen size.
		this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		
		//Set the background color. 
		this.setBackground(new Color(0,128,0));
		
		//Construct the roads.
		constructRoads();
		
		//Place the lights.
		placeLights();
		
	}
	private void displayFines() {
		if((SCREEN_HEIGHT/2-ROAD_THICKNESS/2)<=car1.getY()&&car1.getY()<=(SCREEN_HEIGHT/2+ROAD_THICKNESS/2)) {
			if(vLight.getFillColor().equals(Color.RED)&&v1<=SPEED_LIMIT) {
			     blueCar.setLabel("+"+RED_LIGHT_FINE+"$ Reason: Red Light");
				blueCar.setLocation(LABEL_X_MARGIN,LABEL_Y_MARGIN);
				blueCar.setColor(CAR_COLOR_BLUE);
				blueCar.setFont(LABEL_FONT);
				c.setLabel("");
				d.setLabel("");
				totalFineBlue.setLabel("");  
				totalFine1+=RED_LIGHT_FINE;
			}
			if(v1>SPEED_LIMIT&&vLight.getFillColor().equals(Color.GREEN)) {
				c.setLabel("+"+(SPEED_FINE*(v1-SPEED_LIMIT))+"$ Reason: Speeding");
				c.setLocation(LABEL_X_MARGIN,LABEL_Y_MARGIN);
				c.setColor(CAR_COLOR_BLUE);
				c.setFont(LABEL_FONT);
			    d.setLabel("");
				blueCar.setLabel("");
				totalFineBlue.setLabel("");
			    totalFine1+=(SPEED_FINE*(v1-SPEED_LIMIT));
							   
			}
			if(v1>SPEED_LIMIT&&vLight.getFillColor().equals(Color.RED)) {
		        d.setLabel("+"+(RED_LIGHT_FINE+(SPEED_FINE*(v1-SPEED_LIMIT)))+"$ Reason: Speeding & Red Light");
				d.setLocation(LABEL_X_MARGIN,LABEL_Y_MARGIN);
				d.setColor(CAR_COLOR_BLUE);
			    d.setFont(LABEL_FONT);
				blueCar.setLabel("");
				c.setLabel("");
				totalFineBlue.setLabel("");
				totalFine1+=(RED_LIGHT_FINE+(SPEED_FINE*(v1-SPEED_LIMIT)));
							
			}
		if(v1<SPEED_LIMIT&&vLight.getFillColor()==Color.GREEN)  {
				blueCar.setLabel("");
				blueCar.setLocation(LABEL_X_MARGIN,LABEL_Y_MARGIN);
				c.setLabel("");
				c.setLocation(LABEL_X_MARGIN,LABEL_Y_MARGIN);
				d.setLabel("");
				d.setLocation(LABEL_X_MARGIN,LABEL_Y_MARGIN);
			}
		}
		
		  if((SCREEN_WIDTH/2-ROAD_THICKNESS/2)<=car2.getX()&&car2.getX()<=(SCREEN_WIDTH/2+ROAD_THICKNESS/2)) {
			  if(hLight.getFillColor()==(Color.RED)&&v2<=SPEED_LIMIT) {
							   redCar.setLabel("+"+RED_LIGHT_FINE+"$ Reason: Red Light");
							   redCar.setLocation(SCREEN_WIDTH-redCar.getWidth()-LABEL_X_MARGIN,LABEL_Y_MARGIN);
							   redCar.setColor(CAR_COLOR_RED);
							   redCar.setFont(LABEL_FONT);
							   a.setLabel("");
							   b.setLabel("");
							   totalFineRed.setLabel("");
							   totalFine2+=(RED_LIGHT_FINE);//add RED_LIGHT_FINE to totalFine2, when the car has a fine due to the red light. 
			  }
			   if(v2>SPEED_LIMIT&&hLight.getFillColor()==(Color.GREEN)) {
				               a.setLabel("+"+((SPEED_FINE)*(v2-SPEED_LIMIT))+"$ Reason: Speeding");
							   a.setLocation(SCREEN_WIDTH-a.getWidth()-LABEL_X_MARGIN,LABEL_Y_MARGIN);
							   a.setColor(CAR_COLOR_RED);
							   a.setFont(LABEL_FONT);
							   redCar.setLabel("");
							   b.setLabel("");
							   totalFineRed.setLabel("");
							   totalFine2+=((SPEED_FINE)*(v2-SPEED_LIMIT));// add the quantity of ((SPEED_FINE)*(v2-SPEED_LIMIT)) to totalfine2,when the car has a fine due to speed.
					          
			}
			  if(v2>SPEED_LIMIT&&hLight.getFillColor()==(Color.RED)) {
							  b.setLabel("+"+(RED_LIGHT_FINE+(SPEED_FINE*(v2-SPEED_LIMIT)))+"$ Reason: Speeding & Red Light");
							  b.setLocation(SCREEN_WIDTH-b.getWidth()-LABEL_X_MARGIN,LABEL_Y_MARGIN);
							   b.setColor(CAR_COLOR_RED);
							   b.setFont(LABEL_FONT);
							   redCar.setLabel("");
							   a.setLabel("");
							   totalFineRed.setLabel("");
							   totalFine2+=((RED_LIGHT_FINE)+((SPEED_FINE)*(v2-SPEED_LIMIT)));
							   
			}
			  if(v2<SPEED_LIMIT&&hLight.getFillColor()==Color.GREEN) {
				redCar.setLabel("");
				redCar.setLocation(SCREEN_WIDTH-redCar.getWidth()-LABEL_X_MARGIN,LABEL_Y_MARGIN);
				a.setLabel("");
				a.setLocation(SCREEN_WIDTH-a.getWidth()-LABEL_X_MARGIN,LABEL_Y_MARGIN);
				b.setLabel("");
				b.setLocation(SCREEN_WIDTH-b.getWidth()-LABEL_X_MARGIN,LABEL_Y_MARGIN);
			 }}}
	private void moveAndrenewLocationAndSpeed() {
		car2.move(-v2,0);
		if(car2.getX()+CAR_LENGHT<0) {//If the car2 goes out of the screen.
      	car2.setLocation(SCREEN_WIDTH,((SCREEN_HEIGHT-CAR_WIDTH)/2-(ROAD_THICKNESS/4)));//to renew the location of car2.
		add(car2,car2.getLocation().getX(),car2.getLocation().getY());
		v2= rgen.nextInt(MIN_SPEED,MAX_SPEED);//to renew the speed of car2
		car2.move(-v2,0);}
			car1.move(0, v1);
			pause(PAUSE_TIME);
			if(car1.getY()+CAR_LENGHT>SCREEN_HEIGHT){//If the car1 goes out of the screen. 
		   car1.setLocation((SCREEN_WIDTH/2-CAR_WIDTH/2-ROAD_THICKNESS/4),-CAR_LENGHT);//to renew the location of car1.
		   add(car1,car1.getLocation().getX(),car1.getLocation().getY());
		   v1= rgen.nextInt(MIN_SPEED,MAX_SPEED);//to renew the speed of car1
		   car1.move(0,v1);
		} 
	}
	private void arrangeLightTimes() {//This method's purpose is to determine the time that passes from the redLight situation to greenLight situation or vice versa.
		vLightTime+=PAUSE_TIME;
		 vLightTime1+=PAUSE_TIME;
		 hLightTime+=PAUSE_TIME;
		 hLightTime1+=PAUSE_TIME;
	    if(vLightTime>=rgen.nextInt(MIN_LIGHT_TIME,MAX_LIGHT_TIME)) {
	    	 vLight.setFilled(true);
	    	 vLight.setFillColor(Color.GREEN);
	    	 hLightTime=0;
	    	 hLightTime1=0;
	    	 vLightTime=0;
	    }
	    if(vLightTime1>=rgen.nextInt(MIN_LIGHT_TIME, MAX_LIGHT_TIME)) {
	    	 vLight.setFilled(true);
	    	 vLight.setFillColor(Color.RED);
	    	 hLightTime1=0;
	    	 vLightTime=0;
	    	 vLightTime1=0;
	    	 }
			 
	    if(hLightTime>=rgen.nextInt(MIN_LIGHT_TIME,MAX_LIGHT_TIME)) {
	    	hLight.setFilled(true);
		    hLight.setFillColor(Color.GREEN);
		    vLightTime=0;
		    vLightTime1=0;
		    hLightTime=0;
		    }
	    if(hLightTime1>=rgen.nextInt(MIN_LIGHT_TIME,MAX_LIGHT_TIME)) {
	    	hLight.setFilled(true);
		    hLight.setFillColor(Color.RED);
		    vLightTime1=0;
		    hLightTime=0;
		    hLightTime1=0;
		    }
	}
	
	
	private void displayTotalFine() {
		 totalFineRed.setLabel("Total Fine : "+totalFine2+"");
		 totalFineRed.setLocation(SCREEN_WIDTH-totalFineRed.getWidth()-LABEL_X_MARGIN,LABEL_Y_MARGIN);
		 totalFineRed.setColor(CAR_COLOR_RED);
		 totalFineRed.setFont(LABEL_FONT);
				totalFineBlue.setLabel("Total Fine : "+totalFine1+"");
				totalFineBlue.setLocation(LABEL_X_MARGIN, LABEL_Y_MARGIN);
				totalFineBlue.setColor(CAR_COLOR_BLUE);
				totalFineBlue.setFont(LABEL_FONT);
				 redCar.setLabel("");
					a.setLabel("");
					b.setLabel("");
				blueCar.setLabel("");
				c.setLabel("");
				d.setLabel("");
	}
	private void initializeLabels() {
		b= new GLabel("");
		   add(b,SCREEN_WIDTH-b.getWidth()-LABEL_X_MARGIN,LABEL_Y_MARGIN);
		   a= new GLabel("");
		   add(a,SCREEN_WIDTH-a.getWidth()-LABEL_X_MARGIN,LABEL_Y_MARGIN);
		   redCar= new GLabel("");
		   add(redCar,SCREEN_WIDTH-redCar.getWidth()-LABEL_X_MARGIN,LABEL_Y_MARGIN);
		   totalFineRed= new GLabel("");
		  add(totalFineRed,SCREEN_WIDTH-totalFineRed.getWidth()-LABEL_X_MARGIN,LABEL_Y_MARGIN);
		   blueCar= new GLabel("");
		   add(blueCar,LABEL_X_MARGIN,LABEL_Y_MARGIN);
		   c= new GLabel("");
		   add(c,LABEL_X_MARGIN,LABEL_Y_MARGIN);
		   d= new GLabel("");
		   add(d,LABEL_X_MARGIN,LABEL_Y_MARGIN);
		   totalFineBlue= new GLabel("");
		   add(totalFineBlue,LABEL_X_MARGIN,LABEL_Y_MARGIN);
	}
	
	private void addCars() {
		car1= new GRect((SCREEN_WIDTH/2-CAR_WIDTH/2-ROAD_THICKNESS/4),-CAR_LENGHT,CAR_WIDTH,CAR_LENGHT);
		car1.setFilled(true);
		car1.setFillColor(CAR_COLOR_BLUE);// fills the car with blue
		car2= new GRect(SCREEN_WIDTH,((SCREEN_HEIGHT-CAR_WIDTH)/2-(ROAD_THICKNESS/4)),CAR_LENGHT,CAR_WIDTH);
		car2.setFilled(true);
		car2.setFillColor(CAR_COLOR_RED);//fills the car with red
		add(car1);// adds the blue car to the screen
		add(car2);//adds the red car to the screen 
	}
	/**
	 * This method constructs the lanes and the intersection.
	 */
	public void constructRoads(){
		
		//Road and crossing objects.
		GRect vRoad, hRoad, crossing;
		
		//Vertical and horizontal road creation.
		vRoad = createRoad(SCREEN_WIDTH/2, ROAD_THICKNESS, "v");
		hRoad = createRoad(SCREEN_HEIGHT/2, ROAD_THICKNESS, "h");
		add(vRoad);
		add(hRoad);
		
		//Square crossing section.
		crossing = new GRect(SCREEN_WIDTH/2-ROAD_THICKNESS/2, SCREEN_HEIGHT/2-ROAD_THICKNESS/2, ROAD_THICKNESS, ROAD_THICKNESS);
		crossing.setColor(new Color(255,255,255));
		add(crossing);
		
		//Lane separator lines.
		for(int i = 0; i<SCREEN_WIDTH/2-ROAD_THICKNESS/2; i+=25){
			GLine line = new GLine(i, SCREEN_HEIGHT/2, i+15, SCREEN_HEIGHT/2);
			line.setColor(Color.WHITE);
			add(line);
		}
		for(int i = SCREEN_WIDTH; i>SCREEN_WIDTH/2+ROAD_THICKNESS/2; i-=25){
			GLine line = new GLine(i, SCREEN_HEIGHT/2, i-15, SCREEN_HEIGHT/2);
			line.setColor(Color.WHITE);
			add(line);
		}
		for(int i = 0; i<SCREEN_HEIGHT/2-ROAD_THICKNESS/2; i+=25){
			GLine line = new GLine(SCREEN_WIDTH/2, i, SCREEN_WIDTH/2, i+15);
			line.setColor(Color.WHITE);
			add(line);
		}
		for(int i = SCREEN_HEIGHT; i>SCREEN_HEIGHT/2+ROAD_THICKNESS/2; i-=25){
			GLine line = new GLine(SCREEN_WIDTH/2, i, SCREEN_WIDTH/2, i-15);
			line.setColor(Color.WHITE);
			add(line);
		}
		
	}
	
	/**
	 * This method creates and places the light objects.
	 * @see See vLight and hLight for the light objects.
	 */
	public void placeLights(){
		
		//Create and place the vertical light.
		vLight = new GOval(25, 25);
		vLight.setFilled(true);
		vLight.setFillColor(Color.RED);
		add(vLight, SCREEN_WIDTH/2-75-3, SCREEN_HEIGHT/2-100);
		
		//Create and place the horizontal light.
		hLight = new GOval(25, 25);
		hLight.setFilled(true);
		hLight.setFillColor(Color.RED);
		add(hLight, SCREEN_WIDTH/2+75, SCREEN_HEIGHT/2-75-3);
		
	}
	
	/**
	 * @param center Center of the road to be built.
	 * @param width Thickness of the road.
	 * @param dir Direction of the road. Allowed values: "v", "h"
	 * @throws RuntimeException on invalid orientation.
	 * @return Created road as a GRect.
	 * @see constructRoads()
	 */
	public GRect createRoad(int center, int width, String dir){
		
		//Use dir to determine the road orientation.
		if(dir.equals("v")){
			
			//Create the road object.
			GRect road = new GRect(center-width/2, 0, width, SCREEN_HEIGHT);
			
			//Set border color.
			road.setColor(new Color(0,0,0,0));
			
			//Set fill color.
			road.setFilled(true);
			road.setFillColor(new Color(50,50,50));
			
			//Return the created road object.
			return road;
			
		}else if (dir.equals("h")){
			
			//Create the road object.
			GRect road = new GRect(0, center-width/2, SCREEN_WIDTH, width);
			
			//Set border color.
			road.setColor(new Color(0,0,0,0));
			
			//Set fill color.
			road.setFilled(true);
			road.setFillColor(new Color(50,50,50));
			
			//Return the created road object.
			return road;
			
		}else{
			
			//Ignore throw keyword, it is irrelevant to the course.
			throw new RuntimeException("Invalid argument 'orientation' = '" + dir + "' @ drawRoad.");
			
		}
	}
	
	
	/**
	 * Width of the screen.
	 */
	public static final int SCREEN_WIDTH = 800;
	
	/**
	 * Height of the screen.
	 */
	public static final int SCREEN_HEIGHT = 600;
	
	/**
	 * Thickness of the roads constructed on the screen.
	 */
	public static final int ROAD_THICKNESS = 100;
	
	/**
	 * Width of the cars.
	 */
	public static final int CAR_WIDTH = 30;
	
	/**
	 * Lenght of the cars.
	 */
	public static final int CAR_LENGHT = 70;
	
	/**
	 * Color for the blue car.
	 */
	public static final Color CAR_COLOR_BLUE = new Color(0,0,192);
	
	/**
	 * Color for the red car.
	 */
	public static final Color CAR_COLOR_RED = new Color(192,0,0);
	
	/**
	 * Minimum speed allowed for the cars.
	 */
	public static final int MIN_SPEED = 5;
	
	/**
	 * Maximum speed allowed for the cars.
	 */
	public static final int MAX_SPEED = 30;
	
	/**
	 * Speed limit for the cars
	 */
	public static final int SPEED_LIMIT = 20;
	
	/**
	 * Unit fine used to calculate speeding fines.
	 */
	public static final int SPEED_FINE = 10;
	
	/**
	 * Static fine for passing a red light.
	 */
	public static final int RED_LIGHT_FINE = 100;
	
	/**
	 * Pause time for the animation.
	 */
	public static final int PAUSE_TIME = 50;
	
	/**
	 * Minimum time for a traffic light to change.
	 */
	public static final int MIN_LIGHT_TIME = 1000;
	
	/**
	 * Maximum time for a traffic light to change. 
	 */
	public static final int MAX_LIGHT_TIME = 5000;
	
	/**
	 * Font for use in labels.
	 */
	public static final Font LABEL_FONT = new Font("Courier", Font.PLAIN, 15);
	
	/**
	 * Margin of fine labels for the Y-axis.
	 */
	public static final int LABEL_Y_MARGIN = 20;
	
	/**
	 * Margin of fine labels for the X-axis.
	 */
	public static final int LABEL_X_MARGIN = 10;
	
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

	private void playVictorySong(String fileLocation) {
		try {
			clip.close();
			inputStream.close();
			inputStream = AudioSystem.getAudioInputStream(new File(fileLocation));
			clip.open(inputStream);
			clip.start();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}


	private static final String THEME_SONG = "theme.wav";
	private static final String VICTORY_SONG = "victory.wav";
	private Clip clip;
	private AudioInputStream inputStream;
	 private RandomGenerator rgen = RandomGenerator.getInstance();

}


