package domain.game;

import java.io.IOException;
import java.util.*;

import domain.obstacles.FirmObstacle;
import domain.obstacles.MagicalHex;
import domain.obstacles.MagicalHexListener;
import domain.obstacles.Obstacle;
import domain.obstacles.ObstacleFactory;
import domain.obstacles.ObstacleMovementListener;
import domain.player.EnchantedSphere;
import domain.player.MagicalAbilityCountListener;
import domain.player.MagicalAbilityListener;
import domain.player.NoblePhantasm;
import domain.player.PhantasmMovementLengthListener;
import domain.player.Player;
import domain.player.RemainingGiftBoxListener;
import domain.player.ScoreLivesListener;
import domain.saveLoad.ISaveLoadAdapter;
import domain.saveLoad.SaveLoadFactory;
import domain.ymir.FrozenListener;
import domain.ymir.HollowPurpleListener;

//import domain.saveLoad.DatabaseSaveLoadAdapter;  //Database works only locally (for just login-register). TXT works very well.


public class NeedForSpearGame { //controller

	private ISaveLoadAdapter saveLoadAdapter;

	public NeedForSpearGame(String serviceType) {
		saveLoadAdapter = SaveLoadFactory.getInstance().getSaveLoadAdapter(serviceType);
	}


	public void addUser(String username, String password) {
		//MODIFIES: adds the username and password to userNameAndPassword.txt file.
		this.saveLoadAdapter.addUser(username, password);

	}

	public void useChanceGivingAbility() {
		if(getNumOfChanceGiving()>0) {
			getCurrentPlayer().incrementLives();
			getCurrentPlayer().decrementChanceGivingAbility();
		}
	}


	public void saveGame(String gameName) {
		saveLoadAdapter.saveGame(gameName);
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
	

	public int getSphereDx() {
		return GameInfo.sphere.getDx();
	}

	public int getSphereDy() {
		return  GameInfo.sphere.getDy();
	}


	public void addRandomAbility() {
		getCurrentPlayer().addRandomAbility();
	}


	public boolean usernameExist(String username) {
		try {
			return saveLoadAdapter.usernameExist(username);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}


	public void createEnchantedSphere(int x, int y) {

		if(GameInfo.sphere==null) {
			GameInfo.sphere=new EnchantedSphere(x, y);
		}
	}


	public static void updateSphere(int x, int y) {
		GameInfo.sphere.setxLocation(x);
		GameInfo.sphere.setyLocation(y); 
	}


	public ArrayList<ArrayList<String>> loadGame() throws IOException {

		return saveLoadAdapter.loadGame(GameInfo.currentPlayer.getUsername());
	}

	public Player createPlayer(String username) {
		return new Player(username);
	}

	public void setCurrentPlayer(Player player) {
		GameInfo.currentPlayer=player;
	}

	public Player getCurrentPlayer() {
		return GameInfo.currentPlayer;
	}

	public boolean collisionSphereFromDown(Obstacle o, EnchantedSphere sphere) {
		return GameLogic.collisionSphereFromDown( o,  sphere);
	}

	public void removeObs(Obstacle o) {
		GameInfo.obstacles.remove(o);
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
		return GameLogic.collisionSphereFromUp(o, sphere);
	}

	public boolean collisionSphereFromRight(Obstacle o, EnchantedSphere sphere) {
		return GameLogic.collisionSphereFromRight(o, sphere);
	}

	public boolean collisionSphereFromLeft(Obstacle o, EnchantedSphere sphere) {
		return GameLogic.collisionSphereFromLeft(o, sphere);
	}

	public NoblePhantasm getPhantasm() {
		return GameInfo.phantasm;
	}

	public boolean collisionOfEnchantedSphereWithNoblePhantasm(NoblePhantasm phantasm, EnchantedSphere sphere) {

		return GameLogic.collisionOfEnchantedSphereWithNoblePhantasm(phantasm, sphere);
	}



	public Obstacle createFirmObstacleWithNumHits(int parseInt, int parseInt2, int numHits) {

		Obstacle obs=ObstacleFactory.getInstance().getFirmObstacleWithNumHits(parseInt, parseInt2, numHits);
		GameInfo.obstacles.add(obs);
		return obs;
	}


	public void decreaseNumOfHits(FirmObstacle o) {

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

	public void startCollisionChecking() {
		GameLogic.timer.start();
		GameLogic.isPaused=false;
	}

	public void stopCollisionChecking() {
		GameLogic.timer.stop();
		GameLogic.isPaused=true;
	}


	public void useUnstoppableEnchantedSphereAbility() {
		GameInfo.currentPlayer.useUnstoppableEnchantedSphereAbility();
	}

	public void  useNoblePhantasmExpansionAbility() {
		GameInfo.currentPlayer.useNoblePhantasmExpansionAbility();
	}


	public static boolean returnUnstoppableActivity() {
		return GameInfo.currentPlayer.isUnstoppableActivated();
	}

	public void reverseXMovement() {
		GameInfo.sphere.setDx(GameInfo.sphere.getDx()*(-1));
	}

	public void reverseYMovement() {
		GameInfo.sphere.setDy(GameInfo.sphere.getDy()*(-1));
	}

	public void activateYmir() {
		GameInfo.ymir.setActivated(true);
	}


	public void deactivateYmir() {
		GameInfo.ymir.setActivated(false);

	}

	public void setMapBoundaries(int rightboundaryformap, int upperboundaryformap, int lowerboundaryformap) {
		DomainConstants.rightBoundaryForMap=rightboundaryformap;
		DomainConstants.upperBoundaryForMap=upperboundaryformap;
		DomainConstants.lowerBoundaryForMap=lowerboundaryformap;
	}


	public void setPhantasmAndObstacleDimensions(int phantasmlength, int rectangleobstaclewidth) {
		DomainConstants.phantasmLength=phantasmlength;
		DomainConstants.rectangleObstacleWidth=rectangleobstaclewidth;
	}


	public void setInitialPositionsOfSphereAndPhantasm(int sphereinitialx, int sphereinitialy, int phantasminitialx, int phantasminitialy) {
		DomainConstants.sphereInitialX=sphereinitialx;
		DomainConstants.sphereInitialY=sphereinitialy;
		DomainConstants.phantasmInitialX=phantasminitialx;
		DomainConstants.phantasmInitialY=phantasminitialy;
	}

	public void setMovementAmountOfPhantasm(int phantasmmovement) {
		DomainConstants.phantasmMovement=phantasmmovement;
	}

	public int getNumOfChanceGiving() {

		return GameInfo.currentPlayer.getMagicalAbilities()[0];
	}
	public int getNumOfPhantasmExpansion() {

		return GameInfo.currentPlayer.getMagicalAbilities()[1];
	}
	public int getNumOfMagicalHex() {

		return GameInfo.currentPlayer.getMagicalAbilities()[2];
	}
	public int getNumOfUnstoppable() {

		return GameInfo.currentPlayer.getMagicalAbilities()[3];
	}


	public void movePhantasmRight() {
		if(GameInfo.phantasm.getxLocation() < ((DomainConstants.width*39/50) - DomainConstants.width/10)) {
			GameInfo.phantasm.setxLocation(GameInfo.phantasm.getxLocation()+DomainConstants.phantasmMovement);
		}
	}


	public void movePhantasmLeft() {
		if(GameInfo.phantasm.getxLocation() > 0) {
			GameInfo.phantasm.setxLocation(GameInfo.phantasm.getxLocation()-DomainConstants.phantasmMovement);
		}
	}


	public boolean isSpeedDoubled() {
		return GameLogic.speedDoubled;
	}


	public void halveSpeed() {
		if (isSpeedDoubled()) {
			DomainConstants.phantasmMovement/=2;
			GameLogic.speedDoubled=false;
		}

	}


	public void doubleSpeed() {
		if(!isSpeedDoubled()) {
			DomainConstants.phantasmMovement*=2;
			GameLogic.speedDoubled=true;
		}
	}


	public void useMagicalHexAbility() {
		if(getNumOfMagicalHex()>0) {
			GameInfo.magicalHexAbilityActivated=true;
			for(MagicalAbilityListener lis: GameInfo.magicalAbilityListeners) {
				lis.inMagicalHexAbility();
			}
			GameInfo.currentPlayer.decrementMagicalHexAbility();
			new java.util.Timer().schedule( 

					new java.util.TimerTask() {
						@Override
						public void run() {

							for(MagicalAbilityListener lis: GameInfo.magicalAbilityListeners) {
								lis.inEndMagicalHexAbility();
							}
							GameInfo.magicalHexAbilityActivated=false;


						}
					}, DomainConstants.activationTime
					);
		}
	}


	public void fireMagicalHex() {
		if(GameInfo.magicalHexAbilityActivated) {
			MagicalHex magicalHex1=new MagicalHex(GameInfo.phantasm.getxLocation(), DomainConstants.phantasmInitialY);
			MagicalHex magicalHex2=new MagicalHex(GameInfo.phantasm.getxLocation()+GameInfo.phantasm.getLength(), DomainConstants.phantasmInitialY);
			GameInfo.magicalHexes.add(magicalHex1);
			GameInfo.magicalHexes.add(magicalHex2);
		}
	}


	public void resetGame() {

		GameInfo.currentPlayer.resetScore();

		GameInfo.obstacles.clear();

		GameInfo.sphere.setxLocation(DomainConstants.sphereInitialX);
		GameInfo.sphere.setyLocation(DomainConstants.sphereInitialY);

		GameInfo.phantasm.setxLocation(DomainConstants.phantasmInitialX);

		GameInfo.currentPlayer.getMagicalAbilities()[0]=0;
		GameInfo.currentPlayer.getMagicalAbilities()[1]=0;
		GameInfo.currentPlayer.getMagicalAbilities()[2]=0;
		GameInfo.currentPlayer.getMagicalAbilities()[3]=0;

		GameInfo.giftBoxes.clear();
		GameInfo.remainings.clear();
		GameInfo.magicalHexes.clear();

		stopCollisionChecking();
	}



	public void setWidthHeight(int width, int height, int widthbutton, int heightbutton) {
		DomainConstants.width=width;
		DomainConstants.height=height;
		DomainConstants.widthButton=widthbutton;
		DomainConstants.heightButton=heightbutton;
	}


	public void setYmirDuration(int period) {
		DomainConstants.ymirAbilityPeriod=period;
	}


	public String getCurrentPlayerUsername() {
		return GameInfo.currentPlayer.getUsername();
	}


	public void resetObstacles() {
		GameInfo.obstacles.clear();
	}


	//For RunningGamePanel to Access Listeners.
	public ArrayList<ScoreLivesListener> getScoreLivesListeners(){
		return GameInfo.scoreLivesListeners;
	}

	public ArrayList<CollisionListener> getCollisionListeners(){
		return GameLogic.collisionListeners;
	}

	public ArrayList<SphereMovementListener> getMovementListeners(){
		return GameLogic.movementListeners;
	}

	public ArrayList<MagicalAbilityListener> getMagicalAbilityListeners(){
		return GameInfo.magicalAbilityListeners;
	}

	public ArrayList<PhantasmMovementLengthListener> getPhantasmListeners(){
		return GameInfo.phantasmListeners;
	}

	public ArrayList<RemainingGiftBoxListener> getRemGiftListeners(){
		return GameInfo.remGiftListeners;
	}

	public ArrayList<MagicalHexListener> getMagicalHexListeners(){
		return GameInfo.magicalHexListeners;
	}

	public ArrayList<ResetListener> getResetListeners(){
		return GameInfo.resetListeners;
	}

	public ArrayList<MagicalAbilityCountListener> getMagicalAbilityCountListeners(){
		return GameInfo.magicalAbilityCountListeners;
	}

	public ArrayList<ObstacleMovementListener> getObstacleMovementListeners(){
		return GameLogic.obstacleMovementListeners;
	}

	public ArrayList<HollowPurpleListener> getHollowPurpleListeners(){
		return GameInfo.hollowPurpleListeners;
	}

	public ArrayList<FrozenListener> getFrozenListeners(){
		return GameInfo.frozenListeners;
	}


	public void loadGame(String username, ArrayList<String> gameInfo) {
		Player p=new Player(username, Double.parseDouble(gameInfo.get(0)),Integer.parseInt(gameInfo.get(1)),Integer.parseInt(gameInfo.get(5))
				,Integer.parseInt(gameInfo.get(6)),Integer.parseInt(gameInfo.get(7)),Integer.parseInt(gameInfo.get(8)));
		setCurrentPlayer(p);
		
		updateSphere(Integer.parseInt(gameInfo.get(3)),Integer.parseInt(gameInfo.get(4)));
		
		resetObstacles();
		

	}





}

