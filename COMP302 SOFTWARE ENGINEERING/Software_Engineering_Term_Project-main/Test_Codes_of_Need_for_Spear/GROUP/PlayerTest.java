package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.player.Player;

class PlayerTest {

	public static Player player;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		if(player==null) {
			player= new Player();
		}
	}

	@AfterEach
	void tearDown() throws Exception {
		player=new Player(); //to reset after each test.
	}


	@Test
	public void repOk() {
		assertTrue(player.repOk());
	}

	@Test 
	public void initializationTest() {
		//TESTING THE getter method for the player's lives
		int lives= player.getLives();
		assertTrue(player.repOk());
		assertEquals(lives,3); //To write this test line, we should look to the implementation of the getter method 
		//for the player's lives and to the Player's constructor. 
		//So, this is an example of glass box testing.
		double score= player.getScore();
		assertTrue(player.repOk());
		assertEquals(score,0); 

	}

	@Test
	public void decrementLivesTest() {

		int livesInitial= player.getLives(); 
		player.decrementLives();
		assertTrue(player.repOk());
		assertTrue(player.getLives()==livesInitial-1);
		player.decrementLives();
		assertTrue(player.repOk());
		assertTrue(player.getLives()==livesInitial-2);

		//To write this test line, we should look the implementation of the decrementLives() method. So, this line is an example of glassbox testing. 
		//So, this line is a glassbox testing example.
	}

	@Test
	void incrementLivesTest() {
		int livesInitial= player.getLives(); 
		player.incrementLives();
		assertTrue(player.repOk());
		assertTrue(player.getLives()==livesInitial+1);
		player.incrementLives();
		assertTrue(player.repOk());
		assertTrue(player.getLives()==livesInitial+2);
	}

	@Test 
	void decrementIncrementMixtureTest() {
		int livesInitial= player.getLives();

		for(int i=0;i<5;i++) {
			player.incrementLives();
			assertTrue(player.repOk());
		}

		for(int j=0;j<2;j++) {
			player.decrementLives();
			assertTrue(player.repOk());
		}

		int livesAfter= player.getLives();

		assertTrue(livesAfter==livesInitial+3);
	}
	
	@Test
	public void updateScoreTest() {
		
	    int second= 50;
		player.updateScore(second);//Glassbox testing example for the updateScore method, we should look the implementation of updateScore.
		
		assertTrue(player.getScore()==6);
		
		second=second+1;
		
		player.updateScore(second);
		assertTrue(player.repOk());
		assertTrue(player.getScore()== 6 + 300.0/51);
		
		player.resetScore(); 
		assertTrue(player.repOk());
		assertTrue(player.getScore()==0);
	}
	
	@Test
	public void changeMagicalAbilityCountTest() {
		
		assertEquals(player.playerRep[2], 0);
		assertEquals(player.playerRep[3], 0);
		assertEquals(player.playerRep[4], 0);
		assertEquals(player.playerRep[5], 0);
		
		player.incrementChanceGivingAbility();
		assertTrue(player.repOk());
		assertEquals(player.playerRep[2], 1);
		
		player.incrementNoblePhantasmExpansionAbility();
		assertTrue(player.repOk());
		assertEquals(player.playerRep[3], 1);
		player.incrementNoblePhantasmExpansionAbility();
		player.incrementNoblePhantasmExpansionAbility();
		player.decrementNoblePhantasmExpansionAbility();
		assertEquals(player.playerRep[3], 2);
		
		//currently abilities 1, 2, 0, 0
		player.addRandomAbility();
		assertTrue(player.repOk());
		assertTrue(    (player.playerRep[2]==2 && player.playerRep[3]==2 && player.playerRep[4]==0 && player.playerRep[5]==0)
				     ||(player.playerRep[2]==1 && player.playerRep[3]==3 && player.playerRep[4]==0 && player.playerRep[5]==0)
				     ||(player.playerRep[2]==1 && player.playerRep[3]==2 && player.playerRep[4]==1 && player.playerRep[5]==0)
				     ||(player.playerRep[2]==1 && player.playerRep[3]==2 && player.playerRep[4]==0 && player.playerRep[5]==1));
	}



}
