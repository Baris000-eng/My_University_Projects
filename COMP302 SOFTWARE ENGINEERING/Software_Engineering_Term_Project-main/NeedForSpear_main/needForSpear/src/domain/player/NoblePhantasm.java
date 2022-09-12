package domain.player;

import domain.game.GameInfo;

public class NoblePhantasm{
	
	private int length;
	private int xLocation;
	
	public NoblePhantasm(int xLocation, int length) {
		this.xLocation = xLocation;
		this.length = length;
	}
	
	public void setLength(int x) {
		this.length = x;
		for(PhantasmMovementLengthListener lis: GameInfo.phantasmListeners) {
			lis.inLengthChange(x);
		}
	}
	
	@Override
	public String toString() {
		return "NoblePhantasm [length=" + length + ", xLocation=" + xLocation + "]";
	}

	public int getLength() {
		return length;
	}
	
	
	public int getxLocation() {
		return xLocation;
	}
	
	public void setxLocation(int xLocation) {
		for(PhantasmMovementLengthListener lis: GameInfo.phantasmListeners) {
			lis.inXLocationChangeOfPhantasm(xLocation);
		}
		this.xLocation = xLocation;
	}
	

}
