package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domain.game.GameInfo;
import domain.game.NeedForSpearGame;
import domain.player.EnchantedSphere;
import domain.player.Player;
import domain.saveLoad.TxtSaveLoadAdapter;

class LoadGameTest {

	private static TxtSaveLoadAdapter txtSaveLoad=new TxtSaveLoadAdapter();
	private static NeedForSpearGame controller=new NeedForSpearGame("txt");

	private static Player player=new Player("playerName");
	private static EnchantedSphere sphere=new EnchantedSphere(0,0);

	private static int noSavedGameSize;
	private static int savedGame1Size;
	private static int gameCountAfterSecondGameSaved;
	private static int sizeOfSecondGame;
	private static int gameCountAfterFirstGameSaved;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		//To store the savedWorlds.txt before tests.
		File inputFile = new File("savedWorlds.txt");
		File tempFile = new File("savedWorldsTest.txt");

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		String lineToRemove = "Player playerName 0 3";
		String currentLine;

		while((currentLine = reader.readLine()) != null) {
			String trimmedLine = currentLine.trim();
			if(trimmedLine.equals(lineToRemove)) break;
			writer.write(currentLine + System.getProperty("line.separator"));
		}

		writer.close(); 
		reader.close(); 

		//Set up for tests
		controller.createObstacle("SimpleObstacle", 5, 20);
		controller.createObstacle("GiftObstacle", 30, 60);
		controller.createObstacle("FirmObstacle", 30, 99);
		controller.createObstacle("ExplosiveObstacle", 60, 45);

		//to test when user does not have any saved game.
		noSavedGameSize=txtSaveLoad.loadGame(player.getUsername()).get(0).size();

		//to test after saving game
		txtSaveLoad.saveGame("gameName1", player, sphere, GameInfo.obstacles); //player saves game.
		gameCountAfterFirstGameSaved=txtSaveLoad.loadGame(player.getUsername()).size();
		savedGame1Size=txtSaveLoad.loadGame(player.getUsername()).get(0).size();
		
		//to test after removing an obstacle and saving the new game state.
		GameInfo.obstacles.remove(2); //remove firm obstacle.
		txtSaveLoad.saveGame("gameName2", player, sphere, GameInfo.obstacles); //save new game.
		gameCountAfterSecondGameSaved=txtSaveLoad.loadGame(player.getUsername()).size();
		sizeOfSecondGame=txtSaveLoad.loadGame(player.getUsername()).get(1).size();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		//put original savedWorlds.txt back (the version before tests) so that tests do not change savedWorlds.txt
		File fileToDelete = new File("savedWorlds.txt");
		fileToDelete.delete();

		File fileOldName = new File("savedWorldsTest.txt");
		File fileNewName = new File("savedWorlds.txt");
		fileOldName.renameTo(fileNewName);
	}


	@Test
	void loadEmptyTest() throws Exception {
		assertTrue(noSavedGameSize==0);  //GLASS-BOX TESTING since it requires checking code what is returned when player does not have any game.
		//initially player does not have saved game. so it returns arraylist containing an empty arraylist.
	}

	@Test
	void loadTest() throws Exception {
		//test after saving 1 game
		//now loadgame returns an arraylist containing a nonempty arraylist.
		assertTrue(gameCountAfterFirstGameSaved==1); //BLACK-BOX TESTING since just specification is considered.
		assertTrue(savedGame1Size==18);  //GLASS-BOX TESTING since code is also checked. (what is stored etc.)
		
		/*Each arraylist in arraylist contains score, lives, gameName, sphereXLoc, sphereYLoc, (obstacle, obstaclename, xloc, yloc) for each obstacle other than firm.
		 *For firm obstacles contains numofhitsrequired is also contained additionally.
		 *So, number of elements in each arraylist contained in main arraylist = 5 + (num of obstacles other than firm * 3) + (num of firm obstacles * 4)
		 *So, first game contains, 5 + 3*3 + 1*4 = 18 elements.*/
	}

	@Test
	void removeSaveLoadTest() throws Exception {
		assertTrue(gameCountAfterSecondGameSaved==2);  //BLACK-BOX TESTING since just specification is considered.
		assertTrue(sizeOfSecondGame==14);  //GLASS-BOX TESTING since code is also checked. (what is stored etc.)
	}

	@Test
	void savedToCorrectUserTest() throws Exception {
		//BLACK-BOX TESTING since we except user with username "anotherUser" not to have any saved game by just looking at specification.
		assertTrue(txtSaveLoad.loadGame("anotherUser").size()==1);  
		assertTrue(txtSaveLoad.loadGame("anotherUser").get(0).size()==0);
	}

	@Test
	void loadNullTest() throws Exception {
		//GLASS-BOX TESTING since giving null is not specified in specifications. It requires to check implementation.
		assertTrue(txtSaveLoad.loadGame(null).size()==1);  
	}
	
	//NOTE: The method contains while loop, but since the number of iterations of while loop depends also in the initial
	//		saved games, it was not possible to do 0-1-2 iterations for glass-box testing.
	//      I could have done it deleting all the saved games, but i think there is not need for such a test
	//      since the while loop is just for reading next line.
}
