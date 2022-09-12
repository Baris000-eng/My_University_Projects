package domain.obstacles;

public class Obstacle implements MovementBehavior{ 


	private String obstacleName;
	private int xLocation;
	private int yLocation;
	private int width;
	private int height;
	private boolean frozen=false;
	private boolean hollow=false;

	private boolean isMoving;

	public boolean isHollow() {
		return hollow;
	}


	public void setHollow(boolean hollow) {
		this.hollow = hollow;
	}


	public Obstacle(String obstacleName, int xLocation, int yLocation) {
		this.obstacleName=obstacleName;
		this.xLocation=xLocation;
		this.yLocation=yLocation;
	}


	public String getObstacleName() {
		return obstacleName;
	}

	public int getxLocation() {
		return xLocation;
	}

	public int getyLocation() {
		return yLocation;
	}

	public void setxLocation(int x) {
		this.xLocation=x;
	}

	public void setyLocation(int y) {
		this.yLocation=y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width=width;
	}

	public void setHeight(int height) {
		this.height=height;
	}
	@Override
	public String toString() {
		return "Obstacle " + obstacleName + " " + xLocation + " "
				+ yLocation;
	}


	@Override
	public void move() {
		// TODO Auto-generated method stub

	}


	public void setFrozen(boolean frozen) {
		// TODO Auto-generated method stub
		this.frozen=frozen;
	}


	public boolean isFrozen() {
		// TODO Auto-generated method stub
		return frozen;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean moving) {
		isMoving=moving;
	}



}
