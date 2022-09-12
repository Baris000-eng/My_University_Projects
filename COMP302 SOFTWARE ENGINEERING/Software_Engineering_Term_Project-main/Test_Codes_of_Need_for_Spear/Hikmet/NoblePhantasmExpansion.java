package domain.magicalAbilities;

import java.sql.Time;

public class NoblePhantasmExpansion extends MagicalAbility {
	private Time duration;
    public NoblePhantasmExpansion(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

    
    
    
    
    
  public double doubleNoblePhantasm(double length) throws NullPointerException {
	  // REQUIRES : length must be double.
	  // MODIFIES : length
	  // EFFECTS : returns the doubled length;
	  length= 2*length;
	  return length;
  }
}
