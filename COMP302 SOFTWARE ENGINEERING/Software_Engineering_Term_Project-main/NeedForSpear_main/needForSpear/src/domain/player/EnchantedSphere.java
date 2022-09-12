package domain.player;

import domain.game.GameLogic;
import domain.game.GameInfo;
import domain.game.SphereMovementListener;

public class EnchantedSphere{

	
	private int xLocation;
	private int yLocation;
	private int dx;
	private int dy;

	public EnchantedSphere(int x, int y) {
		xLocation=x;
		yLocation=y;
		dx=2;
		dy=2;
	}

	public void setxLocation(int xLocation) {
		this.xLocation = xLocation;
		for (SphereMovementListener movementListener: GameLogic.movementListeners) {
			movementListener.onSphereMovement(GameInfo.sphere.getxLocation(), GameInfo.sphere.getyLocation());
		}
	}

	public void setyLocation(int yLocation) {
		this.yLocation = yLocation;
		for (SphereMovementListener movementListener: GameLogic.movementListeners) {
			movementListener.onSphereMovement(GameInfo.sphere.getxLocation(), GameInfo.sphere.getyLocation());
		}
	}

	
	public int getxLocation() {
		return xLocation;
	}

	public int getyLocation() {
		return yLocation;
	}

	@Override
	public String toString() {
		return "EnchantedSphere " + xLocation + " " + yLocation;
	}
	
	
	public int getDx() {
		return dx;
	}


	public int getDy() {
		return dy;
	}


	public void setDx(int dx) {
		this.dx = dx;
	}
	
	public void setDy(int dy) {
		this.dy= dy;
	}

}
