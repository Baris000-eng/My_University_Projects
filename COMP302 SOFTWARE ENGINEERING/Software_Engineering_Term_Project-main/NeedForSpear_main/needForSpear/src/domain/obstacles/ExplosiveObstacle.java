package domain.obstacles;

import java.util.Random;

import domain.game.DomainConstants;

public class ExplosiveObstacle extends Obstacle{


	private int radians=0;
	private int velocity=1;

	public ExplosiveObstacle(String obstacleName, int xLocation, int yLocation) {
		super(obstacleName, xLocation, yLocation);
		setHeight(2*DomainConstants.circularObstacleRadius);
		setWidth(2*DomainConstants.circularObstacleRadius);
		Random rand=new Random();
		int randInt=rand.nextInt(10);
		if(randInt<2) {
			setMoving(true);
		}
	}


	@Override
	public void move() {
		if(isMoving()) {
			radians+=velocity;
			if(Math.cos(this.radians)>0) {
				this.setxLocation(this.getxLocation()+2);
			} else {
				this.setxLocation(this.getxLocation()-2);
			}
			if(Math.sin(this.radians)>0) {
				this.setyLocation(this.getyLocation()+2);
			} else {
				this.setyLocation(this.getyLocation()-2);
			}
		}



	}


}
