package domain.player;

import java.util.Random;

import domain.game.GameInfo;

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
	
	public double[] playerRep=new double[6];
	
	private String username;
	private double score;
	private int life;
	private int[] magicalAbilities = {0,0,0,0}; 
	
	public Player() {
		life=3;
		score = 0;
		playerRep[0]=score;
		playerRep[1]=life;
		playerRep[2]=0;
		playerRep[3]=0;
		playerRep[4]=0;
		playerRep[5]=0;
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
	}

	public Player(String username, double score, int life) {
		this.username=username;
		this.score=score;
		this.life=life;
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
	public void updateScore(int second) {  
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

	//MODIFIES: magicalAbilities
	public void decrementChanceGivingAbility() {
		magicalAbilities[0]--;
		playerRep[2]--;
	}
	
	//MODIFIES: magicalAbilities
	public void decrementNoblePhantasmExpansionAbility() {
		magicalAbilities[1]--;
		playerRep[3]--;
	}
	
	//MODIFIES: magicalAbilities
	public void decrementMagicalHexAbility() {
		magicalAbilities[2]--;
		playerRep[4]--;
	}
	
	//MODIFIES: magicalAbilities
	public void decrementUnstoppableEnchantedSphereAbility() {
		magicalAbilities[3]--;
		playerRep[5]--;
	}
	
	//MODIFIES: magicalAbilities
	public void incrementChanceGivingAbility() {
		magicalAbilities[0]++;
		playerRep[2]++;
	}

	//MODIFIES: magicalAbilities
	public void incrementNoblePhantasmExpansionAbility() {
		magicalAbilities[1]++;
		playerRep[3]++;
	}

	//MODIFIES: magicalAbilities
	public void incrementMagicalHexAbility() {
		magicalAbilities[2]++;
		playerRep[4]++;
	}

	//MODIFIES: magicalAbilities
	public void incrementUnstoppableEnchantedSphereAbility() {
		magicalAbilities[3]++;
		playerRep[5]++;
	}

	//MODIFIES: magicalAbilities
	public void addRandomAbility() {
		Random rand = new Random();
		int randInt = rand.nextInt(4);
		magicalAbilities[randInt]++;
		playerRep[randInt+2]++;
	}




}