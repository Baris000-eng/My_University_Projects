package domain.player;

public interface RemainingGiftBoxListener {

	void onRemainingCreation(int x, int y);
	void onGiftBoxCreation(int x, int y);
	
	void onRemainingMovement(int index,int x, int y);
	void onGiftBoxMovement(int index,int x, int y);
	
	void onRemainingTouch(int index);
	void onGiftBoxTouch(int index);
}
