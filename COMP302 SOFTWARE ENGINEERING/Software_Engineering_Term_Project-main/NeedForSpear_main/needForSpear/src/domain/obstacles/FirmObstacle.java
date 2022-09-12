package domain.obstacles;


import java.util.Random;

import domain.game.DomainConstants;

public class FirmObstacle extends Obstacle{


	private int numOfHitsRequired;
	private int offset=DomainConstants.phantasmLength/20;
	private int dx=1;
	private int totalMovement=0;

	public FirmObstacle(String obstacleName, int xLocation, int yLocation) {
		super(obstacleName,  xLocation,  yLocation);	
		Random random = new Random();
		numOfHitsRequired=random.nextInt(8)+2;
		setHeight(DomainConstants.rectangleObstacleHeight);
		setWidth(DomainConstants.rectangleObstacleWidth);
		Random rand=new Random();
		int randInt=rand.nextInt(10);
		if(randInt<2) {
			setMoving(true);
		}
	}


	public FirmObstacle(String obstacleName, int xLocation, int yLocation, int numOfHitsRequired) {
		super(obstacleName, xLocation, yLocation);
		setHeight(DomainConstants.rectangleObstacleHeight);
		setWidth(DomainConstants.rectangleObstacleWidth);
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

	public void decrementNumOfHitsRequired() {
		numOfHitsRequired--;
	}

	@Override
	public void move() {
		if(isMoving()) {
			setxLocation(getxLocation()+dx);
			totalMovement+=dx;
			if(totalMovement>=offset || totalMovement<=(-offset)) {
				dx=-dx;
			}
		}

	}

}

