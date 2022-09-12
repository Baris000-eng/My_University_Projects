package domain.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

import domain.obstacles.FirmObstacle;
import domain.obstacles.GiftBox;
import domain.obstacles.MagicalHex;
import domain.obstacles.MagicalHexListener;
import domain.obstacles.Obstacle;
import domain.obstacles.ObstacleMovementListener;
import domain.obstacles.Remaining;
import domain.player.EnchantedSphere;
import domain.player.NoblePhantasm;
import domain.player.RemainingGiftBoxListener;
import domain.player.ScoreLivesListener;



/*This class performs game logics such as movement of sphere-obstacles-phantasm
and collisions (sphere-obstacle, sphere-phantasm, remaining-phantasm, giftbox-phantasm)*/

public class GameLogic {

	public static String currentCollidedObstacle="null";
	public static String currentCollisionSide="null";
	public static ArrayList<CollisionListener> collisionListeners=new ArrayList<CollisionListener>();
	public static ArrayList<SphereMovementListener> movementListeners=new ArrayList<SphereMovementListener>();
	public static ArrayList<ObstacleMovementListener> obstacleMovementListeners=new ArrayList<ObstacleMovementListener>();
	public static int index=-1;
	public static int maxYofObstacles=-1;
	public static boolean isPaused=true;
	public static boolean speedDoubled=false;
	
	

	public static boolean collisionOfEnchantedSphereWithNoblePhantasm(NoblePhantasm nob, EnchantedSphere esp) {
		if((nob.getxLocation()-16<=esp.getxLocation() && esp.getxLocation()<=nob.getxLocation()+ nob.getLength()) && DomainConstants.phantasmInitialY+2 <= esp.getyLocation()+16 && esp.getyLocation()+16<=DomainConstants.phantasmInitialY+4) {
			return true;
		}
		return false;
	}

	public static boolean collisionSphereFromUp(Obstacle obs,EnchantedSphere esp) {
		//REQUIRES: Nonnull objects as parameters.
		//EFFECTS: Checks if the given obstacle and enchanted sphere collides.
		//If they collide, it returns true. Otherwise, it return false.
		if((obs.getxLocation()-16<=esp.getxLocation() && esp.getxLocation()<= obs.getxLocation()+obs.getWidth()) && (esp.getyLocation()+obs.getHeight()-2>=obs.getyLocation() && obs.getyLocation()>=esp.getyLocation()+16)) {

			return true;
		}
		return false;
	}

	public static boolean collisionSphereFromDown(Obstacle obs,EnchantedSphere esp) {
		if((obs.getxLocation()-16<=esp.getxLocation() && esp.getxLocation()<= obs.getxLocation()+obs.getWidth()) && (obs.getyLocation()+obs.getHeight()-2<=esp.getyLocation()&&esp.getyLocation()<=obs.getyLocation()+obs.getHeight())) {

			return true;
		}
		return false;
	}

	public static boolean collisionSphereFromLeft(Obstacle obs,EnchantedSphere esp) {
		if((esp.getxLocation()+18>=obs.getxLocation() && obs.getxLocation()>=esp.getxLocation()+16) && (obs.getyLocation()-16<=esp.getyLocation() && esp.getyLocation()<=obs.getyLocation()+obs.getHeight())) {

			return true;
		}
		return false;
	}

	public static boolean collisionSphereFromRight(Obstacle obs,EnchantedSphere esp) {
		if((obs.getxLocation()+obs.getWidth()-2<=esp.getxLocation()&& esp.getxLocation()<= obs.getxLocation()+obs.getWidth()) && (obs.getyLocation()-16<=esp.getyLocation() && esp.getyLocation()<=obs.getyLocation()+obs.getHeight())) {

			return true;
		}
		return false;
	}




	public static boolean collisionUp() {

		for (Obstacle o: GameInfo.obstacles) {
			if(collisionSphereFromUp(o, GameInfo.sphere)) {

				if(o.isFrozen()) {
					if(NeedForSpearGame.returnUnstoppableActivity()==true) {
						currentCollidedObstacle=o.getObstacleName();
						currentCollisionSide="Up";
						index=GameInfo.obstacles.indexOf(o);
						if(currentCollidedObstacle.equals("FirmObstacle")) {
							if(NeedForSpearGame.returnUnstoppableActivity()==false) {
								if( ((FirmObstacle) o).getNumOfHitsRequired()==1 ) {
									GameInfo.obstacles.remove(o);
								} else {
									((FirmObstacle) o).decrementNumOfHitsRequired();
								}
							} else {
								GameInfo.obstacles.remove(o);
							}
						} else {
							GameInfo.obstacles.remove(o);
						}
						GameInfo.sphere.setDy(GameInfo.sphere.getDy()*(-1));


					} else {
						GameInfo.sphere.setDy(GameInfo.sphere.getDy()*(-1));
					}

				} else {
					if(NeedForSpearGame.returnUnstoppableActivity()==true) {
						currentCollidedObstacle=o.getObstacleName();
						currentCollisionSide="Up";
						index=GameInfo.obstacles.indexOf(o);
						if(currentCollidedObstacle.equals("FirmObstacle")) {
							if(NeedForSpearGame.returnUnstoppableActivity()==false) {
								if( ((FirmObstacle) o).getNumOfHitsRequired()==1 ) {
									GameInfo.obstacles.remove(o);
								} else {
									((FirmObstacle) o).decrementNumOfHitsRequired();
								}
							} else {
								GameInfo.obstacles.remove(o);
							}
						} else {
							GameInfo.obstacles.remove(o);
						}
					} else {
						currentCollidedObstacle=o.getObstacleName();
						currentCollisionSide="Up";
						index=GameInfo.obstacles.indexOf(o);
						if(currentCollidedObstacle.equals("FirmObstacle")) {
							if(NeedForSpearGame.returnUnstoppableActivity()==false) {
								if( ((FirmObstacle) o).getNumOfHitsRequired()==1 ) {
									GameInfo.obstacles.remove(o);
								} else {
									((FirmObstacle) o).decrementNumOfHitsRequired();
								}
							} else {
								GameInfo.obstacles.remove(o);
							}
						} else {
							GameInfo.obstacles.remove(o);
						}
						GameInfo.sphere.setDy(GameInfo.sphere.getDy()*(-1));
					}

				}






				return true;

			}
		}
		return false;

	}

	public static boolean collisionDown() {

		for (Obstacle o: GameInfo.obstacles) {
			if(collisionSphereFromDown(o, GameInfo.sphere)) {

				if(o.isFrozen()) {
					if(NeedForSpearGame.returnUnstoppableActivity()==true) {
						currentCollidedObstacle=o.getObstacleName();
						currentCollisionSide="Down";
						index=GameInfo.obstacles.indexOf(o);
						if(currentCollidedObstacle.equals("FirmObstacle")) {
							if(NeedForSpearGame.returnUnstoppableActivity()==false) {
								if( ((FirmObstacle) o).getNumOfHitsRequired()==1 ) {
									GameInfo.obstacles.remove(o);
								} else {
									((FirmObstacle) o).decrementNumOfHitsRequired();
								}
							} else {
								GameInfo.obstacles.remove(o);
							}
						} else {
							GameInfo.obstacles.remove(o);
						}
						GameInfo.sphere.setDy(GameInfo.sphere.getDy()*(-1));


					} else {
						GameInfo.sphere.setDy(GameInfo.sphere.getDy()*(-1));
					}

				} else {
					if(NeedForSpearGame.returnUnstoppableActivity()==true) {
						currentCollidedObstacle=o.getObstacleName();
						currentCollisionSide="Down";
						index=GameInfo.obstacles.indexOf(o);
						if(currentCollidedObstacle.equals("FirmObstacle")) {
							if(NeedForSpearGame.returnUnstoppableActivity()==false) {
								if( ((FirmObstacle) o).getNumOfHitsRequired()==1 ) {
									GameInfo.obstacles.remove(o);
								} else {
									((FirmObstacle) o).decrementNumOfHitsRequired();
								}
							} else {
								GameInfo.obstacles.remove(o);
							}
						} else {
							GameInfo.obstacles.remove(o);
						}
					} else {
						currentCollidedObstacle=o.getObstacleName();
						currentCollisionSide="Up";
						index=GameInfo.obstacles.indexOf(o);
						if(currentCollidedObstacle.equals("FirmObstacle")) {
							if(NeedForSpearGame.returnUnstoppableActivity()==false) {
								if( ((FirmObstacle) o).getNumOfHitsRequired()==1 ) {
									GameInfo.obstacles.remove(o);
								} else {
									((FirmObstacle) o).decrementNumOfHitsRequired();
								}
							} else {
								GameInfo.obstacles.remove(o);
							}
						} else {
							GameInfo.obstacles.remove(o);
						}
						GameInfo.sphere.setDy(GameInfo.sphere.getDy()*(-1));
					}

				}




				return true;

			}
		}
		return false;

	}

	public static boolean collisionRight() {

		for (Obstacle o: GameInfo.obstacles) {
			if(collisionSphereFromRight(o, GameInfo.sphere)) {

				if(o.isFrozen()) {
					if(NeedForSpearGame.returnUnstoppableActivity()==true) {
						currentCollidedObstacle=o.getObstacleName();
						currentCollisionSide="Right";
						index=GameInfo.obstacles.indexOf(o);
						if(currentCollidedObstacle.equals("FirmObstacle")) {
							if(NeedForSpearGame.returnUnstoppableActivity()==false) {
								if( ((FirmObstacle) o).getNumOfHitsRequired()==1 ) {
									GameInfo.obstacles.remove(o);
								} else {
									((FirmObstacle) o).decrementNumOfHitsRequired();
								}
							} else {
								GameInfo.obstacles.remove(o);
							}
						} else {
							GameInfo.obstacles.remove(o);
						}
						GameInfo.sphere.setDx(GameInfo.sphere.getDx()*(-1));


					} else {
						GameInfo.sphere.setDx(GameInfo.sphere.getDx()*(-1));
					}

				} else {
					if(NeedForSpearGame.returnUnstoppableActivity()==true) {
						currentCollidedObstacle=o.getObstacleName();
						currentCollisionSide="Right";
						index=GameInfo.obstacles.indexOf(o);
						if(currentCollidedObstacle.equals("FirmObstacle")) {
							if(NeedForSpearGame.returnUnstoppableActivity()==false) {
								if( ((FirmObstacle) o).getNumOfHitsRequired()==1 ) {
									GameInfo.obstacles.remove(o);
								} else {
									((FirmObstacle) o).decrementNumOfHitsRequired();
								}
							} else {
								GameInfo.obstacles.remove(o);
							}
						} else {
							GameInfo.obstacles.remove(o);
						}
					} else {
						currentCollidedObstacle=o.getObstacleName();
						currentCollisionSide="Right";
						index=GameInfo.obstacles.indexOf(o);
						if(currentCollidedObstacle.equals("FirmObstacle")) {
							if(NeedForSpearGame.returnUnstoppableActivity()==false) {
								if( ((FirmObstacle) o).getNumOfHitsRequired()==1 ) {
									GameInfo.obstacles.remove(o);
								} else {
									((FirmObstacle) o).decrementNumOfHitsRequired();
								}
							} else {
								GameInfo.obstacles.remove(o);
							}
						} else {
							GameInfo.obstacles.remove(o);
						}
						GameInfo.sphere.setDx(GameInfo.sphere.getDx()*(-1));
					}

				}




				return true;

			}
		}
		return false;

	}


	public static boolean collisionLeft() {

		for (Obstacle o: GameInfo.obstacles) {
			if(collisionSphereFromLeft(o, GameInfo.sphere)) {

				if(o.isFrozen()) {
					if(NeedForSpearGame.returnUnstoppableActivity()==true) {
						currentCollidedObstacle=o.getObstacleName();
						currentCollisionSide="Left";
						index=GameInfo.obstacles.indexOf(o);
						if(currentCollidedObstacle.equals("FirmObstacle")) {
							if(NeedForSpearGame.returnUnstoppableActivity()==false) {
								if( ((FirmObstacle) o).getNumOfHitsRequired()==1 ) {
									GameInfo.obstacles.remove(o);
								} else {
									((FirmObstacle) o).decrementNumOfHitsRequired();
								}
							} else {
								GameInfo.obstacles.remove(o);
							}
						} else {
							GameInfo.obstacles.remove(o);
						}
						GameInfo.sphere.setDx(GameInfo.sphere.getDx()*(-1));


					} else {
						GameInfo.sphere.setDx(GameInfo.sphere.getDx()*(-1));
					}

				} else {
					if(NeedForSpearGame.returnUnstoppableActivity()==true) {
						currentCollidedObstacle=o.getObstacleName();
						currentCollisionSide="Left";
						index=GameInfo.obstacles.indexOf(o);
						if(currentCollidedObstacle.equals("FirmObstacle")) {
							if(NeedForSpearGame.returnUnstoppableActivity()==false) {
								if( ((FirmObstacle) o).getNumOfHitsRequired()==1 ) {
									GameInfo.obstacles.remove(o);
								} else {
									((FirmObstacle) o).decrementNumOfHitsRequired();
								}
							} else {
								GameInfo.obstacles.remove(o);
							}
						} else {
							GameInfo.obstacles.remove(o);
						}
					} else {
						currentCollidedObstacle=o.getObstacleName();
						currentCollisionSide="Left";
						index=GameInfo.obstacles.indexOf(o);
						if(currentCollidedObstacle.equals("FirmObstacle")) {
							if(NeedForSpearGame.returnUnstoppableActivity()==false) {
								if( ((FirmObstacle) o).getNumOfHitsRequired()==1 ) {
									GameInfo.obstacles.remove(o);
								} else {
									((FirmObstacle) o).decrementNumOfHitsRequired();
								}
							} else {
								GameInfo.obstacles.remove(o);
							}
						} else {
							GameInfo.obstacles.remove(o);
						}
						GameInfo.sphere.setDx(GameInfo.sphere.getDx()*(-1));
					}

				}




				return true;

			}
		}
		return false;

	}

	public static void informCollision() {
		for(CollisionListener collisionListener: collisionListeners) {
			collisionListener.collided(index, currentCollidedObstacle, currentCollisionSide);
		}
	}
	
	/*public static void informCollisionWithoutIncreasingScore() {
		for(CollisionListener collisionListener: collisionListeners) {
			collisionListener.collidedWithHollow(index, currentCollidedObstacle, currentCollisionSide);
		}
	}*/


	public static void findMaxYofObstacles() {
		for(Obstacle o: GameInfo.obstacles) {
			if(o.getyLocation()>maxYofObstacles) {
				maxYofObstacles=o.getyLocation();
			}
		}
	}

	public static Timer timer = new Timer(10, new ActionListener() {
		
		public void actionPerformed(ActionEvent e)
		{

			if(maxYofObstacles==-1) {
				findMaxYofObstacles();
			} 
			if (!isPaused) {
				
				
				if (GameInfo.sphere.getxLocation() < 0)										GameInfo.sphere.setDx(GameInfo.sphere.getDx()*(-1));
				if (GameInfo.sphere.getxLocation() > (DomainConstants.rightBoundaryForMap-16))	GameInfo.sphere.setDx(GameInfo.sphere.getDx()*(-1));
				if (GameInfo.sphere.getyLocation() < DomainConstants.upperBoundaryForMap)				GameInfo.sphere.setDy(GameInfo.sphere.getDy()*(-1));

				if(GameInfo.sphere.getyLocation()<=maxYofObstacles+20) {  //to prevent unnecessary checks
					if(collisionDown() || collisionUp() || collisionRight() || collisionLeft()) {

						if(currentCollidedObstacle.equals("ExplosiveObstacle")) {

							Remaining rem=new Remaining(GameInfo.sphere.getxLocation(), GameInfo.sphere.getyLocation(), -1);
							Remaining rem2=new Remaining(GameInfo.sphere.getxLocation(), GameInfo.sphere.getyLocation(), 0);
							Remaining rem3=new Remaining(GameInfo.sphere.getxLocation(), GameInfo.sphere.getyLocation(), 1);

							GameInfo.remainings.add(rem);
							GameInfo.remainings.add(rem2);
							GameInfo.remainings.add(rem3);

						} else if(currentCollidedObstacle.equals("GiftObstacle")) {
							GiftBox giftBox=new GiftBox(GameInfo.sphere.getxLocation(), GameInfo.sphere.getyLocation());				
							GameInfo.giftBoxes.add(giftBox);

						}

						informCollision();
						
						if(!currentCollidedObstacle.equals("HollowObstacle")) {
							GameInfo.currentPlayer.updateScore();
						} 

						currentCollidedObstacle="null";
						currentCollisionSide="null";
						index=-1;
					}
				}

				if (GameInfo.sphere.getyLocation()>9.0/8.0*DomainConstants.phantasmInitialY) {

					GameInfo.sphere.setxLocation(DomainConstants.sphereInitialX);
					GameInfo.sphere.setyLocation(DomainConstants.sphereInitialY);

					GameInfo.phantasm.setxLocation(DomainConstants.phantasmInitialX);

					GameInfo.currentPlayer.setLives(GameInfo.currentPlayer.getLives()-1);

					timer.stop();
					isPaused= true;
					if(GameInfo.currentPlayer.getLives()>=1) {
						for(ScoreLivesListener lis: GameInfo.scoreLivesListeners) {
							lis.onLiveLose(GameInfo.currentPlayer.getLives());
						}
					}
					//controller.stopCollisionChecking();
					timer.start();
					isPaused= false;

				}

				if(collisionOfEnchantedSphereWithNoblePhantasm(GameInfo.phantasm, GameInfo.sphere)){
					GameInfo.sphere.setDy(GameInfo.sphere.getDy()*(-1));
				}

				for(Remaining rem: GameInfo.remainings) {
					rem.setxLocation(rem.getxLocation()+rem.getDx());
					rem.setyLocation(rem.getyLocation()+rem.getDy());
					int index=GameInfo.remainings.indexOf(rem);

					for (RemainingGiftBoxListener lis: GameInfo.remGiftListeners) {
						lis.onRemainingMovement(index, rem.getxLocation(), rem.getyLocation());
					}

					if(GameInfo.phantasm.getxLocation()-15<rem.getxLocation() 
							&& rem.getxLocation()< GameInfo.phantasm.getxLocation()+ GameInfo.phantasm.getLength() 
							&& rem.getyLocation()+20 >= DomainConstants.phantasmInitialY 
							&& rem.getyLocation()<DomainConstants.phantasmInitialY +20) {

						GameInfo.remainings.remove(rem);

						for(RemainingGiftBoxListener lis: GameInfo.remGiftListeners) {
							lis.onRemainingTouch(index);
						}

						GameInfo.currentPlayer.decrementLives();
						break;
					}
				}

				for(GiftBox giftBox: GameInfo.giftBoxes) {
					giftBox.setxLocation(giftBox.getxLocation()+giftBox.getDx());
					giftBox.setyLocation(giftBox.getyLocation()+giftBox.getDy());
					int index=GameInfo.giftBoxes.indexOf(giftBox);
					for (RemainingGiftBoxListener lis: GameInfo.remGiftListeners) {
						lis.onGiftBoxMovement(index, giftBox.getxLocation(), giftBox.getyLocation());
					}
					if(GameInfo.phantasm.getxLocation()-25<giftBox.getxLocation()
							&& giftBox.getxLocation()< GameInfo.phantasm.getxLocation()+ GameInfo.phantasm.getLength() 
							&& giftBox.getyLocation()+20 >= DomainConstants.phantasmInitialY 
							&& giftBox.getyLocation()<DomainConstants.phantasmInitialY+20) {

						
						GameInfo.giftBoxes.remove(giftBox);
						GameInfo.currentPlayer.addRandomAbility();
						for(RemainingGiftBoxListener lis: GameInfo.remGiftListeners) {
							lis.onGiftBoxTouch(index);
						}
						
						break;
					}
				}

				for(MagicalHex hex: GameInfo.magicalHexes) {
					hex.setxLocation(hex.getxLocation()+hex.getDx());
					hex.setyLocation(hex.getyLocation()+hex.getDy());
					int indexOfHex=GameInfo.magicalHexes.indexOf(hex);

					for (MagicalHexListener lis: GameInfo.magicalHexListeners) {
						lis.onMagicalHexMovement(indexOfHex, hex.getxLocation(), hex.getyLocation());
					}
					boolean collision=false;
					for(Obstacle obs: GameInfo.obstacles) {


						//boolean collision=false;
						if(obs.getxLocation()-16<=hex.getxLocation() 
								&& hex.getxLocation()<= obs.getxLocation()+obs.getWidth() 
								&& (obs.getyLocation()+18<=hex.getyLocation()&&hex.getyLocation()<=obs.getyLocation()+20)) {
							currentCollidedObstacle=obs.getObstacleName();
							currentCollisionSide="Down";
							index=GameInfo.obstacles.indexOf(obs);
							informCollision();
							GameInfo.obstacles.remove(obs);
							index=-1;
							currentCollidedObstacle="null";
							currentCollisionSide="null";
							for(MagicalHexListener lis: GameInfo.magicalHexListeners) {
								lis.onMagicalHexCollision(indexOfHex);
							}
							GameInfo.magicalHexes.remove(hex);
							collision=true;
							break;
						}
					} if(collision) {
						break;
					}

				}

				//Move sphere
				GameInfo.sphere.setxLocation(GameInfo.sphere.getxLocation()+GameInfo.sphere.getDx());
				GameInfo.sphere.setyLocation(GameInfo.sphere.getyLocation()+GameInfo.sphere.getDy());

				//move obstacle
				for(Obstacle o:GameInfo.obstacles) {
					o.move();
					int index=GameInfo.obstacles.indexOf(o);
					for(ObstacleMovementListener lis: obstacleMovementListeners) {
						lis.onObstacleMovement(index, o.getxLocation(), o.getyLocation());
					}
				}

				//Check whether game is over.
				if(GameInfo.obstacles.size()==0) {
					timer.stop();
					for(ResetListener res: GameInfo.resetListeners) {
						res.onWinTheGame(GameInfo.currentPlayer.getScore());
					}
					GameInfo.currentPlayer.setScore(0);
					GameInfo.currentPlayer.getMagicalAbilities()[0]=0;
					GameInfo.currentPlayer.getMagicalAbilities()[1]=0;
					GameInfo.currentPlayer.getMagicalAbilities()[2]=0;
					GameInfo.currentPlayer.getMagicalAbilities()[3]=0;

				}else if(GameInfo.currentPlayer.getLives()==0) {
					timer.stop();
					for(ResetListener res: GameInfo.resetListeners) {
						res.onLoseTheGame(GameInfo.currentPlayer.getScore());
					}
					GameInfo.currentPlayer.setScore(0);
					GameInfo.currentPlayer.getMagicalAbilities()[0]=0;
					GameInfo.currentPlayer.getMagicalAbilities()[1]=0;
					GameInfo.currentPlayer.getMagicalAbilities()[2]=0;
					GameInfo.currentPlayer.getMagicalAbilities()[3]=0;
				}


			}
		}
	});






}
