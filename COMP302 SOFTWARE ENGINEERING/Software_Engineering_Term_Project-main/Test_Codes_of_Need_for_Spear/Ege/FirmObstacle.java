package domain.obstacles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class FirmObstacle extends Obstacle{

	
	public static int getFirmObstacleNumber() {
		return firmObstacleNumber;
	}

	public void setFirmObstacleNumber(int firmObstacleNumber) {
		this.firmObstacleNumber = firmObstacleNumber;
	}


	private int numOfHitsRequired;
	
	private static int firmObstacleNumber= 10;
	
	
	
	public FirmObstacle(String obstacleName, int xLocation, int yLocation) {
		super(obstacleName,  xLocation,  yLocation);	
		Random random = new Random();
		numOfHitsRequired=random.nextInt(8)+2;
	}

	public FirmObstacle(int numHits, String obstacleName, int xLocation, int yLocation) {
		super(numHits, obstacleName, xLocation, yLocation);
		// TODO Auto-generated constructor stub
	}

	public FirmObstacle(String obstacleName, int xLocation, int yLocation, int numOfHitsRequired) {
		super(obstacleName, xLocation, yLocation);
	}
	
	public FirmObstacle(int x, int y, int numHits) {
		super("FirmObstacle", x, y);
		this.numOfHitsRequired=numHits;
	}

   
	public int getNumOfHitsRequired() {
		return numOfHitsRequired;
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + numOfHitsRequired;
	}


	public void setNumOfHitsRequired(int i) {
		numOfHitsRequired=i;
		
	}



}

