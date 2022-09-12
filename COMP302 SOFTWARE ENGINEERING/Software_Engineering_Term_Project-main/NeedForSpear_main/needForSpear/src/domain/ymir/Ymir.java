package domain.ymir;

import domain.game.GameLogic;
import domain.game.CreateWorldHandler;
import domain.game.DomainConstants;
import domain.game.GameInfo;
import domain.obstacles.Obstacle;


import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit; 


public class Ymir {

	public static int second;

	public Random rand = new Random();
	private boolean activated;

	public Ymir() {
		Runnable countSecond = new Runnable() {

			public void run() {

				second++;

				if(second  % DomainConstants.ymirAbilityPeriod == 0) {


					int randint=rand.nextInt(2);
					if(randint==1) {
						if(activated) useRandomAbility();
					}
				}
			}

			private void useRandomAbility() {
				int randint=rand.nextInt(3);
				if(randint==0) {
					useInfiniteVoid();
				} else if (randint==1) {
					useDoubleAccel();
				} else if (randint==2) {
					useHollowPurple();
				}

			}
		};

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(countSecond, 0, 1, TimeUnit.SECONDS);


	}

	protected void useHollowPurple() {

		int numOfPlaces=DomainConstants.maxNumberOfObstacles-GameInfo.obstacles.size();
		GameLogic.timer.stop();
		for(HollowPurpleListener lis: GameInfo.hollowPurpleListeners ) {
			lis.onUseHollowPurpleAbility();
		}
		if(numOfPlaces<8) {
			for (int i=0; i<numOfPlaces; i++) {

				int locXa= rand.nextInt((int) (25*(DomainConstants.width-2*DomainConstants.widthButton-DomainConstants.width/50)/DomainConstants.width)+1);
				int locYa= rand.nextInt((int) ((DomainConstants.height/2-DomainConstants.heightButton)/40)+1);
				while(GameInfo.myArray[locXa][locYa]==1) {
					locXa= rand.nextInt((int) (25*(DomainConstants.width-2*DomainConstants.widthButton-DomainConstants.width/50)/DomainConstants.width)+1);
					locYa= rand.nextInt((int) ((DomainConstants.height/2-DomainConstants.heightButton)/40)+1);
				}

				GameInfo.myArray[locXa][locYa]=1;
				int locX=2*(locXa*DomainConstants.width/50);
				int locY=2*locYa*20+DomainConstants.heightButton;


				CreateWorldHandler.createObstacle("HollowObstacle", locX, locY);
				for(HollowPurpleListener lis: GameInfo.hollowPurpleListeners) {
					lis.onHollowObstacleCreation(locX, locY);
				}
			}
		} else {
			for (int i=0; i<8; i++) {
				int locXa= rand.nextInt((int) (25*(DomainConstants.width-2*DomainConstants.widthButton-DomainConstants.width/50)/DomainConstants.width)+1);
				int locYa= rand.nextInt((int) ((DomainConstants.height/2-DomainConstants.heightButton)/40)+1);
				while(GameInfo.myArray[locXa][locYa]==1) {
					locXa= rand.nextInt((int) (25*(DomainConstants.width-2*DomainConstants.widthButton-DomainConstants.width/50)/DomainConstants.width)+1);
					locYa= rand.nextInt((int) ((DomainConstants.height/2-DomainConstants.heightButton)/40)+1);
				}

				GameInfo.myArray[locXa][locYa]=1;
				int locX=2*(locXa*DomainConstants.width/50);
				int locY=2*locYa*20+DomainConstants.heightButton;


				CreateWorldHandler.createObstacle("HollowObstacle", locX, locY);
				for(HollowPurpleListener lis: GameInfo.hollowPurpleListeners) {
					lis.onHollowObstacleCreation(locX, locY);
				}
			}
		}
		GameLogic.timer.start();

	}

	protected void useDoubleAccel() {
		GameLogic.timer.stop();
		for(HollowPurpleListener lis: GameInfo.hollowPurpleListeners ) {
			lis.onUseDoubleAccelAbility();
		}
		if(GameInfo.sphere.getDx()==2) {
			GameInfo.sphere.setDx(1);
		} else {
			GameInfo.sphere.setDx(-1);
		}

		if(GameInfo.sphere.getDy()==2) {
			GameInfo.sphere.setDy(1);
		} else {
			GameInfo.sphere.setDy(-1);
		}

		new java.util.Timer().schedule( 
				new java.util.TimerTask() {
					@Override
					public void run() {
						if(GameInfo.sphere.getDx()==1) {
							GameInfo.sphere.setDx(2);
						} else {
							GameInfo.sphere.setDx(-2);
						}

						if(GameInfo.sphere.getDy()==1) {
							GameInfo.sphere.setDy(2);
						} else {
							GameInfo.sphere.setDy(-2);
						}
					}
				}, 
				DomainConstants.ymirAbilityDuration
				);
		GameLogic.timer.start();
	}


	public void useInfiniteVoid() {

		GameLogic.timer.stop();
		for(HollowPurpleListener lis: GameInfo.hollowPurpleListeners ) {
			lis.onUseInfiniteVoidAbility();
		}

		int numOfObstacles=GameInfo.obstacles.size();
		if(numOfObstacles!=0) {
			if(numOfObstacles>8) {
				for(int i=0 ; i<8 ; i++) {

					Random rand = new Random();

					int randIndex = rand.nextInt(GameInfo.obstacles.size());

					Obstacle o=GameInfo.obstacles.get(randIndex);
					o.setFrozen(true);


					for(FrozenListener lis: GameInfo.frozenListeners) {
						lis.onFrozen(o.getObstacleName(), randIndex);
					}

					new java.util.Timer().schedule( 
							new java.util.TimerTask() {
								@Override
								public void run() {
									for(FrozenListener lis: GameInfo.frozenListeners) {
										lis.onFrozenEnded(o.getObstacleName(), GameInfo.obstacles.indexOf(o));
									}
									o.setFrozen(false);
								}
							}, 
							DomainConstants.ymirAbilityDuration
							);

				}
			} else {
				for(int i=0 ; i<numOfObstacles ; i++) {
					Obstacle o=GameInfo.obstacles.get(i);
					o.setFrozen(true);
					new java.util.Timer().schedule( 
							new java.util.TimerTask() {
								@Override
								public void run() {
									o.setFrozen(false);
								}
							}, 
							DomainConstants.ymirAbilityDuration
							);

				}
			}
		}
		GameLogic.timer.start();
	}

	public void setActivated(boolean activated) {
		this.activated=activated;
	}






}
