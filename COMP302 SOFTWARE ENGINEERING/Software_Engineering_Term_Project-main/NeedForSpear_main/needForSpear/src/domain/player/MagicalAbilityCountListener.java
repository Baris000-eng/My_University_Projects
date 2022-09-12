package domain.player;

public interface MagicalAbilityCountListener {
	void inChanceGivingCountChange(int count);
	void inPhantasmExpansionCountChange(int count);
	void inMagicalHexCountChange(int count);
	void inUnstoppableCountChange(int count);
	void inMagicalAbilityInitializition(int chanceGiving, int phantasmExpansion, int magicalHex, int unstoppable);
}
