package domain.game;

import java.io.IOException;

import java.util.ArrayList;

import domain.obstacles.ExplosiveObstacle;
import domain.obstacles.FirmObstacle;
import domain.obstacles.GiftObstacle;
import domain.obstacles.Obstacle;
import domain.obstacles.ObstacleFactory;
import domain.obstacles.SimpleObstacle;
import domain.player.EnchantedSphere;
import domain.player.NoblePhantasm;
import domain.player.Player;
import domain.saveLoad.ISaveLoadAdapter;
import domain.saveLoad.SaveLoadFactory;
import java.util.Random; 
//import domain.saveLoad.DatabaseSaveLoadAdapter;  //Database works only locally for now. TXT works very well.
import domain.saveLoad.TxtSaveLoadAdapter;


public class NeedForSpearGame { //controller

	private ISaveLoadAdapter saveLoadAdapter;

	public NeedForSpearGame(String serviceType) {
		saveLoadAdapter = SaveLoadFactory.getInstance().getSaveLoadAdapter(serviceType);
	}


	public void addUser(String username, String password) {
		//MODIFIES: adds the username and password to userNameAndPassword.txt file.
		this.saveLoadAdapter.addUser(username, password);
		
	}

	public void saveGame(String gameName) {
		saveLoadAdapter.saveGame(gameName, GameInfo.currentPlayer,GameInfo.sphere, GameInfo.obstacles);
	}

	public boolean userExist(String username, String password) {
		// returns true if username and password are matching in userNameAndPassword.txt file.
		try {
			return saveLoadAdapter.userExist(username, password);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}


	public void addRandomAbility() {
		getCurrentPlayer().addRandomAbility();
	}

	/*public boolean collisionOccursFromLeftTop(JLabel simpleObstacle, Obstacle obs2) {
		try {
			return Game.obsLeftUpCornerCollision(simpleObstacle, obs2);
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public boolean collisionOccursFromRightTop(JLabel giftObstacle, Obstacle obs2) {
		try {
			return Game.obsRightUpCornerCollision(giftObstacle, obs2);
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public boolean collisionOccursFromRightDown(JLabel giftObstacle, Obstacle obs2) {
		try {
			return Game.obsRightDownCornerCollision(giftObstacle, obs2);
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public boolean collisionOccursFromLeftDown(JLabel giftObstacle, Obstacle obs2) {
		try {
			return Game.obsLeftDownCornerCollision(giftObstacle, obs2);
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}*/

	public boolean usernameExist(String username) {
		try {
			return saveLoadAdapter.usernameExist(username);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Obstacle createObstacle(String typeOfObstacle, int locX, int locY) {
		Obstacle obs=ObstacleFactory.getInstance().getObstacle(typeOfObstacle, locX, locY);
		GameInfo.obstacles.add(obs);
		return obs;
		//
	}

	public void removeObstacle(int locX, int locY) {

		for (Obstacle o:GameInfo.obstacles){
			if (o.getxLocation()==locX && o.getyLocation()==locY) {
				GameInfo.obstacles.remove(o);
				System.out.println("obstacle removed from arraylist.");
				break;
			}
		}
		//
	}


	/*public int[] createRandomObstacle(int id, String typeOfObstacle) {
		// TODO Auto-generated method stub
		Obstacle obs=ObstacleFactory.getInstance().getRandomObstacle(id,typeOfObstacle);
		Game.obstacles.add(obs);

		int arr[]= {obs.getxLocation(),obs.getyLocation()};
		return arr;
	}*/

	public void createEnchantedSphere(int x, int y) {

		if(GameInfo.sphere==null) {
			GameInfo.sphere=new EnchantedSphere(x, y);
		}
	}


	public void updateSphere(int x, int y) {

		GameInfo.sphere.setxLocation(x);
		GameInfo.sphere.setyLocation(y); 
	}

	public void updatePhantasm(int x, int length) {

		GameInfo.phantasm.setxLocation(x);
		GameInfo.phantasm.setLength(length);
	}


	public void setCurrentPlayerUsername(String username) {

		GameInfo.currentPlayerUsername=username;
	}

	public ArrayList<ArrayList> loadGame() throws IOException {
		//System.out.println(Game.currentPlayer.getUsername());
		//System.out.println(txtSaveLoadAdapter.loadGame(Game.currentPlayer.getUsername()));
		return saveLoadAdapter.loadGame(GameInfo.currentPlayer.getUsername());
	}

	public Player createPlayer(String username) {
		return new Player(username);
	}

	public void setCurrentPlayer(Player player) {
		GameInfo.currentPlayer=player;
	}

	public static Player getCurrentPlayer() {
		return GameInfo.currentPlayer;
	}

	public boolean collisionSphereFromDown(Obstacle o, EnchantedSphere sphere) {
		return CollisionChecker.collisionSphereFromDown( o,  sphere);
	}

	public void removeObs(Obstacle o) {
		GameInfo.obstacles.remove(o);
	}

	public void decrementLives() {
		getCurrentPlayer().setLives(getCurrentPlayerLives()-1);
	}

	public void incrementLives() {
		getCurrentPlayer().setLives(getCurrentPlayerLives()+1);
	}

	public int getCurrentPlayerLives() {
		return getCurrentPlayer().getLives();
	}

	public double getCurrentPlayerScore() {
		return getCurrentPlayer().getScore();
	}

	public ArrayList<Obstacle> getObstacles() {
		return GameInfo.obstacles;
	}

	public EnchantedSphere getSphere() {

		return GameInfo.sphere;
	}

	public boolean collisionSphereFromUp(Obstacle o, EnchantedSphere sphere) {

		return CollisionChecker.collisionSphereFromUp(o, sphere);
	}

	public boolean collisionSphereFromRight(Obstacle o, EnchantedSphere sphere) {

		return CollisionChecker.collisionSphereFromRight(o, sphere);
	}

	public boolean collisionSphereFromLeft(Obstacle o, EnchantedSphere sphere) {

		return CollisionChecker.collisionSphereFromLeft(o, sphere);
	}

	public NoblePhantasm getPhantasm() {

		return GameInfo.phantasm;
	}

	public boolean collisionOfEnchantedSphereWithNoblePhantasm(NoblePhantasm phantasm, EnchantedSphere sphere) {

		return CollisionChecker.collisionOfEnchantedSphereWithNoblePhantasm(phantasm, sphere);
	}

	/*public boolean collisionOfRemainingWithNoblePhantasm(NoblePhantasm phantasm, RemainingsOfExplosiveObstacle rem) {

		return Game.collisionOfRemainingWithNoblePhantasm(phantasm, rem);
	}*/

	public void resetObstacles() {
		GameInfo.obstacles.removeAll(GameInfo.obstacles);
	}


	public void updateScore(int second) {   
		getCurrentPlayer().updateScore(second);
	}


	public void updateObstacleLocation(int x, int y, int x2, int y2) {
		// TODO Auto-generated method stub
		for (Obstacle o: GameInfo.obstacles) {
			if(o.getxLocation()==x && o.getyLocation()==y) {
				o.setxLocation(x2);
				o.setyLocation(y2);
				//System.out.println(o);
			}
		}

	}


	public Obstacle createFirmObstacleWithNumHits(int parseInt, int parseInt2, int numHits) {
		// TODO Auto-generated method stub
		Obstacle obs=ObstacleFactory.getInstance().getFirmObstacleWithNumHits(parseInt, parseInt2, numHits);
		GameInfo.obstacles.add(obs);
		return obs;
	}
	
	public int getSimpleObstacleNumber() {
		return SimpleObstacle.getSimpleObstacleNumber();
	}
	
	public int getFirmObstacleNumber() {
		return FirmObstacle.getFirmObstacleNumber();
	}
	public int getExplosiveObstacleNumber() {
		return ExplosiveObstacle.getExplosiveObstacleNumber();
	}
	public int getGiftObstacleNumber() {
		return GiftObstacle.getGiftObstacleNumber();
	}


	public void decreaseNumOfHits(FirmObstacle o) {
		// TODO Auto-generated method stub
		o.setNumOfHitsRequired(o.getNumOfHitsRequired()-1);

	}


	public void decrementChanceGivingAbility() {
		getCurrentPlayer().decrementChanceGivingAbility();
	}
	public void decrementNoblePhantasmExpansionAbility() {
		getCurrentPlayer().decrementNoblePhantasmExpansionAbility();
	}
	public void decrementMagicalHexAbility() {
		getCurrentPlayer().decrementMagicalHexAbility();
	}
	public void decrementUnstoppableEnchantedSphereAbility() {
		getCurrentPlayer().decrementUnstoppableEnchantedSphereAbility();
	}

	public void doubleNoblePhantasm() {
		getPhantasm().setLength(getPhantasm().getLength()*2);
	}

	public void halveNoblePhantasm() {
		getPhantasm().setLength(getPhantasm().getLength()/2);
	}


	public void resetScore() {
		getCurrentPlayer().resetScore();

	}


	/*public boolean collisionOfEnchantedSphereWithNoblePhantasmforExtended(NoblePhantasm phantasm,
			EnchantedSphere sphere) {
		// TODO Auto-generated method stub
		return Game.collisionOfEnchantedSphereWithNoblePhantasmforExtended(phantasm, sphere);
	}*/

}

