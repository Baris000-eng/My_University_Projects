package domain.obstacles;

import domain.game.DomainConstants;

public class GiftObstacle extends Obstacle {
	
	
	public GiftObstacle(String obstacleName, int xLocation, int yLocation) {
		super(obstacleName, xLocation, yLocation);
		setHeight(DomainConstants.rectangleObstacleHeight);
		setWidth(DomainConstants.rectangleObstacleWidth);
	}
	
	@Override
	public void move() {
		//gift obstacle does not move.
	}

	
}
