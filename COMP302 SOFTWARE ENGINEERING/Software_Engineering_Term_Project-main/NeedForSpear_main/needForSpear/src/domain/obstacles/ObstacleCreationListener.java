package domain.obstacles;

public interface ObstacleCreationListener {
	
	void onSimpleObstacleCreation(int x, int y);
	void onFirmObstacleCreation(int x, int y, int numHits);
	void onExplosiveObstacleCreation(int x, int y);
	void onGiftObstacleCreation(int x, int y);
	
	
	void onMissingNumberEntered();
	void onExceedingNumberEntered(int totalNum);
	void onPutOnAnotherObstacle();
	void onClickOutsideMap();
	void onNoObstacleChosen();
	
	
}
