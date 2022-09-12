package domain.ymir;

public interface FrozenListener {
	void onFrozen(String obstacleName, int index);

	void onFrozenEnded(String obstacleName, int index);
}
