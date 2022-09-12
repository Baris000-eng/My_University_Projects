package domain.game;

import java.util.ArrayList;

import domain.obstacles.GiftBox;
import domain.obstacles.MagicalHex;
import domain.obstacles.MagicalHexListener;
import domain.obstacles.Obstacle;
import domain.obstacles.ObstacleCreationListener;
import domain.obstacles.Remaining;
import domain.player.EnchantedSphere;
import domain.player.MagicalAbilityCountListener;
import domain.player.MagicalAbilityListener;
import domain.player.NoblePhantasm;
import domain.player.PhantasmMovementLengthListener;
import domain.player.Player;
import domain.player.RemainingGiftBoxListener;
import domain.player.ScoreLivesListener;
import domain.ymir.FrozenListener;
import domain.ymir.HollowPurpleListener;
import domain.ymir.Ymir;


public class GameInfo {
	
	//Listener lists
	public static ArrayList<ScoreLivesListener> scoreLivesListeners=new ArrayList<ScoreLivesListener>();
	public static ArrayList<MagicalAbilityListener> magicalAbilityListeners=new ArrayList<MagicalAbilityListener>();
	public static ArrayList<PhantasmMovementLengthListener> phantasmListeners=new ArrayList<PhantasmMovementLengthListener>();
	public static ArrayList<RemainingGiftBoxListener> remGiftListeners=new ArrayList<RemainingGiftBoxListener>();
	public static ArrayList<MagicalHexListener> magicalHexListeners=new ArrayList<MagicalHexListener>();
	public static ArrayList<ResetListener> resetListeners=new ArrayList<ResetListener>();
	public static ArrayList<ObstacleCreationListener> obstacleCreationListeners=new ArrayList<ObstacleCreationListener>();
	public static ArrayList<HollowPurpleListener> hollowPurpleListeners=new ArrayList<HollowPurpleListener>();
	public static ArrayList<FrozenListener> frozenListeners=new ArrayList<FrozenListener>();
	public static ArrayList<MagicalAbilityCountListener> magicalAbilityCountListeners=new ArrayList<MagicalAbilityCountListener>();
	
	//Game objects
	public static ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	public static Player currentPlayer=new Player();
	public static EnchantedSphere sphere = new EnchantedSphere(DomainConstants.sphereInitialX,DomainConstants.sphereInitialY);
	public static NoblePhantasm phantasm = new NoblePhantasm(DomainConstants.phantasmInitialX,DomainConstants.phantasmLength);
	public static ArrayList<Remaining> remainings = new ArrayList<Remaining>();
	public static ArrayList<GiftBox> giftBoxes = new ArrayList<GiftBox>();
	public static ArrayList<MagicalHex> magicalHexes = new ArrayList<MagicalHex>();
	
	public static Ymir ymir=new Ymir();
	
	public static int myArray[][]=new int [25*(DomainConstants.width-2*DomainConstants.widthButton-DomainConstants.width/50)/DomainConstants.width+1][(DomainConstants.height/2-DomainConstants.heightButton)/40+1];
	
	public static boolean magicalHexAbilityActivated=false;


}


