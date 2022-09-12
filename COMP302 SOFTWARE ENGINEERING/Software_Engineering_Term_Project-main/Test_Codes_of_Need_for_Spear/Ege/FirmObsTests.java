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
import domain.game.NeedForSpearGame;
import domain.obstacles.FirmObstacle;
import domain.obstacles.Obstacle;
import domain.player.EnchantedSphere;

class FirmObsTests {
	//Writer: Ege Secilmis 

	public static FirmObstacle firmObstacle;
	public static NeedForSpearGame controller;

	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		controller= new NeedForSpearGame("txt");
		GameInfo.sphere=new EnchantedSphere(0, 0); //Initialization of controller and EnchantedSphere.
		controller.updateSphere(21, 4);
	}


	@BeforeEach
	void setUp() throws Exception {
		firmObstacle = new FirmObstacle("FirmObstacle", 20, 20); //Initialize the firmObstacle for different tests.
	}


	//BB test
	//Since collisionSphereFromUp method requires a nonnull object, when we give a null object as a parameter
	//we can expect a NullPointerException.
	@Test
	void testCollisionSphereFromDownException() {
		assertThrows(NullPointerException.class, ()-> {
			CollisionChecker.collisionSphereFromUp(firmObstacle, null); 
		});

		assertThrows(NullPointerException.class, ()-> {
			CollisionChecker.collisionSphereFromUp(null, GameInfo.sphere); 
		});
	}


	//GB test
	//To check if the collision occurs properly, we should look at the code.
	@Test
	void testCollisionSphereFromDown() {
		assertTrue(CollisionChecker.collisionSphereFromUp(firmObstacle, GameInfo.sphere) == true);
	}


	//BB test (this method tests the FirmObstacle method which is called in @BeforeEach)
	//If we look at the specification of FirmObstacle constructor, we can see that an instance of
	//FirmObstacl (therefore an instance of Obstacle) is created.
	@Test
	void testFirmObstacle() {
		assertTrue(firmObstacle instanceof FirmObstacle);
		assertTrue(firmObstacle instanceof Obstacle);
	}


	//GB test (this method tests the FirmObstacle method which is called in @BeforeEach)
	//To check the attributes of FirmObstacle (e.g. the number of hits required to destroy that FirmObstacle),
	//we should look at the code.
	@Test
	void testFirmObstacleRequiredHits() {
		assertTrue(2 <= firmObstacle.getNumOfHitsRequired() && 9 >= firmObstacle.getNumOfHitsRequired());
	}


	//BB Test
	//By looking at the specification of decreaseNumOfHits only, we can understand that the number of 
	//hits required to destroy a firm obstacle will be smaller after this method is called.
	@Test
	void testGetNumOfHitsRequiredBB() {
		int numHitsBefore=firmObstacle.getNumOfHitsRequired();
		controller.decreaseNumOfHits(firmObstacle);
		assertTrue(firmObstacle.getNumOfHitsRequired() < numHitsBefore);
	}



	//GB test
	//To know the amount of decrease, we should check to code. Then we can see that the number of hits
	//required to destroy a firm obstacle is decreased by one.
	@Test
	void testGetNumOfHitsRequiredGB() {
		int numHitsBefore=firmObstacle.getNumOfHitsRequired();
		controller.decreaseNumOfHits(firmObstacle);
		assertTrue(firmObstacle.getNumOfHitsRequired() == numHitsBefore-1);
	}


	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}


	@AfterEach
	void tearDown() throws Exception {
	}
}