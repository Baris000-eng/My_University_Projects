package domain.obstacles;

import domain.game.GameInfo;

public class MagicalHex {
	private int xLocation;
	private int yLocation;
	private int dx;
	private int dy;

	public MagicalHex(int x, int y) {
		xLocation=x;
		yLocation=y;
		dx=0;
		dy=-1;
		for(MagicalHexListener lis: GameInfo.magicalHexListeners) {
			lis.onMagicalHexCreation(x, y);
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
		return "Magical Hex " + xLocation + " " + yLocation;
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
