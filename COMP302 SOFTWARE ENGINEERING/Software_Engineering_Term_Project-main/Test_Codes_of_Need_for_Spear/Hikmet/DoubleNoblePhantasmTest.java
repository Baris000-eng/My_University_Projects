package test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.game.CollisionChecker;
import domain.game.NeedForSpearGame;
import domain.magicalAbilities.MagicalAbility;
import domain.magicalAbilities.NoblePhantasmExpansion;
import domain.player.NoblePhantasm;


class DoubleNoblePhantasmTest {
	private static NeedForSpearGame controller;
	
	@Test
	 void doubleNoblePhantasmMustReturnTrue() { //check if the NoblePhantasmExpension correctly doubles the length.
		
		double  length = 10.0;
		NoblePhantasmExpansion npe = new NoblePhantasmExpansion("test");
		assertTrue(npe.doubleNoblePhantasm(length)==20.0); //BlackBox test
	}
	
	@Test
	 void doubleNoblePhantasmMustReturnFalse() { //check if the NoblePhantasmExpension correctly doubles the length.
		
		double  length = 5.0;
		NoblePhantasmExpansion npe = new NoblePhantasmExpansion("test");
		assertFalse(npe.doubleNoblePhantasm(length)==2.0); //BlackBox test
	}
	
	@Test
	 void doubleNoblePhantasmCheckType() { //check if the NoblePhantasmExpension correctly doubles the length.
		
		int length = 5;
		NoblePhantasmExpansion npe = new NoblePhantasmExpansion("test");
		assertTrue(npe.doubleNoblePhantasm(length)==10.0); //BlackBox test
	}
	
	
	
	
	
	

	
	
	
	
	
	

}
