package domain.player;

public interface ScoreLivesListener {
	void onScoreChange(double score);
	void onLivesChange(int lives);
	void onLiveLose(int lives);
}
