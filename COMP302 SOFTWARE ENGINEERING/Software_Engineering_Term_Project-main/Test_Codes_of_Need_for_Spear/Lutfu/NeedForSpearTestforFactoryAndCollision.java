
package test;

import static org.junit.Assert.assertThrows;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.game.CollisionChecker;
import domain.game.GameInfo;
import domain.game.CollisionChecker;
import domain.obstacles.Obstacle;
import domain.game.NeedForSpearGame;
import domain.obstacles.Obstacle;
import domain.player.EnchantedSphere;
import domain.saveLoad.ISaveLoadAdapter;
import domain.saveLoad.SaveLoadFactory;
import domain.saveLoad.TxtSaveLoadAdapter;
import domain.game.NeedForSpearGame;


class NeedForSpearTestforFactoryAndCollision {
	
	   //DONE BY: Lutfu Ato 
	


	@Test
	public void CollisionCheckerExpectedNullPointerException() {
		//BlackBox testing, checking if first or second argument is null, if so then it should throw NullPointerException. This is BlackBox testing, test written by lookin at the specifications.
		Obstacle obs1 = new Obstacle("FirmObstacle", 10,15);
		assertThrows(NullPointerException.class, ()-> {
			 CollisionChecker.collisionObsFromLeft(null, null); // BlackBox, throws exception
		});
		assertThrows(NullPointerException.class, ()-> {
			CollisionChecker.collisionObsFromLeft(obs1, null); // BlackBox, throws exception
		});
		assertThrows(NullPointerException.class, ()-> {
			CollisionChecker.collisionObsFromLeft(null, obs1); // BlackBox, throws exception
		});
	}
	
	
	@Test
	public void CollisionCheckerMustBeTrue() {
		//test if it returns true in case of a collision, this is BlackBox testing, test written by looking at the specifications.
		Obstacle obs1= new Obstacle("FirmObstacle", 42,42);
		Obstacle obs2 = new Obstacle("FirmObstacle", 42,43);
		assertTrue(CollisionChecker.collisionObsFromLeft(obs1,obs2));  //BlackBox test    		
	}
	
	@Test
	public void CollisionCheckerMustBeFalse() {
		//test if it returns false in case of a non collision of the obstacles. This is BlackBox testing, test written by looking at the specifications.
		Obstacle obs1= new Obstacle("FirmObstacle", 42,42);
		Obstacle obs2 = new Obstacle("FirmObstacle", 10,10);
		assertFalse(CollisionChecker.collisionObsFromLeft(obs1,obs2));  //BlackBox test
		
	}
	
	@Test
	public void CollisionCheckerAliasing() {
		//test for Aliasing (From the lecture pdf), it should return true since they collide, this is GlassBox testing, test written by looking at the code
		Obstacle obs1= new Obstacle("FirmObstacle", 42,42);
		assertTrue(CollisionChecker.collisionObsFromLeft(obs1,obs1));  //GlassBox test
	}
	
	@Test
	public void  saveLoadAdapterInstanceChecker() {
		//Check if the returned value is an instance of TxtSaveLoadAdapter if the argument to the getSaveLoadAdapter() is "txt.
		//This test is written by looking at the code, so it is GlassBox test.
		ISaveLoadAdapter saveLoadAdapter = SaveLoadFactory.getInstance().getSaveLoadAdapter("txt"); 
		assertTrue(saveLoadAdapter instanceof TxtSaveLoadAdapter);  //GlassBox test
		
		
	}

	
	@Test
	public void  saveLoadAdapterMustBeTrue() {
		//Check if the user is correctly added by addUser() function. This test is written by looking at the specifications, so it is BlackBox.
		NeedForSpearGame controller = new NeedForSpearGame("txt");
		controller.addUser("lutfu", "ato123");
		assertTrue(controller.userExist("lutfu", "ato123")); //BlackBox test.
		
	}
	
	@Test
	public void saveLoadAdapterMustBeFalse() {
		//Check if it returns false when users that do not exist are checked with userExist() function. 
		//This test is written by looking at the specifications, so it is BlackBox.
		NeedForSpearGame controller = new NeedForSpearGame("txt");
		assertFalse(controller.userExist("notLutfu", "ato123"));
	}
}


