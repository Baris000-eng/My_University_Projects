package domain.obstacles;

import domain.game.GameInfo;
import domain.player.RemainingGiftBoxListener;

public class Remaining {
	private int xLocation;
	private int yLocation;
	private int dx;
	private int dy;

	public Remaining(int x, int y, int dx) {
		xLocation=x;
		yLocation=y;
		this.dx=dx;
		dy=1;
		for(RemainingGiftBoxListener lis: GameInfo.remGiftListeners) {
			lis.onRemainingCreation(x, y);
		}
	}

	public void setxLocation(int xLocation) {
		this.xLocation = xLocation;
		
	}

	public void setyLocation(int yLocation) {
		this.yLocation = yLocation;
	}

	
	public int getxLocation() {
		return xLocation;
	}

	public int getyLocation() {
		return yLocation;
	}

	@Override
	public String toString() {
		return "Remaining " + xLocation + " " + yLocation;
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
