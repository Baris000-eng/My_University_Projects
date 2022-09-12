package domain.player;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import domain.game.GameLogic;
import domain.game.DomainConstants;
import domain.game.GameInfo;
import domain.game.NeedForSpearGame;


public class Player{

	// OVERVIEW: Player is represented as a double array with size 6,
	// which contains score, lives and number of each ability.

	// The abstraction function is 
	// AF(c) = {c.playerRep[i].intValue | 0 <= i < c.playerRep.size } (but ordered)
	//where
	//c.playerRep[0] = score
	//c.playerRep[1] = life
	//c.playerRep[2] = number of chance giving ability
	//c.playerRep[3] = number of noble phantasm expansion ability
	//c.playerRep[4] = number of magical hex ability
	//c.playerRep[5] = number of unstoppable enchanted sphere ability

	// The representation invariant is
	// c.playerRep != null &&
	// all elements of c.playerRep are doubles &&
	// all elements of c.playerRep are greater than or equal to 0.

	private double[] playerRep=new double[6];

	private String username;
	private double score;
	private int life;
	private int[] magicalAbilities = {0,0,0,0}; 
	private NeedForSpearGame controller= new NeedForSpearGame("txt");
	private boolean unstoppableActivated=false;
	private int second=1;


	public Player() {
		life=3;
		score = 0;
		playerRep[0]=score;
		playerRep[1]=life;
		playerRep[2]=0;
		playerRep[3]=0;
		playerRep[4]=0;
		playerRep[5]=0;
		for(MagicalAbilityCountListener lis: GameInfo.magicalAbilityCountListeners) {
			lis.inMagicalAbilityInitializition(magicalAbilities[0],magicalAbilities[1],magicalAbilities[2],magicalAbilities[3]);
		}

		for(ScoreLivesListener lis: GameInfo.scoreLivesListeners) {
			lis.onScoreChange(score);
			lis.onLivesChange(life);
		}

		Runnable countSecond = new Runnable() {

			public void run() {
				if (GameLogic.isPaused == false) {
					second++;
				}
			}
		};

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(countSecond, 0, 1, TimeUnit.SECONDS);

	}

	public Player(String username) {
		this.username=username;
		life=3;
		score=0;
		playerRep[0]=score;
		playerRep[1]=life;
		playerRep[2]=0;
		playerRep[3]=0;
		playerRep[4]=0;
		playerRep[5]=0;
		for(MagicalAbilityCountListener lis: GameInfo.magicalAbilityCountListeners) {
			lis.inMagicalAbilityInitializition(magicalAbilities[0], magicalAbilities[1], magicalAbilities[2], magicalAbilities[3]);
		}
		for(ScoreLivesListener lis: GameInfo.scoreLivesListeners) {
			lis.onScoreChange(score);
			lis.onLivesChange(life);
		}
		Runnable countSecond = new Runnable() {

			public void run() {
				if (GameLogic.isPaused == false) {
					second++;
				}
			}
		};

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(countSecond, 0, 1, TimeUnit.SECONDS);
	}

	public Player(String username, double score, int life) {
		this.username=username;
		this.score=score;
		this.life=life;
		for(MagicalAbilityCountListener lis: GameInfo.magicalAbilityCountListeners) {
			lis.inMagicalAbilityInitializition(magicalAbilities[0],magicalAbilities[1],magicalAbilities[2],magicalAbilities[3]);
		}
		for(ScoreLivesListener lis: GameInfo.scoreLivesListeners) {
			lis.onScoreChange(score);
			lis.onLivesChange(life);
		}
		Runnable countSecond = new Runnable() {

			public void run() {
				if (GameLogic.isPaused == false) {
					second++;
				}
			}
		};

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(countSecond, 0, 1, TimeUnit.SECONDS);
	}


	public Player(String username, double score, int life, int ability1, int ability2, int ability3, int ability4) {
		this.username=username;
		this.score=score;
		this.life=life;
		magicalAbilities[0]= ability1;
		magicalAbilities[1]= ability2;
		magicalAbilities[2]= ability3;
		magicalAbilities[3]= ability4;

		for(MagicalAbilityCountListener lis: GameInfo.magicalAbilityCountListeners) {
			lis.inMagicalAbilityInitializition(magicalAbilities[0],magicalAbilities[1],magicalAbilities[2],magicalAbilities[3]);
		}
		for(ScoreLivesListener lis: GameInfo.scoreLivesListeners) {
			lis.onScoreChange(score);
			lis.onLivesChange(life);
		}
		Runnable countSecond = new Runnable() {

			public void run() {
				if (GameLogic.isPaused == false) {
					second++;
				}
			}
		};

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(countSecond, 0, 1, TimeUnit.SECONDS);
	}

	public boolean repOk() {
		// EFFECTS: Returns true if the rep invariant holds
		// for this; otherwise returns false.
		return playerRep[0]>=0 && playerRep[1]>=0 && playerRep[2]>=0 && playerRep[3]>=0 && playerRep[4]>=0 && playerRep[5]>=0;
	}


	public int[] getMagicalAbilities() {
		return magicalAbilities;
	}

	public void setMagicalAbilities(int[] magicalAbilities) {
		this.magicalAbilities = magicalAbilities;
	}

	// MODIFIES: lives
	public void decrementLives() {
		setLives(getLives()-1);
		playerRep[1]--;
	}

	// MODIFIES: lives
	public void incrementLives() {
		setLives(getLives()+1);
		playerRep[1]++;
	}


	public String getUsername() {
		return username;
	}


	public double getScore() {
		return score;
	}


	public int getLives() {
		return life;
	}

	public void setSecond(int second) {
		this.second=second;
	}

	@Override
	public String toString() {
		return "Player " + username + " " + score + " " + life;
	}

	// REQUIRES: life>=0
	// MODIFIES: this.life, GameInfo.scoreLivesListeners
	public void setLives(int life) {
		this.life = life;
		playerRep[1]=life;
		publishLivesChange(life);
	}

	// REQUIRES: score>=0 
	// MODIFIES: this.score, GameInfo.scoreLivesListeners
	public void setScore(double score) {  
		this.score=score;
		playerRep[0]=score;
		publishScoreChange(score);
	}

	// MODIFIES: score
	public void updateScore() {  
		setScore(getScore()+300.0/second);
		playerRep[0]=getScore()+300.0/second;
	}

	// MODIFIES: score
	public void resetScore() {
		setScore(0);
		playerRep[0]=0;
	}

	// MODIFIES: GameInfo.scoreLivesListeners
	//This method publishes the score change event to the scoreLivesListeners
	public void publishScoreChange(double score) {
		for(ScoreLivesListener lis: GameInfo.scoreLivesListeners) {
			lis.onScoreChange(score);
		}

	}

	// MODIFIES: GameInfo.scoreLivesListeners
	//This method publishes the lives change event to the scoreLivesListeners
	public void publishLivesChange(int lives) {
		for(ScoreLivesListener lis: GameInfo.scoreLivesListeners) {
			lis.onLivesChange(lives);
		}

	}


	public void setUnstoppableActivated() {
		unstoppableActivated= true;
		for(MagicalAbilityListener lis: GameInfo.magicalAbilityListeners) {
			lis.inUnstoppableSphereAbility();
		}
	}

	public void endUnstoppableActivity() {
		unstoppableActivated =false;
		for(MagicalAbilityListener lis: GameInfo.magicalAbilityListeners) {
			lis.inEndUnstoppableSphereAbility();
		}
	}

	public boolean isUnstoppableActivated() {
		return unstoppableActivated;
	}


	public void useUnstoppableEnchantedSphereAbility() {
		if(getMagicalAbilities()[3]>0) {

			decrementUnstoppableEnchantedSphereAbility();

			setUnstoppableActivated();

			new java.util.Timer().schedule( 



					new java.util.TimerTask() {
						@Override
						public void run() {
							endUnstoppableActivity();
						}
					}, 
					DomainConstants.activationTime
					);


		}
	}

	public void useNoblePhantasmExpansionAbility() {

		if(GameInfo.currentPlayer.getMagicalAbilities()[1]>0) {


			GameInfo.phantasm.setLength(GameInfo.phantasm.getLength()*2);

			decrementNoblePhantasmExpansionAbility();
			new java.util.Timer().schedule( 
					new java.util.TimerTask() {
						@Override
						public void run() {
							controller.halveNoblePhantasm();
						}
					}, 
					DomainConstants.activationTime
					);
		}

	}


	//MODIFIES: magicalAbilities
	public void decrementChanceGivingAbility() {

		magicalAbilities[0]--;
		playerRep[2]--;
		for(MagicalAbilityCountListener lis: GameInfo.magicalAbilityCountListeners) {
			lis.inChanceGivingCountChange(magicalAbilities[0]);
		}
	}

	//MODIFIES: magicalAbilities
	public void decrementNoblePhantasmExpansionAbility() {

		magicalAbilities[1]--;
		playerRep[3]--;
		for(MagicalAbilityCountListener lis: GameInfo.magicalAbilityCountListeners) {
			lis.inPhantasmExpansionCountChange(magicalAbilities[1]);
		}
	}

	//MODIFIES: magicalAbilities
	public void decrementMagicalHexAbility() {
		magicalAbilities[2]--;
		playerRep[4]--;
		for(MagicalAbilityCountListener lis: GameInfo.magicalAbilityCountListeners) {
			lis.inMagicalHexCountChange(magicalAbilities[2]);
		}
	}

	//MODIFIES: magicalAbilities
	public void decrementUnstoppableEnchantedSphereAbility() {
		magicalAbilities[3]--;
		playerRep[5]--;
		for(MagicalAbilityCountListener lis: GameInfo.magicalAbilityCountListeners) {
			lis.inUnstoppableCountChange(magicalAbilities[3]);
		}
	}

	//MODIFIES: magicalAbilities
	public void incrementChanceGivingAbility() {

		magicalAbilities[0]++;
		playerRep[2]++;
		for(MagicalAbilityCountListener lis: GameInfo.magicalAbilityCountListeners) {
			lis.inChanceGivingCountChange(magicalAbilities[0]);
		}
	}

	//MODIFIES: magicalAbilities
	public void incrementNoblePhantasmExpansionAbility() {
		magicalAbilities[1]++;
		playerRep[3]++;
		for(MagicalAbilityCountListener lis: GameInfo.magicalAbilityCountListeners) {
			lis.inPhantasmExpansionCountChange(magicalAbilities[1]);
		}
	}

	//MODIFIES: magicalAbilities
	public void incrementMagicalHexAbility() {
		magicalAbilities[2]++;
		playerRep[4]++;
		for(MagicalAbilityCountListener lis: GameInfo.magicalAbilityCountListeners) {
			lis.inMagicalHexCountChange(magicalAbilities[2]);
		}
	}

	//MODIFIES: magicalAbilities
	public void incrementUnstoppableEnchantedSphereAbility() {
		magicalAbilities[3]++;
		playerRep[5]++;
		for(MagicalAbilityCountListener lis: GameInfo.magicalAbilityCountListeners) {
			lis.inUnstoppableCountChange(magicalAbilities[3]);
		}
	}

	//MODIFIES: magicalAbilities
	public void addRandomAbility() {
		Random rand = new Random();
		int randInt = rand.nextInt(4);
		switch(randInt) {
		case 0: incrementChanceGivingAbility(); break; 
		case 1: incrementNoblePhantasmExpansionAbility(); break;
		case 2: incrementMagicalHexAbility(); break;
		case 3: incrementUnstoppableEnchantedSphereAbility(); break;
		}
	}




}