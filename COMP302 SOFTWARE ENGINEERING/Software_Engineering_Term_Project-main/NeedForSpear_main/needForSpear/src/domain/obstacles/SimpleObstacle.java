package domain.obstacles;

import java.util.Random;

import domain.game.DomainConstants;

public class SimpleObstacle extends Obstacle  {


	private int totalMovement=0;
	private int dx=1;
	private int offset=DomainConstants.phantasmLength/20;


	public SimpleObstacle(String obstacleName, int xLocation, int yLocation) {
		super(obstacleName, xLocation, yLocation);
		setHeight(DomainConstants.rectangleObstacleHeight);
		setWidth(DomainConstants.rectangleObstacleWidth);
		Random rand=new Random();
		int randInt=rand.nextInt(10);
		if(randInt<2) {
			setMoving(true);
		}
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
