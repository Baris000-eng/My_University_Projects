package domain.obstacles;

import domain.game.DomainConstants;


public class HollowObstacle extends Obstacle  {

	
	public HollowObstacle(String obstacleName, int xLocation, int yLocation) {
		super(obstacleName, xLocation, yLocation);
		setHeight(DomainConstants.rectangleObstacleHeight);
		setWidth(DomainConstants.rectangleObstacleWidth);
	}

	@Override
	public void move() {
		
	}
	
	

}