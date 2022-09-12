package gui;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class GameImages {
	
	public static ImageIcon chanceGivingAbilityIcon1= new ImageIcon("heart.png");
	public static Image chanceGivingAbilityImg = chanceGivingAbilityIcon1.getImage().getScaledInstance(15,15,Image.SCALE_SMOOTH);
	public static ImageIcon chanceGivingAbilityIcon2 = new ImageIcon(chanceGivingAbilityImg);

	public static ImageIcon noblePhantasmExpansionIcon1= new ImageIcon("doubleLength.png");
	public static Image noblePhantasmExpansionImg = noblePhantasmExpansionIcon1.getImage().getScaledInstance(15,15,Image.SCALE_SMOOTH);
	public static ImageIcon noblePhantasmExpansionIcon2 = new ImageIcon(noblePhantasmExpansionImg);

	public static ImageIcon magicalHexIcon1= new ImageIcon("magicalHex.jpg");
	public static Image magicalHexImg = magicalHexIcon1.getImage().getScaledInstance(15,15,Image.SCALE_SMOOTH);
	public static ImageIcon magicalHexIcon2 = new ImageIcon(magicalHexImg);

	public static ImageIcon unstoppableEnchantedSphereIcon1= new ImageIcon("updatedUnstoppable.png");
	public static Image unstoppableEnchantedSphereImg = unstoppableEnchantedSphereIcon1.getImage().getScaledInstance(15,15,Image.SCALE_SMOOTH);
	public static ImageIcon unstoppableEnchantedSphereIcon2 = new ImageIcon(unstoppableEnchantedSphereImg);

	public static ImageIcon unstoppableIcon1= new ImageIcon("updatedUnstoppable.png");
	public static Image unstoppableImg = unstoppableEnchantedSphereIcon1.getImage().getScaledInstance(16,16,Image.SCALE_SMOOTH);
	public static ImageIcon unstoppableIcon2 = new ImageIcon(unstoppableEnchantedSphereImg);

	public static ImageIcon leftCanonIcon1= new ImageIcon("leftCanon.png");
	public static Image leftCanonImg = leftCanonIcon1.getImage().getScaledInstance(35,45,Image.SCALE_SMOOTH);
	public static ImageIcon leftCanonIcon2 = new ImageIcon(leftCanonImg);

	public static ImageIcon rightCanonIcon1= new ImageIcon("rightCanon.png");
	public static Image rightCanonImg = rightCanonIcon1.getImage().getScaledInstance(35,45,Image.SCALE_SMOOTH);
	public static ImageIcon rightCanonIcon2 = new ImageIcon(rightCanonImg);
	
	public static ImageIcon magicalBallIcon1= new ImageIcon("magicalHexBall.png");
	public static Image magicalBallImg = magicalBallIcon1.getImage().getScaledInstance(16,16,Image.SCALE_SMOOTH);
	public static ImageIcon magicalBallIcon2 = new ImageIcon(magicalBallImg);
	
	
	public static ImageIcon remainingIcon1= new ImageIcon("remaining.png");
	public static Image remainingIm = remainingIcon1.getImage().getScaledInstance(15,15,Image.SCALE_SMOOTH);
	public static ImageIcon remainingIcon = new ImageIcon(remainingIm);

	public static ImageIcon giftBoxIcon1= new ImageIcon("giftbox.png");
	public static Image giftBoxIm = giftBoxIcon1.getImage().getScaledInstance(25,25,Image.SCALE_SMOOTH);
	public static ImageIcon giftBoxIcon = new ImageIcon(giftBoxIm);

	public static ImageIcon noblePhantasmIcon1= new ImageIcon("phantasm-rotated.png");
	public static Image noblePhantasmIm = noblePhantasmIcon1.getImage().getScaledInstance(UIConstants.width/10,20,Image.SCALE_SMOOTH);
	public static ImageIcon noblePhantasmIcon = new ImageIcon(noblePhantasmIm);

	public static ImageIcon sphereIcon1= new ImageIcon("sphere_cropped.png");
	public static Image sphereIm = sphereIcon1.getImage().getScaledInstance(16,16,Image.SCALE_SMOOTH);
	public static ImageIcon sphereIcon = new ImageIcon(sphereIm);

	public static JLabel chanceGivingAbilityIcon = new JLabel(chanceGivingAbilityIcon2);
	public static JLabel noblePhantasmExpansionIcon = new JLabel(noblePhantasmExpansionIcon2);
	public static JLabel magicalHexIcon = new JLabel(magicalHexIcon2);
	public static JLabel unstoppableEnchantedSphereIcon = new JLabel(unstoppableEnchantedSphereIcon2);

	static JLabel leftCanonLabel= new JLabel(leftCanonIcon2);
	static JLabel rightCanonLabel= new JLabel(rightCanonIcon2);
	
	static JLabel firstBallLabel= new JLabel(magicalBallIcon2);
	static JLabel secondBallLabel= new JLabel(magicalBallIcon2);
	
	public static ImageIcon simpleIcon1= new ImageIcon("simple_obstacle_cropped.png");
	public static Image simpleIm = simpleIcon1.getImage().getScaledInstance(UIConstants.rectangleObstacleWidth,20,Image.SCALE_SMOOTH);
	public static ImageIcon simpleIcon = new ImageIcon(simpleIm);

	public static ImageIcon firmIcon1= new ImageIcon("firm_obstacle_cropped.png");
	public static Image firmIm = firmIcon1.getImage().getScaledInstance(UIConstants.rectangleObstacleWidth,20,Image.SCALE_SMOOTH);
	public static ImageIcon firmIcon = new ImageIcon(firmIm);

	public static ImageIcon giftIcon1= new ImageIcon("gift_obstacle_cropped.png");
	public static Image giftIm = giftIcon1.getImage().getScaledInstance(UIConstants.rectangleObstacleWidth,20,Image.SCALE_SMOOTH);
	public static ImageIcon giftIcon = new ImageIcon(giftIm);

	public static ImageIcon explosiveIcon1= new ImageIcon("explosive_obstacle_cropped.png");
	public static Image explosiveIm = explosiveIcon1.getImage().getScaledInstance(2*UIConstants.circularObstacleRadius, 2*UIConstants.circularObstacleRadius,Image.SCALE_SMOOTH);
	public static ImageIcon explosiveIcon = new ImageIcon(explosiveIm);
	
	public static ImageIcon frozenSimpleIcon1= new ImageIcon("frozenSimple.png");
	public static Image frozenSimpleIm = frozenSimpleIcon1.getImage().getScaledInstance(UIConstants.rectangleObstacleWidth,20,Image.SCALE_SMOOTH);
	public static ImageIcon frozenSimpleIcon = new ImageIcon(frozenSimpleIm);
	
	public static ImageIcon frozenExplosiveIcon1= new ImageIcon("frozenExplosive.png");
	public static Image frozenExplosiveIm = frozenExplosiveIcon1.getImage().getScaledInstance(2*UIConstants.circularObstacleRadius, 2*UIConstants.circularObstacleRadius,Image.SCALE_SMOOTH);
	public static ImageIcon frozenExplosiveIcon = new ImageIcon(frozenExplosiveIm);
	
	public static ImageIcon frozenFirmIcon1= new ImageIcon("frozenFirm.png");
	public static Image frozenFirmIm = frozenFirmIcon1.getImage().getScaledInstance(UIConstants.rectangleObstacleWidth,20,Image.SCALE_SMOOTH);
	public static ImageIcon frozenFirmIcon = new ImageIcon(frozenFirmIm);
	
	public static ImageIcon imageIcon = new ImageIcon("intro.png"); // load the image to a imageIcon
	public static Image image = imageIcon.getImage(); // transform it 
	public static Image newimg = image.getScaledInstance(UIConstants.height*2/5, UIConstants.height*2/5,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
	public static ImageIcon imageIcon1 = new ImageIcon(newimg);  // transform it back
	public static JLabel introImage= new JLabel(imageIcon1);
	
	public static Image noblePhantasmIm2 = GameImages.noblePhantasmIcon.getImage().getScaledInstance(UIConstants.width/5,20,Image.SCALE_SMOOTH);
	public static ImageIcon noblePhantasmIconDoubled = new ImageIcon(noblePhantasmIm2);
	
	static ImageIcon hpo1= new ImageIcon("hallow_purple.png");
	static Image hpoIm = hpo1.getImage().getScaledInstance(UIConstants.width/50,20,Image.SCALE_SMOOTH);
	static ImageIcon hollowIcon = new ImageIcon(hpoIm);



}
