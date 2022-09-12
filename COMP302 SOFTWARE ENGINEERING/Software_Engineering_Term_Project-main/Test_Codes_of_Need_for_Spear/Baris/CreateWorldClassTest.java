package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JLabel;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.game.CollisionChecker;
import domain.game.NeedForSpearGame;
import domain.obstacles.FirmObstacle;
import gui.CreateWorldPanel;
import gui.MainFrame;
import gui.RunningGamePanel;

import java.awt.AWTException;
import java.awt.Event;
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

class CreateWorldTest {
	
	//DONE BY: BARIŞ KAPLAN
	
	
	
	private static CreateWorldPanel cwp;
	private static NeedForSpearGame controller;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		if(cwp==null) {
			cwp= new CreateWorldPanel();
		}
		
		if(controller==null) {
			controller=new NeedForSpearGame("txt");
		}
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	
	}

	
	
	

	@AfterEach
	void tearDown() throws Exception {
		 MainFrame.obstacles.removeAll(MainFrame.obstacles); 
		 cwp.removeAll();
		 //For removing all of the obstacles in the arraylist keeping track of the obstacles in the player map
	}


	
	
	
	//IN THIS TEST, I have checked whether the default values of the numbers of the obstacles are correctly initialized.
	//This is an example of GLASSBOX testing. To see what the default values in the textfields of the 
	//obstacle numbers are, we should look the method called initializeTextFields()  which is in createWorldPanel.java.
	//The default values in the obstacle number textboxes are used in the obstacle creation by the system.
	@Test
	void initializeObstacleNumberTextFieldsTest() {
		int simpleObstacleNum= Integer.parseInt(cwp.getNumOfSimpleObstacle().getText());
		assertTrue(simpleObstacleNum==75);
		
		int firmObstacleNum= Integer.parseInt(cwp.getNumOfFirmObstacle().getText());
		assertTrue(firmObstacleNum==10);
		
		int explosiveObstacleNum= Integer.parseInt(cwp.getNumOfExplosiveObstacle().getText());
		assertTrue(explosiveObstacleNum==5);
		
		int giftObstacleNum= Integer.parseInt(cwp.getNumOfGiftObstacle().getText());
		assertTrue(giftObstacleNum==10);
		
	}
	
	//To write the test for the collision/intersection of two obstacles, 
	// we should look the implementation of the obsCollision (look what the obsCollision returns depending on the conditions specified in the code). 
	// In this test, we are obtaining the data for testing from the code of the helper method called obsCollision.
	//So, we can say that this test is an example of GLASSBOX testing.
	//Note: As specified in the building mode requirements, two obstacle should not intersect with each other. 
	@Test
	public void collisionOfTwoObstacles() {
		JLabel firmObstacle= new JLabel(MainFrame.firmIcon);
		cwp.add(firmObstacle);
		firmObstacle.setBounds(100,100,MainFrame.width/50,20);
		
		JLabel explosiveObstacle= new JLabel(MainFrame.explosiveIcon);
		cwp.add(explosiveObstacle);
		explosiveObstacle.setBounds(100+MainFrame.width/50,120,MainFrame.width/50,20);
	
		assertTrue(cwp.obsCollision(firmObstacle, explosiveObstacle)==true);
		
		
	}
	
	 //To write the test for the collision/intersection of two obstacles from the leftDownCorner.
		// we should look the implementation of the collisionOfTwoObstaclesFromLeftDownCorner (we should look what the 
	    // collisionOfTwoObstaclesFromLeftDownCorner returns depending on the conditions specified in the code). 

		// In this test, we are obtaining the data for testing from the code of the helper method called collisionOfTwoObstaclesFromLeftDownCorner.
		//So, we can say that this test is an example of GLASSBOX testing.
		//Note: As specified in the building mode requirements, two obstacle should not intersect with each other. 
	   //From the code of the obsLeftDownCornerCollision method, I have derived the sample x and y coordinates that I give to the 
	   // JLabels in this test case.
	@Test
	public void collisionOfTwoObstaclesFromRightDownCorner() {
		JLabel simpleObstacle= new JLabel(MainFrame.simpleIcon);
		cwp.add(simpleObstacle);
		simpleObstacle.setBounds(110,110,MainFrame.width/50,20);
		
		JLabel giftObstacle= new JLabel(MainFrame.giftIcon);
		cwp.add(giftObstacle);
		giftObstacle.setBounds(simpleObstacle.getX()+MainFrame.width/50,simpleObstacle.getY()+20,MainFrame.width/50,20);

		assertTrue(cwp.obsRightDownCornerCollision(simpleObstacle, giftObstacle)==true);
	}
	
	
	 //To write the test for the collision/intersection of two obstacles from the leftDownCorner.
	// we should look the implementation of the collisionOfTwoObstaclesFromLeftDownCorner (we should look what the 
    // collisionOfTwoObstaclesFromLeftDownCorner returns depending on the conditions specified in the code). 

	// In this test, we are obtaining the data for testing from the code of the helper method called collisionOfTwoObstaclesFromLeftDownCorner.
	//So, we can say that this test is an example of GLASSBOX testing.
	//Note: As specified in the building mode requirements, two obstacle should not intersect with each other. 
   //From the code of the obsLeftDownCornerCollision method, I have derived the sample x and y coordinates that I give to the 
   // JLabels in this test case.
@Test
public void collisionOfTwoObstaclesFromLeftDownCorner() {
	JLabel simpleObstacle= new JLabel(MainFrame.simpleIcon);
	cwp.add(simpleObstacle);
	simpleObstacle.setBounds(150,150,MainFrame.width/50,20);
	
	JLabel giftObstacle= new JLabel(MainFrame.giftIcon);
	cwp.add(giftObstacle);
	giftObstacle.setBounds(simpleObstacle.getX()-MainFrame.width/50,simpleObstacle.getY()+20,MainFrame.width/50,20);

	assertTrue(cwp.obsLeftDownCornerCollision(simpleObstacle, giftObstacle)==true);
}

	
	        //To write the test for the collision/intersection of two obstacles from the rightUpCorner.
			// we should look the implementation of the collisionOfTwoObstaclesFromRightUpCorner (we should look what the 
		    // collisionOfTwoObstaclesFromRightUpCorner returns depending on the conditions specified in the code). 
		
			// In this test, we are obtaining the data for testing from the code of the helper method called collisionOfTwoObstaclesFromRightUpCorner.
			//So, we can say that this test is an example of GLASSBOX testing.
			//Note: As specified in the building mode requirements, two obstacle should not intersect with each other. 
	       //From the code of the obsRightUpCornerCollision method, I have derived the sample x and y coordinates that I give to the 
	       // JLabels in this test case.
		@Test
		public void collisionOfTwoObstaclesFromRightUpCorner() {
			JLabel simpleObstacle= new JLabel(MainFrame.simpleIcon);
			cwp.add(simpleObstacle);
			simpleObstacle.setBounds(200,200,MainFrame.width/50,20);
			
			JLabel giftObstacle= new JLabel(MainFrame.giftIcon);
			cwp.add(giftObstacle);
			giftObstacle.setBounds(simpleObstacle.getX()+MainFrame.width/50,simpleObstacle.getY()-20,MainFrame.width/50,20);
		
			assertTrue(cwp.obsRightUpCornerCollision(simpleObstacle, giftObstacle)==true);
		}
	
	
	
	   //To write the test for the collision/intersection of two obstacles from the leftUpCorner, 
		// we should look the implementation of the collisionOfTwoObstaclesFromLeftUpCorner (we should look what the 
	    // collisionOfTwoObstaclesFromLeftUpCorner returns depending on the conditions specified in the code). 
	
		// In this test, we are obtaining the data for testing from the code of the helper method called collisionOfTwoObstaclesFromLeftUpCorner.
		//So, we can say that this test is an example of GLASSBOX testing.
		//Note: As specified in the building mode requirements, two obstacle should not intersect with each other. 
	@Test
	public void collisionOfTwoObstaclesFromLeftUpCorner() {
		JLabel simpleObstacle= new JLabel(MainFrame.simpleIcon);
		cwp.add(simpleObstacle);
		simpleObstacle.setBounds(100,100,MainFrame.width/50,20);
		
		JLabel explosiveObstacle= new JLabel(MainFrame.explosiveIcon);
		cwp.add(explosiveObstacle);
		explosiveObstacle.setBounds(simpleObstacle.getX()-MainFrame.width/50,simpleObstacle.getY()-20,MainFrame.width/50,20);
	
		assertTrue(cwp.obsLeftUpCornerCollision(simpleObstacle, explosiveObstacle)==true);
	}
	
	
	
	
	
	///15 EXAMPLES OF BLACKBOX TESTING. In order to make the JLabels collide or not collide, the JLabels should not be null. 
	//We can get this information from the specification of the obsCollision & obsLeftDownCornerCollision & obsLeftUpCornerCollision 
	// & obsRightUpCornerCollision & obsRightDownCornerCollision methods in the CreateWorldPanel.java. We do not have to look the code of 
	// the obsCollision, obsLeftDownCornerCollision, obsRightDownCornerCollision, obsRightUpCornerCollision, and obsLeftUpCornerCollision 
	// methods to write these test cases. 
	// We should more specifically look to the REQUIRES part which is before the obsCollision, obsRightDownCornerCollision, 
	// obsLeftDownCornerCollision,obsRightUpCornerCollision, and obsLeftUpCornerCollision methods.
	@Test
	public void obstacleCollisionsWithEachOtherExpectedNullPointerException() {
		//BlackBox testing, checking if 
		assertThrows(NullPointerException.class, ()-> {
			 cwp.obsCollision(null, null); // AN EXAMPLE OF BLACKBOX TESTING for obsCollision method, throws exception
		});
		assertThrows(NullPointerException.class, ()-> {
              JLabel simpleLbl= new JLabel(MainFrame.simpleIcon);
              cwp.obsCollision(simpleLbl, null);  // AN EXAMPLE OF BLACKBOX TESTING for obsCollision method, throws exception
		});
		assertThrows(NullPointerException.class, ()-> {
			 JLabel firmLbl= new JLabel(MainFrame.firmIcon);
             cwp.obsCollision(null, firmLbl);  // AN EXAMPLE OF BLACKBOX TESTING for obsCollision method, throws exception
		});
		
		assertThrows(NullPointerException.class, ()-> {
			 JLabel firmLbl= new JLabel(MainFrame.firmIcon);
              cwp.obsRightDownCornerCollision(null, firmLbl);  // AN EXAMPLE OF BLACKBOX TESTING for obsRightDownCollision method, throws exception
		});
		
		assertThrows(NullPointerException.class, ()-> {
			 JLabel firmLbl= new JLabel(MainFrame.firmIcon);
             cwp.obsLeftDownCornerCollision(null, firmLbl);  // AN EXAMPLE OF BLACKBOX TESTING for obsLeftDownCollision method, throws exception
		});
		
		
		assertThrows(NullPointerException.class, ()-> {
			 JLabel firmLbl= new JLabel(MainFrame.firmIcon);
            cwp.obsLeftDownCornerCollision(firmLbl, null);  // AN EXAMPLE OF BLACKBOX TESTING for obsLeftDownCollision method, throws exception
		});
		
		assertThrows(NullPointerException.class, ()-> {
			 JLabel firmLbl= new JLabel(MainFrame.firmIcon);
             cwp.obsLeftUpCornerCollision(null, null); // AN EXAMPLE OF BLACKBOX TESTING for obsLeftUpCollision method, throws exception
		});
		
		assertThrows(NullPointerException.class, ()-> {
			 JLabel firmLbl= new JLabel(MainFrame.firmIcon);
            cwp.obsRightUpCornerCollision(firmLbl, null);  // AN EXAMPLE OF BLACKBOX TESTING for obsRightUpCollision method, throws exception
		});
		
		assertThrows(NullPointerException.class, ()-> {
			 JLabel firmLbl= new JLabel(MainFrame.firmIcon);
           cwp.obsRightUpCornerCollision(null, null);  // AN EXAMPLE OF BLACKBOX TESTING for obsRightUpCollision method, throws exception
		});
		
		assertThrows(NullPointerException.class, ()-> {
		  JLabel firmLbl= new JLabel(MainFrame.firmIcon);
          cwp.obsRightUpCornerCollision(null, firmLbl);  // AN EXAMPLE OF BLACKBOX TESTING for obsRightUpCollision method, throws exception
		});
		
		assertThrows(NullPointerException.class, ()-> {
			 JLabel firmLbl= new JLabel(MainFrame.firmIcon);
          cwp.obsLeftDownCornerCollision(null, null);  // AN EXAMPLE OF BLACKBOX TESTING for obsLeftDownCollision method, throws exception
		});
		
		assertThrows(NullPointerException.class, ()-> {
			 JLabel firmLbl= new JLabel(MainFrame.firmIcon);
          cwp.obsRightDownCornerCollision(null, null);  // AN EXAMPLE OF BLACKBOX TESTING for obsRightDownCollision method, throws exception
		});
		
		assertThrows(NullPointerException.class, ()-> {
			 JLabel firmLbl= new JLabel(MainFrame.firmIcon);
         cwp.obsRightDownCornerCollision(firmLbl, null);  // AN EXAMPLE OF BLACKBOX TESTING for obsRightDownCollision method, throws exception
		});
		
		assertThrows(NullPointerException.class, ()-> {
			 JLabel giftLbl= new JLabel(MainFrame.giftIcon);
            cwp.obsLeftUpCornerCollision(giftLbl, null); // AN EXAMPLE OF BLACKBOX TESTING for obsLeftUpCollision method, throws exception
		});
		
		assertThrows(NullPointerException.class, ()-> {
			 JLabel giftLbl= new JLabel(MainFrame.giftIcon);
           cwp.obsLeftUpCornerCollision(null, giftLbl); // AN EXAMPLE OF BLACKBOX TESTING for obsLeftUpCollision method, throws exception
		});
		
		
		
	}
	
	
	//NOTE: SINCE THEY INCLUDE SOME PARTS OF THE createWorld() FUNCTION, I HAVE INCLUDED THE TESTS BELOW THIS LINE. I RESEARCHED ON 
	//THE INTERNET BUT I COULD NOT FIND A WAY TO SIMULATE THE MOUSE DRAG, MOUSE CLICK, AND MOUSE MOTION EVENTS. I HAVE TRIED 
	//THESE SIMULATIONS VIA USING SOME BOOLEAN VALUES BUT STILL COULD NOT SIMULATE MOUSE DRAG, MOUSE CLICK, AND MOUSE MOTION EVENTS.
	// createWorld() in CreateWorldPanel.java mostly include mouse motion, mouse click, and mouse drag events and outcomes of these events.
	

	//After the system creates each obstacle type with the default values, I have checked whether the size of the arraylist called MainFrame.obstacles
	//is equal to the number of obstacles created (I have done this test for each obstacle type, 
	// and resetted the size of the arraylist called obstacles to 0 after each test. 
	//So, this means that I have tested the creations of only one type of obstacle at every execution of the obstacle creation test.

	//This is an example of the BLACKBOX testing. To add an obstacle to the obstacles arraylist each time we add, 
	// we call add method to add to the arraylist called obstacles. We are creating & adding obstacles 
	//until the upper-boundary in the for loop is reached.
	//These upper boundaries are Integer.parseInt(cwp.getNumOfExplosiveObstacle().getText()), 
	//Integer.parseInt(cwp.getNumOfFirmObstacle().getText()),
	//, and Integer.parseInt(cwp.getNumOfSimpleObstacle().getText()). 
	//After we create & add obstacles, I have checked whether the equality of the size of
	// the arraylist called MainFrame.obstacles (array list keeping track of the obstacles in the player's map) and 
	//the upper boundary in the for loop. 
	//I have done this check for explosive obstacles, firm obstacles, and simple obstacles.
	
	@Test
	void explosiveObstacleSystemCreationTest(){
        for(int i=0;i<Integer.parseInt(cwp.getNumOfExplosiveObstacle().getText());i++) {
			Random random= new Random();
			CreateWorldPanel.locXa= random.nextInt((int) (25*(MainFrame.width-2*MainFrame.widthButton-MainFrame.width/50)/MainFrame.width)+1);
			CreateWorldPanel.locYa= random.nextInt((int) ((MainFrame.height/2-MainFrame.heightButton)/40)+1);
			while(MainFrame.myArray[CreateWorldPanel.locXa][CreateWorldPanel.locYa]==1) {
				CreateWorldPanel.locXa= random.nextInt((int) (25*(MainFrame.width-2*MainFrame.widthButton-MainFrame.width/50)/MainFrame.width)+1);
				CreateWorldPanel.locYa= random.nextInt((int) ((MainFrame.height/2-MainFrame.heightButton)/40)+1);
			}

			MainFrame.myArray[CreateWorldPanel.locXa][CreateWorldPanel.locYa]=1;
			CreateWorldPanel.locX=2*(CreateWorldPanel.locXa*MainFrame.width/50);
			CreateWorldPanel.locY=2*CreateWorldPanel.locYa*20+MainFrame.heightButton;

			JLabel explosiveObstacle=new JLabel(MainFrame.explosiveIcon);


			controller.createObstacle("ExplosiveObstacle", CreateWorldPanel.locX, CreateWorldPanel.locY);
			MainFrame.obstacles.add(explosiveObstacle);
			cwp.add(explosiveObstacle);
			explosiveObstacle.setIcon(MainFrame.explosiveIcon);
			explosiveObstacle.setBounds(CreateWorldPanel.locX,CreateWorldPanel.locY,MainFrame.width/50,20);

	}
        
        assertEquals(MainFrame.obstacles.size(),Integer.parseInt(cwp.getNumOfExplosiveObstacle().getText()));

}
	
	@Test
	void firmObstacleSystemCreationTest(){
        for(int i=0;i<Integer.parseInt(cwp.getNumOfFirmObstacle().getText());i++) {
			Random random= new Random();
			CreateWorldPanel.locXa= random.nextInt((int) (25*(MainFrame.width-2*MainFrame.widthButton-MainFrame.width/50)/MainFrame.width)+1);
			CreateWorldPanel.locYa= random.nextInt((int) ((MainFrame.height/2-MainFrame.heightButton)/40)+1);
			while(MainFrame.myArray[CreateWorldPanel.locXa][CreateWorldPanel.locYa]==1) {
				CreateWorldPanel.locXa= random.nextInt((int) (25*(MainFrame.width-2*MainFrame.widthButton-MainFrame.width/50)/MainFrame.width)+1);
				CreateWorldPanel.locYa= random.nextInt((int) ((MainFrame.height/2-MainFrame.heightButton)/40)+1);
			}

			MainFrame.myArray[CreateWorldPanel.locXa][CreateWorldPanel.locYa]=1;
			CreateWorldPanel.locX=2*(CreateWorldPanel.locXa*MainFrame.width/50);
			CreateWorldPanel.locY=2*CreateWorldPanel.locYa*20+MainFrame.heightButton;

			JLabel firmObstacle=new JLabel(MainFrame.firmIcon);


			controller.createObstacle("FirmObstacle", CreateWorldPanel.locX, CreateWorldPanel.locY);
			MainFrame.obstacles.add(firmObstacle);
			cwp.add(firmObstacle);
			firmObstacle.setIcon(MainFrame.firmIcon);
			firmObstacle.setBounds(CreateWorldPanel.locX,CreateWorldPanel.locY,MainFrame.width/50,20);

	}
        
        System.out.println(MainFrame.obstacles.size());
        System.out.println(Integer.parseInt(cwp.getNumOfFirmObstacle().getText()));
        assertEquals(MainFrame.obstacles.size(),Integer.parseInt(cwp.getNumOfFirmObstacle().getText()));

}
	
	@Test
	void simpleObstacleSystemCreationTest(){
        for(int i=0;i<Integer.parseInt(cwp.getNumOfSimpleObstacle().getText());i++) {
			Random random= new Random();
			CreateWorldPanel.locXa= random.nextInt((int) (25*(MainFrame.width-2*MainFrame.widthButton-MainFrame.width/50)/MainFrame.width)+1);
			CreateWorldPanel.locYa= random.nextInt((int) ((MainFrame.height/2-MainFrame.heightButton)/40)+1);
			while(MainFrame.myArray[CreateWorldPanel.locXa][CreateWorldPanel.locYa]==1) {
				CreateWorldPanel.locXa= random.nextInt((int) (25*(MainFrame.width-2*MainFrame.widthButton-MainFrame.width/50)/MainFrame.width)+1);
				CreateWorldPanel.locYa= random.nextInt((int) ((MainFrame.height/2-MainFrame.heightButton)/40)+1);
			}

			MainFrame.myArray[CreateWorldPanel.locXa][CreateWorldPanel.locYa]=1;
			CreateWorldPanel.locX=2*(CreateWorldPanel.locXa*MainFrame.width/50);
			CreateWorldPanel.locY=2*CreateWorldPanel.locYa*20+MainFrame.heightButton;

			JLabel simpleObstacle=new JLabel(MainFrame.simpleIcon);


			RunningGamePanel.controller.createObstacle("SimpleObstacle", CreateWorldPanel.locX, CreateWorldPanel.locY);
			MainFrame.obstacles.add(simpleObstacle);
			cwp.add(simpleObstacle);
			simpleObstacle.setIcon(MainFrame.simpleIcon);
			simpleObstacle.setBounds(CreateWorldPanel.locX,CreateWorldPanel.locY,MainFrame.width/50,20);

	}
        
    
        assertTrue(MainFrame.obstacles.size()==Integer.parseInt(cwp.getNumOfSimpleObstacle().getText()));

}
	
}
