package domain.game;

import java.util.Random;



import domain.obstacles.FirmObstacle;
import domain.obstacles.Obstacle;
import domain.obstacles.ObstacleCreationListener;
import domain.obstacles.ObstacleFactory;



public class CreateWorldHandler {

	public CreateWorldHandler() {}

	public static Obstacle createObstacle(String typeOfObstacle, int locX, int locY) {
		Obstacle obs=ObstacleFactory.getInstance().getObstacle(typeOfObstacle, locX, locY);
		GameInfo.obstacles.add(obs);
		return obs;
		//
	}

	public void removeObstacle(int locX, int locY) {

		for (Obstacle o:GameInfo.obstacles){
			if (o.getxLocation()==locX && o.getyLocation()==locY) {
				GameInfo.obstacles.remove(o);
				break;
			}
		}
	}

	public static void updateObstacleLocation(int x, int y, int x2, int y2) {

		for (Obstacle o: GameInfo.obstacles) {
			if(o.getxLocation()==x && o.getyLocation()==y) {
				o.setxLocation(x2);
				o.setyLocation(y2);
			}
		}

	}
	public void createWorld(int simpleObstacleNumber, int firmObstacleNumber, int explosiveObstacleNumber, int giftObstacleNumber) {
		if(simpleObstacleNumber<DomainConstants.simpleObstacleNumberBoundary 
				|| firmObstacleNumber<DomainConstants.firmObstacleNumberBoundary 
				|| explosiveObstacleNumber< DomainConstants.explosiveObstacleNumberBoundary
				|| giftObstacleNumber<DomainConstants.giftObstacleNumberBoundary) {
			for(ObstacleCreationListener lis: GameInfo.obstacleCreationListeners) {
				lis.onMissingNumberEntered();
			}
		} else if(simpleObstacleNumber + firmObstacleNumber + explosiveObstacleNumber + giftObstacleNumber>DomainConstants.maxNumberOfObstacles){
			for(ObstacleCreationListener lis: GameInfo.obstacleCreationListeners) {
				lis.onExceedingNumberEntered(simpleObstacleNumber + firmObstacleNumber + explosiveObstacleNumber + giftObstacleNumber);
			}
		}
		else {

			Random random= new Random();

			for(int i=0;i<simpleObstacleNumber;i++) {

				int locXa= random.nextInt((int) (25*(DomainConstants.width-2*DomainConstants.widthButton-DomainConstants.width/50)/DomainConstants.width)+1);
				int locYa= random.nextInt((int) ((DomainConstants.height/2-DomainConstants.heightButton)/40)+1);
				while(GameInfo.myArray[locXa][locYa]==1) {
					locXa= random.nextInt((int) (25*(DomainConstants.width-2*DomainConstants.widthButton-DomainConstants.width/50)/DomainConstants.width)+1);
					locYa= random.nextInt((int) ((DomainConstants.height/2-DomainConstants.heightButton)/40)+1);
				}

				GameInfo.myArray[locXa][locYa]=1;
				int locX=2*(locXa*DomainConstants.width/50);
				int locY=2*locYa*20+DomainConstants.heightButton;


				createObstacle("SimpleObstacle", locX, locY);
				for(ObstacleCreationListener lis: GameInfo.obstacleCreationListeners) {
					lis.onSimpleObstacleCreation(locX, locY);
				}
			}

			for(int j=0;j<firmObstacleNumber;j++) {


				int locXa= random.nextInt((int) (25*(DomainConstants.width-2*DomainConstants.widthButton-DomainConstants.width/50)/DomainConstants.width)+1);
				int locYa= random.nextInt((int) ((DomainConstants.height/2-DomainConstants.heightButton)/40)+1);
				while(GameInfo.myArray[locXa][locYa]==1) {
					locXa= random.nextInt((int) (25*(DomainConstants.width-2*DomainConstants.widthButton-DomainConstants.width/50)/DomainConstants.width)+1);
					locYa= random.nextInt((int) ((DomainConstants.height/2-DomainConstants.heightButton)/40)+1);
				}

				GameInfo.myArray[locXa][locYa]=1;
				int locX=2*(locXa*DomainConstants.width/50);
				int locY=2*locYa*20+DomainConstants.heightButton;


				FirmObstacle f=(FirmObstacle) createObstacle("FirmObstacle", locX, locY);
				for(ObstacleCreationListener lis: GameInfo.obstacleCreationListeners) {
					lis.onFirmObstacleCreation(locX, locY, f.getNumOfHitsRequired());
				}


			}

			for(int k=0;k<explosiveObstacleNumber;k++) {


				int locXa= random.nextInt((int) (25*(DomainConstants.width-2*DomainConstants.widthButton-DomainConstants.width/50)/DomainConstants.width)+1);
				int locYa= random.nextInt((int) ((DomainConstants.height/2-DomainConstants.heightButton)/40)+1);
				while(GameInfo.myArray[locXa][locYa]==1) {
					locXa= random.nextInt((int) (25*(DomainConstants.width-2*DomainConstants.widthButton-DomainConstants.width/50)/DomainConstants.width)+1);
					locYa= random.nextInt((int) ((DomainConstants.height/2-DomainConstants.heightButton)/40)+1);
				}

				GameInfo.myArray[locXa][locYa]=1;
				int locX=2*(locXa*DomainConstants.width/50);
				int locY=2*locYa*20+DomainConstants.heightButton;


				createObstacle("ExplosiveObstacle", locX, locY);
				for(ObstacleCreationListener lis: GameInfo.obstacleCreationListeners) {
					lis.onExplosiveObstacleCreation(locX, locY);
				}


			}

			for(int t=0;t<giftObstacleNumber;t++) {


				int locXa= random.nextInt((int) (25*(DomainConstants.width-2*DomainConstants.widthButton-DomainConstants.width/50)/DomainConstants.width)+1);
				int locYa= random.nextInt((int) ((DomainConstants.height/2-DomainConstants.heightButton)/40)+1);
				while(GameInfo.myArray[locXa][locYa]==1) {
					locXa= random.nextInt((int) (25*(DomainConstants.width-2*DomainConstants.widthButton-DomainConstants.width/50)/DomainConstants.width)+1);
					locYa= random.nextInt((int) ((DomainConstants.height/2-DomainConstants.heightButton)/40)+1);
				}

				GameInfo.myArray[locXa][locYa]=1;
				int locX=2*(locXa*DomainConstants.width/50);
				int locY=2*locYa*20+DomainConstants.heightButton;


				createObstacle("GiftObstacle", locX, locY);
				for(ObstacleCreationListener lis: GameInfo.obstacleCreationListeners) {
					lis.onGiftObstacleCreation(locX, locY);
				}

			}


			for(int in=0; in<25*(DomainConstants.width-2*DomainConstants.widthButton-DomainConstants.width/50)/DomainConstants.width+1; in++){
				for(int j=0; j<(DomainConstants.height/2-DomainConstants.heightButton)/40+1; j++) {
					GameInfo.myArray[in][j]=0;
				}
			}

		}
	}



	public static void createObstacleByClick(String selectedObs, int x, int y) {

		if(x<DomainConstants.rightBoundaryForMap -DomainConstants.phantasmLength/5 && DomainConstants.upperBoundaryForMap<y && y<DomainConstants.lowerBoundaryForMap) {
			if(selectedObs.equals("simpleObstacle")) {

				boolean collisionOccurs = false;
				Obstacle simpleObstacle=ObstacleFactory.getInstance().getObstacle(selectedObs, x, y);	

				for (Obstacle obs :GameInfo.obstacles){
					if(obsCollision(obs, simpleObstacle)){
						collisionOccurs = true;
					}	

				}

				if(collisionOccurs==false) {

					createObstacle("SimpleObstacle", x, y);
					for(ObstacleCreationListener lis: GameInfo.obstacleCreationListeners) {
						lis.onSimpleObstacleCreation(x, y);
					}
				} else {
					for(ObstacleCreationListener lis: GameInfo.obstacleCreationListeners) {
						lis.onPutOnAnotherObstacle();
					}
				}
				
			} else if(selectedObs.equals("firmObstacle")) {

				boolean collisionOccurs = false;
				Obstacle firmObstacle=ObstacleFactory.getInstance().getObstacle(selectedObs, x, y);	

				for (Obstacle obs :GameInfo.obstacles){
					if(obsCollision(obs, firmObstacle)){
						collisionOccurs = true;
					}	

				}

				if(collisionOccurs==false) {

					FirmObstacle f=(FirmObstacle) createObstacle("FirmObstacle", x, y);
					for(ObstacleCreationListener lis: GameInfo.obstacleCreationListeners) {
						lis.onFirmObstacleCreation(x, y, f.getNumOfHitsRequired());
					}
				} else {
					for(ObstacleCreationListener lis: GameInfo.obstacleCreationListeners) {
						lis.onPutOnAnotherObstacle();
					}
				}
			} else if(selectedObs.equals("giftObstacle")) {

				boolean collisionOccurs = false;
				Obstacle giftObstacle=ObstacleFactory.getInstance().getObstacle(selectedObs, x, y);	

				for (Obstacle obs :GameInfo.obstacles){
					if(obsCollision(obs, giftObstacle)){
						collisionOccurs = true;
					}	

				}

				if(collisionOccurs==false) {

					createObstacle("GiftObstacle", x, y);
					for(ObstacleCreationListener lis: GameInfo.obstacleCreationListeners) {
						lis.onGiftObstacleCreation(x, y);
					}
				} else {
					for(ObstacleCreationListener lis: GameInfo.obstacleCreationListeners) {
						lis.onPutOnAnotherObstacle();
					}
				}
			} else if(selectedObs.equals("explosiveObstacle")) {

				boolean collisionOccurs = false;
				Obstacle explosiveObstacle=ObstacleFactory.getInstance().getObstacle(selectedObs, x, y);	

				for (Obstacle obs :GameInfo.obstacles){
					if(obsCollision(obs, explosiveObstacle)){
						collisionOccurs = true;
					}	

				}

				if(collisionOccurs==false) {

					createObstacle("ExplosiveObstacle", x, y);
					for(ObstacleCreationListener lis: GameInfo.obstacleCreationListeners) {
						lis.onExplosiveObstacleCreation(x, y);
					}
				} else {
					for(ObstacleCreationListener lis: GameInfo.obstacleCreationListeners) {
						lis.onPutOnAnotherObstacle();
					}
				}
			} else {
				for(ObstacleCreationListener lis: GameInfo.obstacleCreationListeners) {
					lis.onNoObstacleChosen();
				}
			}
		}  else {
			for(ObstacleCreationListener lis: GameInfo.obstacleCreationListeners) {
				lis.onClickOutsideMap();
			}
		}
	}


	public static Boolean obsLeftUpCornerCollision(Obstacle o, Obstacle obs2) {

		if( (o.getxLocation()>=obs2.getxLocation()&&o.getxLocation()<=obs2.getxLocation()+obs2.getWidth()) &&
				(o.getyLocation()>= obs2.getyLocation() && o.getyLocation()<=obs2.getyLocation()+obs2.getHeight())) {
			return true;
		}	
		return false;
	}

	public static Boolean obsRightUpCornerCollision(Obstacle o, Obstacle obs2) {
		if( (o.getxLocation()+ o.getWidth()  >=obs2.getxLocation()&&o.getxLocation()+o.getWidth()<=obs2.getxLocation()+obs2.getWidth()) &&
				(o.getyLocation()>= obs2.getyLocation() && o.getyLocation()<=obs2.getyLocation()+obs2.getHeight())) {
			return true;
		}	
		return false;
	}


	public static Boolean obsLeftDownCornerCollision(Obstacle o, Obstacle obs2) {
		if( (o.getxLocation()>=obs2.getxLocation()&&o.getxLocation()<=obs2.getxLocation()+obs2.getWidth()) &&
				(o.getyLocation()+o.getHeight()>= obs2.getyLocation() && o.getyLocation()+o.getHeight()<=obs2.getyLocation()+obs2.getHeight())) {
			return true;
		}	
		return false;
	}

	public static Boolean obsRightDownCornerCollision(Obstacle o, Obstacle obs2) {
		if( (o.getxLocation()+ o.getWidth()  >=obs2.getxLocation()&&o.getxLocation()+o.getWidth()<=obs2.getxLocation()+obs2.getWidth()) &&
				(o.getyLocation()+o.getHeight()>= obs2.getyLocation() && o.getyLocation()+o.getHeight()<=obs2.getyLocation()+obs2.getHeight())) {
			return true;
		}	
		return false;
	}


	public static Boolean obsCollision(Obstacle o, Obstacle obs2) {
		return obsRightDownCornerCollision(o,  obs2) 
				|| obsLeftDownCornerCollision(o,  obs2)
				|| obsRightUpCornerCollision(o,  obs2) 
				|| obsLeftUpCornerCollision(o,  obs2);
	}

	public void startGame() {
		GameInfo.currentPlayer.setSecond(1);
	}
}
