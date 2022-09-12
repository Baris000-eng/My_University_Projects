package domain.game;

import domain.obstacles.Obstacle;
import domain.player.EnchantedSphere;
import domain.player.NoblePhantasm;
import gui.MainFrame;

public class CollisionChecker {
	public static boolean collisionOfEnchantedSphereWithNoblePhantasm(NoblePhantasm nob, EnchantedSphere esp) {
		if((nob.getxLocation()-16<=esp.getxLocation() && esp.getxLocation()<=nob.getxLocation()+ nob.getLength()) && esp.getyLocation()+16 == 8*MainFrame.height/10+3) {
			return true;
		}
		return false;
	}

	public static Boolean collisionSphereFromUp(Obstacle obs,EnchantedSphere esp) {
		//REQUIRES: Nonnull objects as parameters.
		//EFFECTS: Checks if the given obstacle and enchanted sphere collides.
		//If they collide, it returns true. Otherwise, it return false.
		if((obs.getxLocation()-16<=esp.getxLocation() && esp.getxLocation()<= obs.getxLocation()+(MainFrame.width/50)) && (obs.getyLocation()==esp.getyLocation()+16)) {
			return true;
		}
		return false;
	}

	public static Boolean collisionSphereFromDown(Obstacle obs,EnchantedSphere esp) {
		if((obs.getxLocation()-16<=esp.getxLocation() && esp.getxLocation()<= obs.getxLocation()+(MainFrame.width/50)) && (esp.getyLocation()==obs.getyLocation()+20)) {
			return true;
		}
		return false;
	}

	public static Boolean collisionSphereFromLeft(Obstacle obs,EnchantedSphere esp) {
		if((obs.getxLocation()==esp.getxLocation()+16) && (obs.getyLocation()-16<=esp.getyLocation() && esp.getyLocation()<=obs.getyLocation()+20)) {
			return true;
		}
		return false;
	}

	public static Boolean collisionSphereFromRight(Obstacle obs,EnchantedSphere esp) {
		if((esp.getxLocation()== obs.getxLocation()+(MainFrame.width/50)) && (obs.getyLocation()-16<=esp.getyLocation() && esp.getyLocation()<=obs.getyLocation()+20)) {
			return true;
		}
		return false;
	}

	//----------------------------------------------------------


	public static Boolean collisionObsFromRight(Obstacle obs,Obstacle obs2) {
		if((obs.getxLocation()== obs2.getxLocation()+(MainFrame.width/50)) && (obs2.getyLocation()-20<=obs.getyLocation() && obs2.getyLocation()<=obs.getyLocation()+20)) {
			return true;
		}
		return false;
	}
	public static Boolean collisionObsFromUp(Obstacle obs,Obstacle obs2) {
		if((obs.getxLocation()-MainFrame.width/50<=obs2.getxLocation() && obs2.getxLocation()<= obs.getxLocation()+(MainFrame.width/50)) && (obs.getyLocation()==obs2.getyLocation()+20)) {
			return true;
		}
		return false;
	}

	public static Boolean collisionObsFromDown(Obstacle obs,Obstacle obs2) {
		if((obs.getxLocation()-MainFrame.width/50<=obs2.getxLocation() && obs2.getxLocation()<= obs.getxLocation()+(MainFrame.width/50)) && (obs2.getyLocation()==obs.getyLocation()+20)) {
			return true;
		}
		return false;
	}
	
	public static Boolean collisionObsFromLeft(Obstacle obs,Obstacle obs2) {
		// EFFECTS: Returns true if obs and obs2 are colliding, else if they are not colliding it returns false. If obs or obs2 is null, throws NullPointerException.
		if((obs.getxLocation()<=obs2.getxLocation()+MainFrame.width/50) && (obs.getyLocation()-20<=obs2.getyLocation() && obs2.getyLocation()<=obs.getyLocation()+20)) {
			return true;
		}
		return false;
	}

	public static int[] collisionUp() {
		int[] loc= {-1,-1};
		for (Obstacle o: GameInfo.obstacles) {
			if(collisionSphereFromUp(o, GameInfo.sphere)) {
				loc[0]=o.getxLocation();
				loc[1]=o.getyLocation();
				break;
			}
		}
		return loc;
	}

	public static int[] collisionDown() {
		int[] loc= {-1,-1};
		for (Obstacle o: GameInfo.obstacles) {
			if(collisionSphereFromDown(o, GameInfo.sphere)) {
				loc[0]=o.getxLocation();
				loc[1]=o.getyLocation();
				break;
			}
		}
		return loc;
	}

	public static int[] collisionRight() {
		int[] loc= {-1,-1};
		for (Obstacle o: GameInfo.obstacles) {
			if(collisionSphereFromRight(o, GameInfo.sphere)) {
				loc[0]=o.getxLocation();
				loc[1]=o.getyLocation();
				break;
			}
		}
		return loc;
	}

	public static int[] collisionLeft() {
		int[] loc= {-1,-1};
		for (Obstacle o: GameInfo.obstacles) {
			if(collisionSphereFromLeft(o, GameInfo.sphere)) {
				loc[0]=o.getxLocation();
				loc[1]=o.getyLocation();
				break;
			}
		}
		return loc;
	}

}
