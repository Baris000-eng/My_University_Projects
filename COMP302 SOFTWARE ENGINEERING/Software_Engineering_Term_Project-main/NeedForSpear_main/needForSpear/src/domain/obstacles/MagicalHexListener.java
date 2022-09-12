package domain.obstacles;

public interface MagicalHexListener {
	
	void onMagicalHexCreation(int x, int y);
	void onMagicalHexMovement(int index,int x, int y);
	void onMagicalHexCollision(int index);

}
