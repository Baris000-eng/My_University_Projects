package gui;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import domain.game.NeedForSpearGame;


public class MainFrame{
	
	public static JFrame frame= new JFrame();
	public static RegisterPanel registerPanel ;
	public static OpeningPanel openingPanel;
	public static LogInPanel logInPanel;
	public static CreateOrLoadOrCustomizePanel createOrLoadOrCustomizePanel;
	public static CustomizeGamePanel customizeGamePanel;
	public static CreateWorldPanel createWorldPanel;
	public static RunningGamePanel runningGamePanel;
	public static ArrayList<JLabel> obstacles=new ArrayList<JLabel>();
	public static ArrayList<JLabel>numHitsArrayList= new ArrayList<JLabel>();
	static protected NeedForSpearGame controller = new NeedForSpearGame("txt");
	public static int myArray[][]=new int [25*(UIConstants.width-2*UIConstants.widthButton-UIConstants.width/50)/UIConstants.width+1][(UIConstants.height/2-UIConstants.heightButton)/40+1];

	public static void main(String[] args) throws IOException {

		controller.setMapBoundaries(UIConstants.rightBoundaryForMap, UIConstants.upperBoundaryForMap, UIConstants.lowerBoundaryForMap);
		controller.setPhantasmAndObstacleDimensions(UIConstants.phantasmLength, UIConstants.rectangleObstacleWidth);
		controller.setInitialPositionsOfSphereAndPhantasm(UIConstants.sphereInitialX, UIConstants.sphereInitialY, UIConstants.phantasmInitialX, UIConstants.phantasmInitialY);
		controller.setMovementAmountOfPhantasm(UIConstants.phantasmMovement);
		controller.setWidthHeight(UIConstants.width, UIConstants.height, UIConstants.widthButton, UIConstants.heightButton);
		//create frame
		frame = new JFrame();
		frame.setTitle("BROGRAMMERS");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(UIConstants.width, UIConstants.height);      

		frame.setLayout(null);

		//add panels
		openingPanel = new OpeningPanel();
		openingPanel.setVisible(true);
		openingPanel.setSize(UIConstants.width, UIConstants.height);
		frame.add(openingPanel);

		registerPanel = new RegisterPanel();
		registerPanel.setVisible(false);
		registerPanel.setSize(UIConstants.width, UIConstants.height);
		frame.add(registerPanel);

		logInPanel = new LogInPanel();
		logInPanel.setVisible(false);
		logInPanel.setSize(UIConstants.width, UIConstants.height);
		frame.add(logInPanel);

		createOrLoadOrCustomizePanel= new CreateOrLoadOrCustomizePanel();
		createOrLoadOrCustomizePanel.setVisible(false);
		createOrLoadOrCustomizePanel.setSize(UIConstants.width, UIConstants.height);
		frame.add(createOrLoadOrCustomizePanel);

		customizeGamePanel= new CustomizeGamePanel();
		customizeGamePanel.setSize(UIConstants.width, UIConstants.height);
		customizeGamePanel.setVisible(false);
		frame.add(customizeGamePanel);

		createWorldPanel= new CreateWorldPanel();
		createWorldPanel.setVisible(false);
		createWorldPanel.setSize(UIConstants.width,  UIConstants.height);
		frame.add(createWorldPanel);

		runningGamePanel= new RunningGamePanel();
		runningGamePanel.setVisible(false);
		runningGamePanel.setSize(UIConstants.width, UIConstants.height);
		frame.add(runningGamePanel);

		frame.setVisible(true);

	}

	


	
	public static void disappear(JLabel j) {
		// TODO Auto-generated method stub
		for(JLabel j1: obstacles) {
			if(j1.getX()==j.getX() && j1.getY()==j.getY()){
				j1.setVisible(false);
				obstacles.remove(j);
				break;
			}
		}

	}
	

	public static int[][] getMyArray() {
		return myArray;
	}

	public static void setMyArray(int myArray[][]) {
		MainFrame.myArray = myArray;
	}




}



