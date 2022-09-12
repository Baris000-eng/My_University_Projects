package domain.player;

public interface MagicalAbilityListener {
	void inChanceGivingAbility();
	void inPhantasmExpansionAbility();
	void inMagicalHexAbility();
	void inUnstoppableSphereAbility();
	
	void inEndUnstoppableSphereAbility();
	
	void inEndMagicalHexAbility();
}
