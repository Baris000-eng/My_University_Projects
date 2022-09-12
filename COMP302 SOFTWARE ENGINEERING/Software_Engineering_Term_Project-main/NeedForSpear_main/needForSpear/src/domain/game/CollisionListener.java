package domain.game;

public interface CollisionListener {

	void collided(int index, String currentCollidedObstacle, String currentCollisionSide);

}
